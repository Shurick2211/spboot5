package com.sn.org.spboot5.telegram_bot.command;


import com.sn.org.spboot5.services.TelegramBotCommandListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessButton;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StopGameCommand extends CommandPattern {


    public StopGameCommand(SendMessService sendMessService, TelegramBotCommandListener botListener) {
        super(sendMessService, botListener);
        this.sendMessService = new SendMessButton(sendMessService.getBot());
    }

    @Override
    public void execute(Message message) {
        String mess = message.getText()
            + "=/=/buy - Початок автоторгівлі==/sell - Продати зараз=="
            + "/info - Інформація з кошелька!==/curs - Отримати поточний курс!";
        message.setText(mess);
        sendMessService.send(message.getChatId().toString(), mess);
    }
}
