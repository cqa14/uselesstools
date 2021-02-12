package ch.cqa.uselesstools;

import ch.cqa.uselesstools.init.*;
import ch.cqa.uselesstools.items.mfc.ItemMFC;
import ch.cqa.uselesstools.tileentity.mfc.ContainerScreenMFC;
import ch.cqa.uselesstools.usefultools.debugging.DebugBlockVoxelShapeHighlighter;
import ch.cqa.uselesstools.usefultools.debugging.DebugSpawnInhibitor;
import ch.cqa.uselesstools.usefultools.debugging.ForgeLoggerTweaker;
import ch.cqa.uselesstools.usefultools.debugging.RegisterCommandEvent;
import ch.cqa.uselesstools.usefultools.debugging.commands.DebugTriggerWatcher;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Level;


@Mod(UselessTools.MODID)

public class UselessTools {

    public static final String MODID = "uselesstools";

    public UselessTools()
    {
        final boolean HIDE_CONSOLE_NOISE = true;
        if (HIDE_CONSOLE_NOISE) {
            ForgeLoggerTweaker.setMinimumLevel(Level.WARN);
            ForgeLoggerTweaker.applyLoggerFilter();
        }

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientsetup);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModTileEntities.TILE_ENTITIES.register(bus);
        ModContainers.CONTAINERS.register(bus);

    }

    private void setup(FMLCommonSetupEvent e)
    {

        ModFeatures features = new ModFeatures();
        features.init();
        MinecraftForge.EVENT_BUS.register(features);

        MinecraftForge.EVENT_BUS.register(RegisterCommandEvent.class);
        MinecraftForge.EVENT_BUS.register(DebugSpawnInhibitor.class);
        MinecraftForge.EVENT_BUS.register(DebugTriggerWatcher.class);

    }

    private void clientsetup(FMLClientSetupEvent e)
    {
        e.enqueueWork(UselessTools::registerPropertyOverride);

        ScreenManager.registerFactory(ModContainers.MFC_CONTAINER.get(), ContainerScreenMFC::new);
        MinecraftForge.EVENT_BUS.register(DebugBlockVoxelShapeHighlighter.class);
    }

    public static void registerPropertyOverride() {
        ItemModelsProperties.registerProperty(ModItems.MFC.get(), new ResourceLocation("fullness"), ItemMFC::getFullnessPropertyOverride);

    }

}
