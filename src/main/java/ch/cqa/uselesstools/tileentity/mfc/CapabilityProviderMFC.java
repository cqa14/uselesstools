package ch.cqa.uselesstools.tileentity.mfc;

import ch.cqa.uselesstools.items.mfc.ItemStackHandlerMFC;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderMFC implements ICapabilitySerializable<INBT> {

    private final Direction NO_SPECIFIC_SIDE = null;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability) return (LazyOptional<T>)(lazyInitialisionSupplier);
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), NO_SPECIFIC_SIDE);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), NO_SPECIFIC_SIDE, nbt);
    }

    private ItemStackHandlerMFC getCachedInventory() {
        if (itemStackHandlerMFC == null) {
            itemStackHandlerMFC = new ItemStackHandlerMFC(MAX_NUMBER_OF_CHICKEN_IN_MFC);
        }
        return itemStackHandlerMFC;
    }

    private static final int MAX_NUMBER_OF_CHICKEN_IN_MFC = 16;

    private ItemStackHandlerMFC itemStackHandlerMFC;

    private final LazyOptional<IItemHandler> lazyInitialisionSupplier = LazyOptional.of(this::getCachedInventory);
}
