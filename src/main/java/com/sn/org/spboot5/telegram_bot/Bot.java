package com.sn.org.spboot5.telegram_bot;



import com.sn.org.spboot5.telegram_bot.command_service.CommandBox;
import com.sn.org.spboot5.telegram_bot.command_service.CommandName;
import com.sn.org.spboot5.telegram_bot.send_service.SendMess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.name}")
    private String botName;



    @Override
    public void onUpdateReceived(Update update) {
        CommandBox commandBox = new CommandBox(new SendMess(this));
        if(update.hasMessage()) {
            String message = update.getMessage().getText().trim();
            log.info("{} {} : {}",update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getUserName(),message);
            if (message.startsWith("/")) {
                String identification = message.split(" ")[0].toLowerCase();
                commandBox.useCommand(identification).execute(update.getMessage());
            } else {
                commandBox.useCommand(CommandName.NO.name()).execute(update.getMessage());
            }
        }
        /* else

        if(update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("list")){
            long chatId = update.getCallbackQuery().getFrom().getId();
            db.save(new Person(chatId,
                    update.getCallbackQuery().getFrom().getFirstName(),
                    update.getCallbackQuery().getFrom().getLastName(),
                    update.getCallbackQuery().getFrom().getUserName()
                    ));
            String mess = db.findAll().stream()
                    .map(Person::toString)
                    .reduce((x,y) -> x.replace("null","")
                            + "\n" + y.replace("null",""))
                    .orElse("");
            new SendMess(this).send(String.valueOf(chatId),mess);
        }

 */
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
