package com.sn.org.spboot5.telegram_bot.command;

import com.sn.org.spboot5.services.TelegramBotCommandListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandBox;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessButton;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class SellCommand extends CommandPattern {

  public SellCommand(SendMessService sendMessService,
      TelegramBotCommandListener botListener) {
    super(sendMessService, botListener);
  }

  @Override
  public void execute(Message message) {
    sendMessService.send(message.getChatId().toString(), botListener.sell(
        String.valueOf(message.getChatId())));
    String mess = "/buy - Початок автоторгівлі=="
        + "/info - Інформація з кошелька!==/curs - Отримати поточний курс!";
    message.setText(mess);
    CommandBox commandBox = new CommandBox(new SendMessButton(sendMessService.getBot()),botListener);
    commandBox.useCommand("BUTTON").execute(message);
  }
}
