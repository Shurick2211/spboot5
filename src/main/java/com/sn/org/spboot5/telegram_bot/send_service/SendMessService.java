package com.sn.org.spboot5.telegram_bot.send_service;


import com.sn.org.spboot5.telegram_bot.Bot;

public interface SendMessService {
    void send(String chatId, String mess);
    Bot getBot();
}
