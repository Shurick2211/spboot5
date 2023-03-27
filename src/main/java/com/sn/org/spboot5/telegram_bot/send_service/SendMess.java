package com.sn.org.spboot5.telegram_bot.send_service;


import com.sn.org.spboot5.telegram_bot.Bot;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class SendMess implements SendMessService{

    private final Bot bot;

    public SendMess(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void send(String chatId, String mess) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(mess);
        try {
           bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Send Message error",e);
        }
    }

    public Bot getBot() {
        return bot;
    }
}
