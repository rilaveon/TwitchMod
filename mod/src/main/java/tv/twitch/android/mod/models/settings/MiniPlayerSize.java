package tv.twitch.android.mod.models.settings;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PreferenceManager;

public enum MiniPlayerSize implements PreferenceItem {
    DEFAULT("Default", "1.0"),
    SIZE1("1.25", "1.25"),
    SIZE2("1.35", "1.35"),
    SIZE3("1.5", "1.5"),
    SIZE4("1.65", "1.65"),
    SIZE5("1.75", "1.75"),
    SIZE6("1.85", "1.85"),
    SIZE7("2.0", "2.0");

    public final String mPreferenceName;
    public final String mPreferenceValue;


    MiniPlayerSize(String name, String preferenceValue) {
        this.mPreferenceName = name;
        this.mPreferenceValue = preferenceValue;
    }

    @Override
    public String getValue() {
        return mPreferenceValue;
    }

    @Override
    public String getKey() {
        return PreferenceManager.MINIPLAYER_SIZE;
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
