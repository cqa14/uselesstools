package ch.cqa.uselesstools.usefultools.debugging.commands;

import ch.cqa.uselesstools.usefultools.debugging.DebugSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.Vec3Argument;

import java.util.Arrays;

public class MBEdebugCommand {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    if (dispatcher.findNode(Arrays.asList("mbedebug")) != null) return;
    LiteralArgumentBuilder<CommandSource> mbedebugCommand
      = Commands.literal("mbedebug")
            .then(Commands.literal("param")
                    .then(Commands.argument("parametername", StringArgumentType.word())
                            .suggests((context, builder) ->
                                    ISuggestionProvider.suggest(DebugSettings.listAllDebugParameters().stream(), builder))
                            .then(Commands.literal("clear")
                                    .executes(context -> {
                                      DebugSettings.clearDebugParameter(StringArgumentType.getString(context, "parametername"));
                                      return 1;
                                    })
                            )
                            .then(Commands.argument("parametervalue", DoubleArgumentType.doubleArg())
                                    .executes(context -> { DebugSettings.setDebugParameter(
                                              StringArgumentType.getString(context, "parametername"),
                                              DoubleArgumentType.getDouble(context, "parametervalue")); return 1;}))
                    )
            )
            .then(Commands.literal("paramvec3d")
                    .then(Commands.argument("parametername", StringArgumentType.word())
                            .suggests((context, builder) ->
                                    ISuggestionProvider.suggest(DebugSettings.listAllDebugParameterVec3ds().stream(), builder))
                            .then(Commands.literal("clear")
                                    .executes(context -> {
                                      DebugSettings.clearDebugParameterVec3d(StringArgumentType.getString(context, "parametername"));
                                      return 1;
                                    })
                            )
                            .then(Commands.argument("parametervalue", Vec3Argument.vec3(false))  //don't automatically centre integers
                              .executes(context -> { DebugSettings.setDebugParameterVec3d(
                                      StringArgumentType.getString(context, "parametername"),
                                      Vec3Argument.getVec3(context, "parametervalue")); return 1;}))
                    )
            )
            .then(Commands.literal("trigger")
                    .then(Commands.argument("parametername", StringArgumentType.word())
                            .suggests((context, builder) ->
                                    ISuggestionProvider.suggest(DebugSettings.listAllDebugTriggers().stream(), builder))
                            .executes(context -> { DebugSettings.setDebugTrigger(
                                    StringArgumentType.getString(context, "parametername")); return 1;})
                    )
            )
            .then(Commands.literal("test")
                        .then(Commands.argument("testnumber", IntegerArgumentType.integer())
                          .executes(context -> { DebugSettings.setDebugTest(
                                  IntegerArgumentType.getInteger(context, "testnumber")); return 1;}))
            );
    dispatcher.register(mbedebugCommand);
  }
}
