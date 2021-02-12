package ch.cqa.uselesstools.items.mfc;

import ch.cqa.uselesstools.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemStackHandlerMFC extends ItemStackHandler
{

    public static final int MIN_CHICKEN_SLOTS = 1;
    public static final int MAX_CHICKEN_SLOTS = 16;

    public ItemStackHandlerMFC(int numberOfSlots) {
        super(MathHelper.clamp(numberOfSlots, MIN_CHICKEN_SLOTS, MAX_CHICKEN_SLOTS));
        if (numberOfSlots < MIN_CHICKEN_SLOTS || numberOfSlots > MAX_CHICKEN_SLOTS) {
            throw new IllegalArgumentException("Invalid number of flower slots:"+numberOfSlots);
        }
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot < 0 || slot >= MAX_CHICKEN_SLOTS) {
            throw new IllegalArgumentException("Invalid slot number:"+slot);
        }
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();
        if (item == ModItems.FRIED_CHICKEN.get()) return true;
        return false;
    }

    public int getNumberOfEmptySlots() {
        final int NUMBER_OF_SLOTS = getSlots();

        int emptySlotCount = 0;
        for (int i = 0; i < NUMBER_OF_SLOTS; ++i) {
            if (getStackInSlot(i) == ItemStack.EMPTY) {
                ++emptySlotCount;
            }
        }
        return emptySlotCount;
    }

    public boolean isDirty() {
        boolean currentState = isDirty;
        isDirty = false;
        return currentState;
    }

    protected void onContentsChanged(int slot) {
        isDirty = true;
    }

    private boolean isDirty = true;
}
