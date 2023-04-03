package com.sn.org.spboot5.telegram_bot;


import com.binance.connector.client.utils.JSONParser;
import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.services.TelegramBotCommandListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandBox;
import com.sn.org.spboot5.telegram_bot.command_service.CommandName;
import com.sn.org.spboot5.telegram_bot.send_service.SendMess;
import com.sn.org.spboot5.telegram_bot.send_service.SendMessButton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot  {
    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.name}")
    private String botName;

    @Autowired
    private TelegramBotCommandListener botListener;
    @Override
    public void onUpdateReceived(Update update) {
        CommandBox commandBox = new CommandBox(new SendMess(this), botListener);
        if(update.hasMessage() && update.getMessage().getWebAppData() == null) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith("/")) {
                String identification = message.split(" ")[0].toLowerCase();
                commandBox.useCommand(identification).execute(update.getMessage());
            } else {
                commandBox.useCommand(CommandName.NO.name()).execute(update.getMessage());
            }
        } else {
            String json = update.getMessage().getWebAppData().getData();
            log.info("chatId = {}, data = {}",update.getMessage().getChatId(),
                    json);
            Person person = new Person();
            person.setTelegramId(String.valueOf(update.getMessage().getChatId()));
            person.setApiKey(JSONParser.getJSONStringValue(json, "api_key"));
            person.setSecretKey(JSONParser.getJSONStringValue(json, "api_secret"));
            String mess = person
                + "=/=/buy - Початок автоторгівлі==/sell - Продати і зупинити автоторгівлю=="
                + "/info - Інформація з кошелька!==/curs - Отримати поточний курс!";
            Message message = update.getMessage();
            message.setText(mess);
            commandBox = new CommandBox(new SendMessButton(this), botListener);
            commandBox.useCommand(CommandName.BUTTON.name()).execute(message);
        }


    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    public void sendTelegram(Person person, String mess) {
        new SendMess(this).send(person.getTelegramId(),mess);
    }
}
