package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.telegram_bot.command_service.Command;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class TestCommand implements Command {
    private final SendMessService sendMessService;

    public TestCommand(SendMessService sendMessService) {
        this.sendMessService = sendMessService;
    }

    @Override
    public void execute(Message message) {
        String mess = "Це просто перевірка!";
        sendMessService.send(message.getChatId().toString(), mess);
    }
}
