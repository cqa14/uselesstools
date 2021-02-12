package ch.cqa.uselesstools.init;

import ch.cqa.uselesstools.UselessTools;
import ch.cqa.uselesstools.tileentity.TileEntityTickCounter;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities
{

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, UselessTools.MODID);

    public static final RegistryObject<TileEntityType<?>> TICK_COUNTER_TILE_ENTITY = TILE_ENTITIES.register("tick_counter_tile_entity", () -> TileEntityType.Builder.create(TileEntityTickCounter::new, ModBlocks.TICK_COUNTER.get()).build(null));

}
