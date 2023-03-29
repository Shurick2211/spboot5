package com.sn.org.spboot5.telegram_bot.command_service;


import com.google.common.collect.ImmutableMap;
import com.sn.org.spboot5.services.TelegramBotListener;
import com.sn.org.spboot5.telegram_bot.command.Button;
import com.sn.org.spboot5.telegram_bot.command.CursCommand;
import com.sn.org.spboot5.telegram_bot.command.HelpCommand;
import com.sn.org.spboot5.telegram_bot.command.Inline;
import com.sn.org.spboot5.telegram_bot.command.NoCommand;
import com.sn.org.spboot5.telegram_bot.command.StartCommand;
import com.sn.org.spboot5.telegram_bot.command.StopCommand;
import com.sn.org.spboot5.telegram_bot.command.UnknowCommand;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;


public class CommandBox {
    private final UnknowCommand unknowCommand;
    ;
    private final ImmutableMap<String, Command> commandMap;

    public CommandBox(SendMessService sendMessService, TelegramBotListener botListener) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getComName(), new StartCommand(sendMessService, botListener))
                .put(CommandName.CURS.getComName(), new CursCommand(sendMessService, botListener))
                .put(CommandName.HELP.getComName(), new HelpCommand(sendMessService, botListener))
                .put(CommandName.NO.getComName(), new NoCommand(sendMessService, botListener))
                .put(CommandName.BUTTON.getComName(), new Button(sendMessService, botListener))
                .put(CommandName.INLINE.getComName(), new Inline(sendMessService, botListener))
                .put(CommandName.STOP.getComName(), new StopCommand(sendMessService, botListener))
                .build();
        unknowCommand = new UnknowCommand(sendMessService, botListener);

    }
    public Command useCommand(String identification){
        return commandMap.getOrDefault(identification, unknowCommand);
    }
}