package com.sn.org.spboot5.telegram_bot.command;

import com.sn.org.spboot5.services.TelegramBotListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandBox;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessButton;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartCommand extends CommandPattern {


    public StartCommand(SendMessService sendMessService, TelegramBotListener botListener) {
        super(sendMessService, botListener);
    }

    @Override
    public void execute(Message message) {
        String mess = "Бот BTC-USD вітає Вас!\n" +
                "Купити/продати BTC";
        sendMessService.send(message.getChatId().toString(), mess);

        CommandBox commandBox = new CommandBox(new SendMessButton(sendMessService.getBot()),botListener);
        commandBox.useCommand("BUTTON").execute(message);
    }
}
