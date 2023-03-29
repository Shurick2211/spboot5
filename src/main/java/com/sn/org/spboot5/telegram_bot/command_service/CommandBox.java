package com.sn.org.spboot5.telegram_bot.command_service;


import com.google.common.collect.ImmutableMap;
import com.sn.org.spboot5.telegram_bot.command.Button;
import com.sn.org.spboot5.telegram_bot.command.HelpCommand;
import com.sn.org.spboot5.telegram_bot.command.Inline;
import com.sn.org.spboot5.telegram_bot.command.NoCommand;
import com.sn.org.spboot5.telegram_bot.command.StartCommand;
import com.sn.org.spboot5.telegram_bot.command.StopCommand;
import com.sn.org.spboot5.telegram_bot.command.TestCommand;
import com.sn.org.spboot5.telegram_bot.command.UnknowCommand;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.springframework.stereotype.Component;


public class CommandBox {
    private final UnknowCommand unknowCommand;
    private final ImmutableMap<String, Command> commandMap;

    public CommandBox(SendMessService sendMessService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getComName(), new StartCommand(sendMessService))
                .put(CommandName.TEST.getComName(), new TestCommand(sendMessService))
                .put(CommandName.HELP.getComName(), new HelpCommand(sendMessService))
                .put(CommandName.NO.getComName(), new NoCommand(sendMessService))
                .put(CommandName.BUTTON.getComName(), new Button(sendMessService))
                .put(CommandName.INLINE.getComName(), new Inline(sendMessService))
                .put(CommandName.STOP.getComName(), new StopCommand(sendMessService))
                .build();
        unknowCommand = new UnknowCommand(sendMessService);

    }
    public Command useCommand(String identification){
        return commandMap.getOrDefault(identification, unknowCommand);
    }
}