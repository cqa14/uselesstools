package ch.cqa.uselesstools.init;

import ch.cqa.uselesstools.UselessTools;
import ch.cqa.uselesstools.blocks.BlockTickCounter;
import ch.cqa.uselesstools.utils.ModItemGroups;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks
{

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UselessTools.MODID);


    public static final RegistryObject<Block> DARKSTONE_ORE = createBlock("darkstone_ore", () -> new Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(3f, 15f).harvestTool(ToolType.PICKAXE).harvestLevel(3).setRequiresTool()));
    public static final RegistryObject<Block> DARKSTONE_BLOCK = createBlock("darkstone_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(2f, 10f).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool()));

    public static final RegistryObject<Block> TICK_COUNTER = createBlock("tick_counter", BlockTickCounter::new);

    public static RegistryObject<Block> createBlock(String name, Supplier<? extends Block> supplier)
    {

        RegistryObject<Block> block = BLOCKS.register(name, supplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP)));
        return block;

    }

}
