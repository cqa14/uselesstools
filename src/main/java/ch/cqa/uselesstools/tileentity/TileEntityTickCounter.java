package ch.cqa.uselesstools.tileentity;

import ch.cqa.uselesstools.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTickCounter extends TileEntity implements ITickableTileEntity
{

    private int counter = 0;

    public TileEntityTickCounter() {
        super(ModTileEntities.TICK_COUNTER_TILE_ENTITY.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);

        this.setCounter(nbt.getInt("counter"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        compound.putInt("counter", this.getCounter());

        return compound;
    }

    @Override
    public void tick() {
        setCounter(getCounter() + 1);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
