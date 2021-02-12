package ch.cqa.uselesstools.items.mfc;

import ch.cqa.uselesstools.tileentity.mfc.CapabilityProviderMFC;
import ch.cqa.uselesstools.tileentity.mfc.ContainerMFC;
import ch.cqa.uselesstools.utils.ModItemGroups;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class ItemMFC extends Item {

    public ItemMFC() {
        super(new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            INamedContainerProvider containerProviderMFC = new ContainerProviderMFC(this, stack);
            final int NUMBER_OF_CHICKEN_SLOTS = 16;
            NetworkHooks.openGui((ServerPlayerEntity) player,
                    containerProviderMFC,
                    (packetBuffer)->{packetBuffer.writeInt(NUMBER_OF_CHICKEN_SLOTS);});

        }
        return ActionResult.resultSuccess(stack);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext ctx) {
        World world = ctx.getWorld();
        if (world.isRemote()) return ActionResultType.PASS;

        BlockPos pos = ctx.getPos();
        Direction side = ctx.getFace();
        ItemStack itemStack = ctx.getItem();
        if (!(itemStack.getItem() instanceof ItemMFC)) throw new AssertionError("Unexpected ItemMFC type");
        ItemMFC itemMFC = (ItemMFC)itemStack.getItem();

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity == null) return ActionResultType.PASS;
        if (world.isRemote()) return ActionResultType.SUCCESS;

        IItemHandler tileInventory;
        LazyOptional<IItemHandler> capability = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
        if (capability.isPresent()) {
            tileInventory = capability.orElseThrow(AssertionError::new);
        } else if (tileEntity instanceof IInventory) {
            tileInventory = new InvWrapper((IInventory)tileEntity);
        } else {
            return ActionResultType.FAIL;
        }

        ItemStackHandlerMFC itemStackHandlerMFC =  itemMFC.getItemStackHandlerMFC(itemStack);
        for (int i = 0; i < itemStackHandlerMFC.getSlots(); i++) {
            ItemStack chicken = itemStackHandlerMFC.getStackInSlot(i);
            ItemStack chickenWhichDidNotFit = ItemHandlerHelper.insertItemStacked(tileInventory, chicken, false);
            itemStackHandlerMFC.setStackInSlot(i, chickenWhichDidNotFit);
        }
        tileEntity.markDirty();

        CompoundNBT nbt = itemStack.getOrCreateTag();
        int dirtyCounter = nbt.getInt("dirtyCounter");
        nbt.putInt("dirtyCounter", dirtyCounter + 1);
        itemStack.setTag(nbt);

        return ActionResultType.SUCCESS;
    }

    private static class ContainerProviderMFC implements INamedContainerProvider {
        public ContainerProviderMFC(ItemMFC itemMFC, ItemStack itemStackMFC) {
            this.itemStackMFC = itemStackMFC;
            this.itemMFC = itemMFC;
        }

        @Override
        public ITextComponent getDisplayName() {
            return itemStackMFC.getDisplayName();
        }

        @Override
        public ContainerMFC createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
            ContainerMFC newContainerServerSide =
                    ContainerMFC.createContainerServerSide(windowID, playerInventory,
                            itemMFC.getItemStackHandlerMFC(itemStackMFC),
                            itemStackMFC);
            return newContainerServerSide;
        }

        private ItemMFC itemMFC;
        private ItemStack itemStackMFC;
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT oldCapNbt) {

        return new CapabilityProviderMFC();
    }

    private static ItemStackHandlerMFC getItemStackHandlerMFC(ItemStack itemStack) {
        IItemHandler mfc = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        if (mfc == null || !(mfc instanceof ItemStackHandlerMFC)) {
            LOGGER.error("ItemMFC did not have the expected ITEM_HANDLER_CAPABILITY");
            return new ItemStackHandlerMFC(1);
        }
        return (ItemStackHandlerMFC)mfc;
    }

    private final String BASE_NBT_TAG = "base";
    private final String CAPABILITY_NBT_TAG = "cap";

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT baseTag = stack.getTag();
        ItemStackHandlerMFC itemStackHandlerMFC = getItemStackHandlerMFC(stack);
        CompoundNBT capabilityTag = itemStackHandlerMFC.serializeNBT();
        CompoundNBT combinedTag = new CompoundNBT();
        if (baseTag != null) {
            combinedTag.put(BASE_NBT_TAG, baseTag);
        }
        if (capabilityTag != null) {
            combinedTag.put(CAPABILITY_NBT_TAG, capabilityTag);
        }
        return combinedTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }
        CompoundNBT baseTag = nbt.getCompound(BASE_NBT_TAG);
        CompoundNBT capabilityTag = nbt.getCompound(CAPABILITY_NBT_TAG);
        stack.setTag(baseTag);
        ItemStackHandlerMFC itemStackHandlerMFC = getItemStackHandlerMFC(stack);
        itemStackHandlerMFC.deserializeNBT(capabilityTag);
    }

    public static float getFullnessPropertyOverride(ItemStack itemStack, @Nullable World world, @Nullable LivingEntity livingEntity) {
        ItemStackHandlerMFC mfc = getItemStackHandlerMFC(itemStack);
        float fractionEmpty = mfc.getNumberOfEmptySlots() / (float)mfc.getSlots();
        return 1.0F - fractionEmpty;
    }

    private static final Logger LOGGER = LogManager.getLogger();

}
