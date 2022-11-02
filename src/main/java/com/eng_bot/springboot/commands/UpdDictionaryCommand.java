package com.eng_bot.springboot.commands;

import com.eng_bot.springboot.DictionaryReader;
import com.eng_bot.springboot.db.ImportWordsData;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

public class UpdDictionaryCommand extends BotCommand {
    public UpdDictionaryCommand(String id, String description) {
        super(id, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        DictionaryReader reader = new DictionaryReader();
        ImportWordsData importWordsData = new ImportWordsData();
        int rowsAdded = 0;
        try {
            List<String[]> wordRows = reader.read();
            for (String[] row: wordRows) {
                rowsAdded += importWordsData.addRow(
                        row[0],
                        row[1],
                        row[2],
                        row[3],
                        row[4],
                        row[5]
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SendMessage message = new SendMessage();

        message.setChatId(chat.getId().toString());
        message.setText("Added rows: " + rowsAdded);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
