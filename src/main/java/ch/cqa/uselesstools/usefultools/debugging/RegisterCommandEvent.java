package ch.cqa.uselesstools.usefultools.debugging;

import ch.cqa.uselesstools.usefultools.debugging.commands.MBEdebugCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RegisterCommandEvent
{
  @SubscribeEvent
  public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
    CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
    MBEdebugCommand.register(commandDispatcher);
  }
}
