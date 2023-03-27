package com.sn.org.spboot5.telegram_bot.command;

import com.sn.org.spboot5.telegram_bot.command_service.Command;
import com.sn.org.spboot5.telegram_bot.command_service.CommandBox;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessButton;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartCommand implements Command {
    private final SendMessService sendMessService;

    public StartCommand(SendMessService sendMessService) {
        this.sendMessService = sendMessService;
    }

    @Override
    public void execute(Message message) {
        String mess = "Бот вітає Вас!\n" +
                "Вам потрібно сказати 'Привіт!'" +
                "\nЦе можна зробити кнопкою нижче";
        sendMessService.send(message.getChatId().toString(), mess);

        CommandBox commandBox = new CommandBox(new SendMessButton(sendMessService.getBot()));
        commandBox.useCommand("BUTTON").execute(message);
    }
}
