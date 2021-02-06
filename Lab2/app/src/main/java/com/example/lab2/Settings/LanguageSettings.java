package com.example.lab2.Settings;

public enum LanguageSettings {
    ENGLISH_RUS("английский"),
    ENGLISH_ENG("english"),
    LANGUAGE_ENG("en"),
    LANGUAGE_RUS("ru");

    private final String setting;

    LanguageSettings(String sett)
    {
        this.setting = sett;
    }

    public String getLanguage()
    {
        return setting;
    }
}
