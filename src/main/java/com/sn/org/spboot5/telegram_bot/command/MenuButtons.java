package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.services.TelegramBotCommandListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MenuButtons extends CommandPattern {


    public MenuButtons(SendMessService sendMessService, TelegramBotCommandListener botListener) {
        super(sendMessService, botListener);
    }

    @Override
    public void execute(Message message) {
        String mess = message.getText();
            //"/buy - Початок автоторгівлі==/sell - Продати і зупинити автоторгівлю=="
           // + "/info - Інформація з кошелька!==/curs - Отримати поточний курс!";
        sendMessService.send(message.getChatId().toString(), mess);
    }
}
