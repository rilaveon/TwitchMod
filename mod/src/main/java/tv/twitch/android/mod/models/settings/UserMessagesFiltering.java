package tv.twitch.android.mod.models.settings;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PreferenceManager;

public enum UserMessagesFiltering implements PreferenceItem {
    DISABLED("Disabled", "disabled"),
    PLEBS("Plebs", "plebs"),
    SUBS("Subs", "subs"),
    MODS("Mods", "mods"),
    BROADCASTER("Broadcaster", "broadcaster");

    public final String mPreferenceName;
    public final String mPreferenceValue;


    UserMessagesFiltering(String name, String preferenceValue) {
        this.mPreferenceName = name;
        this.mPreferenceValue = preferenceValue;
    }

    @Override
    public String getValue() {
        return mPreferenceValue;
    }

    @Override
    public String getKey() {
        return PreferenceManager.USER_MESSAGES_FILTERING;
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
        return DISABLED;
    }
}
