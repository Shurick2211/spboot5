package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.services.TelegramBotListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CursCommand extends CommandPattern {


    public CursCommand(SendMessService sendMessService, TelegramBotListener botListener) {
        super(sendMessService, botListener);
    }

    @Override
    public void execute(Message message) {
        String mess = "Курс = " + botListener.curs();
        sendMessService.send(message.getChatId().toString(), mess);
    }
}
