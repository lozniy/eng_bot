package com.eng_bot.springboot.commands;

public class UserSettings {
    private boolean isRus;
    private boolean isEng;
    private String topic;
    private String[] currentWordData;

    public boolean isRus() {
        return isRus;
    }

    public void setRus(boolean rus) {
        isRus = rus;
    }

    public boolean isEng() {
        return isEng;
    }

    public void setEng(boolean eng) {
        isEng = eng;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String[] getCurrentWordData() {
        return currentWordData;
    }

    public void setCurrentWordData(String[] currentWordData) {
        this.currentWordData = currentWordData;
    }

    public boolean isSettingsSet() {
        return (isRus() || isEng()) && getTopic() != null;
    }

    public String getWordToGuess() {
        int index = isEng ? 1 : 2;
        return currentWordData[index];
    }

    public boolean isTransCorrect(String word) {
        int wordIndexToCheck = isEng ? 2 : 1;
        String wordToCheck = currentWordData[wordIndexToCheck];
        return wordToCheck.equals(word);
    }
}
