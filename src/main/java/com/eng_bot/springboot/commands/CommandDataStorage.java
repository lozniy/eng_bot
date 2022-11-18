package com.eng_bot.springboot.commands;

import java.util.HashMap;

public class CommandDataStorage {
    private static CommandDataStorage storage;
    private final HashMap<Long, UserSettings> dataStorage = new HashMap<>();
    public static CommandDataStorage getStorage() {
        if (null == storage) {
            storage = new CommandDataStorage();
        }
        return storage;
    }

    public UserSettings getUserSettings(Long userId) {
        if (dataStorage.containsKey(userId)) {
            return dataStorage.get(userId);
        }
        UserSettings settings = new UserSettings();
        dataStorage.put(userId, settings);
        return settings;
    }
}
