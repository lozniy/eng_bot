package com.eng_bot.springboot;

import com.eng_bot.springboot.commands.SettingsCommand;
import com.eng_bot.springboot.commands.StartCommand;
import com.eng_bot.springboot.commands.TopicsListCommand;
import com.eng_bot.springboot.commands.UpdDictionaryCommand;
import com.eng_bot.springboot.nonCommands.TextMessageHandler;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EngBot extends TelegramLongPollingCommandBot {
    public static String BOT_TOKEN = "";
    public static String BOT_USERNAME = "";

    public EngBot() {
        register(new StartCommand("start", "Старт"));
        register(new UpdDictionaryCommand("upd_dictionary", "Обновить словарь"));
        register(new SettingsCommand("settings", "Задать настройки"));
        register(new TopicsListCommand("topics", "Список топиков"));
    }
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasDocument()) {
            GetFile getFile = new GetFile();
            getFile.setFileId(update.getMessage().getDocument().getFileId());
            try {
                File file = execute(getFile);
                downloadFile(file, new java.io.File("./data/docs/dic.csv"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("file received");

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            TextMessageHandler textMessageHandler = new TextMessageHandler(update);
            String responseText = textMessageHandler.processTextMessage();

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(responseText);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (update.hasCallbackQuery()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            message.setText(update.getCallbackQuery().getData());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
