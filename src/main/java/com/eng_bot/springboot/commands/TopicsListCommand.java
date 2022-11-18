package com.eng_bot.springboot.commands;

import com.eng_bot.springboot.db.GetRows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TopicsListCommand  extends BotCommand {

    public TopicsListCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        GetRows getRows = new GetRows();
        ArrayList<String> topicsList = getRows.getTopics();
        StringBuilder messageText = new StringBuilder();
        for (String topic: topicsList) {
            messageText.append(topic).append(", ");
        }

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        message.setText(messageText.toString());

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
