package com.sn.org.spboot5.telegram_bot.command;

import com.sn.org.spboot5.services.TelegramBotCommandListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandPattern;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessButton;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class BuyCommand extends CommandPattern {

  public BuyCommand(SendMessService sendMessService,
      TelegramBotCommandListener botListener) {
    super(sendMessService, botListener);
    this.sendMessService = new SendMessButton(sendMessService.getBot());
  }

  @Override
  public void execute(Message message) {
    String mess = botListener.buy(String.valueOf(message.getChatId()))
        + "=/=/buy - Купити зараз BTC!=="
        + "/sell - Продати зараз BTC!=="
        + "/stop_game - Зупинити авто торгівлю=="
        + "/info - Інформація з кошелька!==/curs - Отримати поточний курс!";
    sendMessService.send(message.getChatId().toString(), mess);

  }
}
