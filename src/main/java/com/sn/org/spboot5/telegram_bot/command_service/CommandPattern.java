package com.sn.org.spboot5.telegram_bot.command_service;


import com.sn.org.spboot5.services.TelegramBotListener;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class CommandPattern {
    protected final SendMessService sendMessService;
    protected final TelegramBotListener botListener;

    public CommandPattern(SendMessService sendMessService, TelegramBotListener botListener) {
        this.sendMessService = sendMessService;
        this.botListener = botListener;
    }
    public abstract void execute(Message message);
}
