package ch.cqa.uselesstools.tileentity.mfc;

import ch.cqa.uselesstools.init.ModContainers;
import ch.cqa.uselesstools.items.mfc.ItemStackHandlerMFC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

public class ContainerMFC extends Container {

    public static ContainerMFC createContainerServerSide(int windowID, PlayerInventory playerInventory, ItemStackHandlerMFC bagContents,
                                                               ItemStack flowerBag) {
        return new ContainerMFC(windowID, playerInventory, bagContents, flowerBag);
    }

    public static ContainerMFC createContainerClientSide(int windowID, PlayerInventory playerInventory, net.minecraft.network.PacketBuffer extraData) {

        int numberOfChickenSlots = extraData.readInt();

        try {
            ItemStackHandlerMFC itemStackHandlerMFC = new ItemStackHandlerMFC(numberOfChickenSlots);

            return new ContainerMFC(windowID, playerInventory, itemStackHandlerMFC, ItemStack.EMPTY);
        } catch (IllegalArgumentException iae) {
            LOGGER.warn(iae);
        }
        return null;
    }

    private final ItemStackHandlerMFC itemStackHandlerMFC;
    private final ItemStack itemStackBeingHeld;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int BAG_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int MAX_EXPECTED_BAG_SLOT_COUNT = 16;

    public static final int BAG_INVENTORY_YPOS = 26;
    public static final int PLAYER_INVENTORY_YPOS = 84;

    private ContainerMFC(int windowId, PlayerInventory playerInv,
                               ItemStackHandlerMFC itemStackHandlerMFC,
                               ItemStack itemStackBeingHeld) {
        super(ModContainers.MFC_CONTAINER.get(), windowId);
        this.itemStackHandlerMFC = itemStackHandlerMFC;
        this.itemStackBeingHeld = itemStackBeingHeld;

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 142;
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            int slotNumber = x;
            addSlot(new Slot(playerInv, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        final int PLAYER_INVENTORY_XPOS = 8;
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(playerInv, slotNumber, xpos, ypos));
            }
        }

        int bagSlotCount = itemStackHandlerMFC.getSlots();
        if (bagSlotCount < 1 || bagSlotCount > MAX_EXPECTED_BAG_SLOT_COUNT) {
            LOGGER.warn("Unexpected invalid slot count in ItemStackHandlerMFC(" + bagSlotCount + ")");
            bagSlotCount = MathHelper.clamp(bagSlotCount, 1, MAX_EXPECTED_BAG_SLOT_COUNT);
        }

        final int BAG_SLOTS_PER_ROW = 8;
        final int BAG_INVENTORY_XPOS = 17;
        for (int bagSlot = 0; bagSlot < bagSlotCount; ++bagSlot) {
            int slotNumber = bagSlot;
            int bagRow = bagSlot / BAG_SLOTS_PER_ROW;
            int bagCol = bagSlot % BAG_SLOTS_PER_ROW;
            int xpos = BAG_INVENTORY_XPOS + SLOT_X_SPACING * bagCol;
            int ypos = BAG_INVENTORY_YPOS + SLOT_Y_SPACING * bagRow;
            addSlot(new SlotItemHandler(itemStackHandlerMFC, slotNumber, xpos, ypos));
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity player) {

        ItemStack main = player.getHeldItemMainhand();
        ItemStack off = player.getHeldItemOffhand();
        return (!main.isEmpty() && main == itemStackBeingHeld) ||
                (!off.isEmpty() && off == itemStackBeingHeld);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int sourceSlotIndex) {
        Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();
        final int BAG_SLOT_COUNT = itemStackHandlerMFC.getSlots();


        if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!mergeItemStack(sourceStack, BAG_INVENTORY_FIRST_SLOT_INDEX, BAG_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT, false)){
                return ItemStack.EMPTY;
            }
        } else if (sourceSlotIndex >= BAG_INVENTORY_FIRST_SLOT_INDEX && sourceSlotIndex < BAG_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT) {

            if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            LOGGER.warn("Invalid slotIndex:" + sourceSlotIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public void detectAndSendChanges() {
        if (itemStackHandlerMFC.isDirty()) {
            CompoundNBT nbt = itemStackBeingHeld.getOrCreateTag();
            int dirtyCounter = nbt.getInt("dirtyCounter");
            nbt.putInt("dirtyCounter", dirtyCounter + 1);
            itemStackBeingHeld.setTag(nbt);
        }
        super.detectAndSendChanges();
    }

    private static final Logger LOGGER = LogManager.getLogger();
}
