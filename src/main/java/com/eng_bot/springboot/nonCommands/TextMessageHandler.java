package com.eng_bot.springboot.nonCommands;

import com.eng_bot.springboot.commands.CommandDataStorage;
import com.eng_bot.springboot.commands.UserSettings;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TextMessageHandler {

    private final UserSettings userSettings;
    private final Update update;

    public TextMessageHandler(Update update) {
        this.update = update;
        this.userSettings = CommandDataStorage.getStorage().getUserSettings(update.getMessage().getFrom().getId());
    }
    public String processTextMessage() {

        String msg = update.getMessage().getText();
        String[] parts = msg.split(" ");
        String subCommand = parts[0].toLowerCase();
        String data = parts[1].toLowerCase();
        String response;
        switch (subCommand) {
            case "lang" -> response = setLangSettings(data);
            case "topic" -> {
                userSettings.setTopic(data);
                response = "Topic was set";
            }
            default -> response = userSettings.isTransCorrect(subCommand) ? "Correct" : "Incorrect";
        }

        return response;
    }

    private String setLangSettings(String msg) {
        String response;
        switch (msg) {
            case "ru" -> {
                userSettings.setRus(true);
                userSettings.setEng(false);
                response = "set RU to EN";
            }
            case "en" -> {
                userSettings.setEng(true);
                userSettings.setRus(false);
                response = "set EN to RU";
            }
            default -> response = "Did not get you";
        }
        return response;
    }
}
