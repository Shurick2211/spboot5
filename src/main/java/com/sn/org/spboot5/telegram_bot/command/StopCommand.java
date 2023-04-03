package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.services.TelegramBotCommandListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
@Slf4j
public class StopCommand extends CommandPattern {


    public StopCommand(SendMessService sendMessService, TelegramBotCommandListener botListener) {
        super(sendMessService, botListener);
    }

    @Override
    public void execute(Message message) {
        botListener.stopBot(String.valueOf(message.getChatId()));
        String mess = "Зупинка Бота!";
        sendMessService.send(message.getChatId().toString(), mess);
        log.warn("Bot was stopped!");
    }
}
