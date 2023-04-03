package com.sn.org.spboot5.telegram_bot.command;

import com.sn.org.spboot5.services.TelegramBotCommandListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandBox;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessButton;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartCommand extends CommandPattern {


    public StartCommand(SendMessService sendMessService, TelegramBotCommandListener botListener) {
        super(sendMessService, botListener);
        this.sendMessService = new SendMessButton(sendMessService.getBot());
    }

    @Override
    public void execute(Message message) {
        String mess = "Бот BTC-USD вітає Вас! Тут ви зможете:\n"
            + "Купити/продати BTC на Binance в автоматичному режимі!\n"
            + "Для цього пройдіть регістрацію!"
            + "=/=Регістрація!"
            + "==/curs - Отримати поточний курс!";
        sendMessService.send(message.getChatId().toString(), mess);
    }
}
