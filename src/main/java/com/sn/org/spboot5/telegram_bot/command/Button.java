package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.services.TelegramBotListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class Button extends CommandPattern {


    public Button(SendMessService sendMessService, TelegramBotListener botListener) {
        super(sendMessService, botListener);
    }

    @Override
    public void execute(Message message) {
        String mess = "/buy Початок авто торгівлі=/sell Продати і зупинити автоторгівлю=/curs Отримати поточний курс!";
        sendMessService.send(message.getChatId().toString(), mess);
    }
}
