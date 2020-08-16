package tv.twitch.android.mod.models.settings;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PreferenceManager;

public enum ChatWidthPercent implements PreferenceItem {
    DEFAULT("Default", "-1"),
    P5("5%", "5"),
    P10("10%", "10"),
    P15("15%", "15"),
    P20("20%", "20"),
    P25("25%", "25"),
    P830("30%", "30"),
    P35("35%", "35"),
    P40("40%", "40");


    public final String mPreferenceName;
    public final String mPreferenceValue;


    ChatWidthPercent(String name, String preferenceValue) {
        this.mPreferenceName = name;
        this.mPreferenceValue = preferenceValue;
    }

    @Override
    public String getValue() {
        return mPreferenceValue;
    }

    @Override
    public String getKey() {
        return PreferenceManager.CHAT_WIDTH_SCALE;
    }

    @Override
    public String getName() {
        return mPreferenceName;
    }

    @Override
    public PreferenceItem lookup(String value) {
        for (PreferenceItem item : values()) {
            if (item.getValue().equals(value))
                return item;
        }

        return this;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public PreferenceItem getDefault() {
        return DEFAULT;
    }
}
