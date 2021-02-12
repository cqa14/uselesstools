package ch.cqa.uselesstools.init;

import ch.cqa.uselesstools.UselessTools;
import ch.cqa.uselesstools.items.ItemHighmeter;
import ch.cqa.uselesstools.items.mfc.ItemMFC;
import ch.cqa.uselesstools.utils.CustomArmorMaterials;
import ch.cqa.uselesstools.utils.CustomItemTiers;
import ch.cqa.uselesstools.utils.ModItemGroups;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UselessTools.MODID);

    public static final RegistryObject<Item> CRUMBS = ITEMS.register("crumbs", () -> new Item(new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP)));

    public static final RegistryObject<Item> UNFRIED_CHICKEN = ITEMS.register("unfried_chicken", () -> new Item(new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).food(new Food.Builder().hunger(5).saturation(0.5f).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.2F).build())));
    public static final RegistryObject<Item> FRIED_CHICKEN = ITEMS.register("fried_chicken", () -> new Item(new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).food(new Food.Builder().fastToEat().hunger(11).saturation(1.5f).build())));

    public static final RegistryObject<Item> DARKSTONE_SHARD = ITEMS.register("darkstone_shard", () -> new Item(new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP)));

    public static final RegistryObject<Item> DARKSTONE_SWORD = ITEMS.register("darkstone_sword", () -> new SwordItem(CustomItemTiers.DARKSTONE, 3, -2.4f, new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> DARKSTONE_PICKAXE = ITEMS.register("darkstone_pickaxe", () -> new PickaxeItem(CustomItemTiers.DARKSTONE, 0, -2.8f, new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> DARKSTONE_SHOVEL = ITEMS.register("darkstone_shovel", () -> new ShovelItem(CustomItemTiers.DARKSTONE, 0f, -2.8f, new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> DARKSTONE_AXE = ITEMS.register("darkstone_axe", () -> new AxeItem(CustomItemTiers.DARKSTONE, 4f, -2.6f, new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> DARKSTONE_HOE = ITEMS.register("darkstone_hoe", () -> new HoeItem(CustomItemTiers.DARKSTONE,  0, -2.8f, new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1)));

    public static final RegistryObject<Item> DARKSTONE_HELMET = ITEMS.register("darkstone_helmet", () -> new ArmorItem(CustomArmorMaterials.DARKSTONE_ARMOR, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> DARKSTONE_CHESTPLATE = ITEMS.register("darkstone_chestplate", () -> new ArmorItem(CustomArmorMaterials.DARKSTONE_ARMOR, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> DARKSTONE_LEGGINGS = ITEMS.register("darkstone_leggings", () -> new ArmorItem(CustomArmorMaterials.DARKSTONE_ARMOR, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> DARKSTONE_BOOTS = ITEMS.register("darkstone_boots", () -> new ArmorItem(CustomArmorMaterials.DARKSTONE_ARMOR, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP).maxStackSize(1)));

    public static final RegistryObject<Item> MFC = ITEMS.register("mfc", ItemMFC::new);
    public static final RegistryObject<Item> CARDBOARD = ITEMS.register("cardboard", () -> new Item(new Item.Properties().group(ModItemGroups.USELESSTOOLS_GROUP)));

    public static final RegistryObject<Item> HIGHMETER = ITEMS.register("highmeter", ItemHighmeter::new);

}
