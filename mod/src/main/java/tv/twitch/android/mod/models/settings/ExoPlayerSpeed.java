package tv.twitch.android.mod.models.settings;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PreferenceManager;

public enum ExoPlayerSpeed implements PreferenceItem {
    DEFAULT("Default", "1.0"),
    SPEED110("1.1x", "1.1"),
    SPEED115("1.15x", "1.15"),
    SPEED120("1.20x", "1.20"),
    SPEED125("1.25x", "1.25"),
    SPEED150("1.5x", "1.5"),
    SPEED175("1.75x", "1.75"),
    SPEED200("2.0x", "2.0");

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
