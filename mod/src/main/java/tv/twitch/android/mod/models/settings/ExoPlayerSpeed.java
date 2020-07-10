package tv.twitch.android.mod.models.settings;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PreferenceManager;

public enum ExoPlayerSpeed implements PreferenceItem {
    DEFAULT("Default", "1.0"),
    SPEED1("1.25", "1.25"),
    SPEED2("1.5", "1.5"),
    SPEED3("1.75", "1.75"),
    SPEED4("2.0", "2.0");

    public final String mPreferenceName;
    public final String mPreferenceValue;


    ExoPlayerSpeed(String name, String preferenceValue) {
        this.mPreferenceName = name;
        this.mPreferenceValue = preferenceValue;
    }

    @Override
    public String getValue() {
        return mPreferenceValue;
    }

    @Override
    public String getKey() {
        return PreferenceManager.EXOPLAYER_SPEED;
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
