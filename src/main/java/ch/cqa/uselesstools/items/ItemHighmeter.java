package ch.cqa.uselesstools.items;

import ch.cqa.uselesstools.utils.ModItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


public class ItemHighmeter extends Item
{
    public ItemHighmeter() {
        super(new Item.Properties().maxStackSize(1).group(ModItemGroups.USELESSTOOLS_GROUP));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            int altitude = player.getPosition().getY();

            player.sendStatusMessage(new StringTextComponent("Altitude : " + altitude), true);
        }
        return ActionResult.resultSuccess(stack);
    }

}
