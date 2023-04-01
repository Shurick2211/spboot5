package com.sn.org.spboot5.telegram_bot;


import com.sn.org.spboot5.models.Person;
import com.sn.org.spboot5.services.TelegramBotCommandListener;
import com.sn.org.spboot5.telegram_bot.command_service.CommandBox;
import com.sn.org.spboot5.telegram_bot.command_service.CommandName;
import com.sn.org.spboot5.telegram_bot.send_service.SendMess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
        if(update.hasMessage()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith("/")) {
                String identification = message.split(" ")[0].toLowerCase();
                commandBox.useCommand(identification).execute(update.getMessage());
            } else {
                commandBox.useCommand(CommandName.NO.name()).execute(update.getMessage());
            }
        }
        log.info(update.getMessage().toString());

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
