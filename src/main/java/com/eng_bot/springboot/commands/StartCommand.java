package com.eng_bot.springboot.commands;

import com.eng_bot.springboot.db.GetRows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class StartCommand extends BotCommand {
    public StartCommand(String id, String description) {
        super(id, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        CommandDataStorage storage = CommandDataStorage.getStorage();

        UserSettings settings = storage.getUserSettings(user.getId());
        String word = "";
        if (settings.isSettingsSet()) {
            GetRows getRows = new GetRows();
            ArrayList<String[]> topicsRow = getRows.getRowsByTopic(settings.getTopic());
            settings.setCurrentWordData(topicsRow.get(getRandomNum(0, topicsRow.size())));
            word = settings.getWordToGuess();
        } else {
            word = "Settings is not set";
        }

        message.setText(word);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private int getRandomNum(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
