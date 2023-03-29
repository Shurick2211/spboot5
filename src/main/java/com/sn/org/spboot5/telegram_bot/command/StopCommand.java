package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.services.TelegramBotListener;
import com.sn.org.spboot5.telegram_bot.command_service.Command;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
@Slf4j
public class StopCommand implements Command {
    private final SendMessService sendMessService;
    private final TelegramBotListener botListener;

    public StopCommand(SendMessService sendMessService, TelegramBotListener botListener) {
        this.sendMessService = sendMessService;
        this.botListener = botListener;
    }

    @Override
    public void execute(Message message) {
        String mess = "Зупинка Бота!";
        sendMessService.send(message.getChatId().toString(), mess);
        log.warn("Bot was stopped!");
        System.exit(0);
    }
}
