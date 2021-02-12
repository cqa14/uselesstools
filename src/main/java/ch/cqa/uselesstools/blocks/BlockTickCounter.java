package ch.cqa.uselesstools.blocks;

import ch.cqa.uselesstools.tileentity.TileEntityTickCounter;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;


public class BlockTickCounter extends Block
{

    public BlockTickCounter()
    {

        super(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(2f, 3f));

    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityTickCounter();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if(worldIn.getTileEntity(pos) instanceof TileEntityTickCounter){
            TileEntityTickCounter tileEntity = (TileEntityTickCounter) worldIn.getTileEntity(pos);

            player.sendStatusMessage(new StringTextComponent("Counter : " + tileEntity.getCounter()), true);
            return  ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }
}

