package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.services.TelegramBotListener;
import com.sn.org.spboot5.telegram_bot.command_service.Command;
import com.sn.org.spboot5.telegram_bot.command_service.CommandName;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import java.util.Arrays;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelpCommand implements Command {
    private final SendMessService sendMessService;
    private final TelegramBotListener botListener;
    public HelpCommand(SendMessService sendMessService, TelegramBotListener botListener) {
        this.sendMessService = sendMessService;
        this.botListener = botListener;
    }

    @Override
    public void execute(Message message) {
        String mess="Команди бота: \n" +
                Arrays.stream(CommandName.values())
                        .filter(c -> !(c.equals(CommandName.BUTTON)
                                || c.equals(CommandName.STOP)
                                || c.equals(CommandName.INLINE)
                                || c.equals(CommandName.NO)))
                        .map(CommandName::getComName)
                        .reduce((x,y) ->  x + "\n" + y)
                        .orElse("");
        sendMessService.send(message.getChatId().toString(),mess);
    }
}
