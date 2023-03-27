package com.sn.org.spboot5.telegram_bot.send_service;


import com.sn.org.spboot5.telegram_bot.Bot;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
@Slf4j
public class SendMessInline implements SendMessService{

    private final Bot bot;

    public SendMessInline(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void send(String chatId, String mess) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        sendMessage.enableHtml(true);
        sendMessage.setText(mess);

        try {
            setInButton(sendMessage, mess);
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
           log.error("Inline error", e);
        }
    }

    @Override
    public Bot getBot() {
        return bot;
    }

    private void setInButton(SendMessage sendMessage, String name) {

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setCallbackData("list");
        button.setText(name);
        buttonsRow.add(button);
        buttons.add(buttonsRow);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        inlineKeyboard.setKeyboard(buttons);
        sendMessage.setReplyMarkup(inlineKeyboard);
    }
}
