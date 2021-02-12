package ch.cqa.uselesstools.init;

import jdk.jfr.Category;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModFeatures
{

    public ConfiguredFeature<?,?> DARKSTONE_OREFEATURE;

    public void init()
    {

        DARKSTONE_OREFEATURE = register("darkstone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.DARKSTONE_ORE.get().getDefaultState(), 3))
        .square()
        .range(15)
        .func_242731_b(10));

    }

    public <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> feature)
    {

        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, name, feature);

    }

    @SubscribeEvent
    public void biomeLoading(BiomeLoadingEvent e)
    {

        BiomeGenerationSettingsBuilder generation = e.getGeneration();

        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DARKSTONE_OREFEATURE);

    }

}
