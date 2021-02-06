package com.example.lab2.Settings;

public enum AnotherSettings {
    LANGUAGE_SETTINGS ("language"),
    FONT_SETTINGS ("font"),
    THEME_SETTINGS ("theme"),
    DELETE_FUNCTION("DeleteAll");

    private final String setting;

    AnotherSettings(String sett)
    {
        this.setting = sett;
    }

    public String getSetting()
    {
        return setting;
    }
}
