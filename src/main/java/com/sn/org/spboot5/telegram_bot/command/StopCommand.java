package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.services.TelegramBotListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
@Slf4j
public class StopCommand extends CommandPattern {


    public StopCommand(SendMessService sendMessService, TelegramBotListener botListener) {
        super(sendMessService, botListener);
    }

    @Override
    public void execute(Message message) {
        String mess = "Зупинка Бота!";
        sendMessService.send(message.getChatId().toString(), mess);
        log.warn("Bot was stopped!");
        System.exit(0);
    }
}
