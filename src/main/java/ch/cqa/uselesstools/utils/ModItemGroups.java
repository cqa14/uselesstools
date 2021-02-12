package ch.cqa.uselesstools.utils;

import ch.cqa.uselesstools.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups
{

    public static final ItemGroup USELESSTOOLS_GROUP = new ItemGroup("uselesstools_group")
    {

        @Override
        public ItemStack createIcon()
        {

            return new ItemStack(ModItems.DARKSTONE_SHARD.get());

        }

    };

}
