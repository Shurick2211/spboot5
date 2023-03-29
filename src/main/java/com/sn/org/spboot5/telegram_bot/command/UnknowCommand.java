package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.services.TelegramBotListener;
import com.sn.org.spboot5.telegram_bot.command_service.Command;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class UnknowCommand implements Command {
    private final SendMessService sendMessService;
    private final TelegramBotListener botListener;

    public UnknowCommand(SendMessService sendMessService, TelegramBotListener botListener) {
        this.sendMessService = sendMessService;
        this.botListener = botListener;
    }

    @Override
    public void execute(Message message) {
        String mess = "Моя-твоя don`t understand! \n Допомога: /help";
        sendMessService.send(message.getChatId().toString(), mess);
    }
}
