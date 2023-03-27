package com.sn.org.spboot5.telegram_bot.command_service;


import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {

    void execute(Message message);
}
