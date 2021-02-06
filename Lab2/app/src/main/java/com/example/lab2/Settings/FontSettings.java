package com.example.lab2.Settings;

public enum FontSettings {
    MEDIUM(new String[]{"средний", "medium"}),
    LARGE(new String[]{"увеличенный", "large"});

    private final String [] setting;

    FontSettings(String[] sett)
    {
        this.setting = sett;
    }

    public String[] getFont()
    {
        return setting;
    }
}
