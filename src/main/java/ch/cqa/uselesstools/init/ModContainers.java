package ch.cqa.uselesstools.init;

import ch.cqa.uselesstools.UselessTools;
import ch.cqa.uselesstools.tileentity.mfc.ContainerMFC;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers
{

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, UselessTools.MODID);

    public static final RegistryObject<ContainerType<ContainerMFC>> MFC_CONTAINER = CONTAINERS.register("mfc_container", () -> IForgeContainerType.create(ContainerMFC::createContainerClientSide));

}
