package com.sn.org.spboot5.telegram_bot.send_service;

import com.sn.org.spboot5.telegram_bot.Bot;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
@Slf4j
public class SendMessButton implements SendMessService{

    private final Bot bot;

    public SendMessButton(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void send(String chatId, String mess) {
        String name=mess.trim();
        String [] btn=name.split("=/=");
        String [] names=btn[1].split("==");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        sendMessage.enableHtml(true);
        sendMessage.setText(btn[0]);

        try {
            setButtons(sendMessage, names);
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Send button error", e);
        }
    }

    @Override
    public Bot getBot() {
        return bot;
    }

    public synchronized void setButtons(SendMessage sendMessage, String [] names) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardButton button;
            for (String name:names){
                if (name.startsWith("Регістрація!")){
                     button = new KeyboardButton(name);
                     button.setWebApp(WebAppInfo.builder().url("https://shurick2211.github.io/telega-web-app/").build());
                } else{
                    button = new KeyboardButton(name);
                }
                newKeyboardRowWithButton(keyboard, button);
            }
        button = new KeyboardButton("Binance");
        button.setWebApp(WebAppInfo.builder().url("https://www.binance.com/ru-UA/trade/BTC_USDT?theme=dark&type=spot").build());
        newKeyboardRowWithButton(keyboard, button);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private void newKeyboardRowWithButton(List<KeyboardRow> keyboard, KeyboardButton button) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(button);
        keyboard.add(keyboardRow);
    }


}
