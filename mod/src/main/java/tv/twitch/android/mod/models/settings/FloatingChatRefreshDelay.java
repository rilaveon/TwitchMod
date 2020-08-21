package tv.twitch.android.mod.models.settings;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PreferenceManager;


public enum FloatingChatRefreshDelay implements PreferenceItem {
    DEFAULT("default", "800"),
    MUL1("1.25x", "650"),
    MUL2("1.5x", "550"),
    MUL3("1.75x", "450"),
    MUL4("2x", "400");

    public final String mPreferenceName;
    public final String mPreferenceValue;

    private FloatingChatRefreshDelay(String name, String preferenceValue) {
        this.mPreferenceName = name;
        this.mPreferenceValue = preferenceValue;
    }

    @Override
    public String getValue() {
        return this.mPreferenceValue;
    }

    @Override
    public String getKey() {
        return PreferenceManager.FLOATING_CHAT_REFRESH;
    }

    @Override
    public String getName() {
        return this.mPreferenceName;
    }

    @Override
    public PreferenceItem lookup(String value) {
        PreferenceItem[] values = values();
        for (PreferenceItem item : values) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return this;
    }

    public String toString() {
        return getName();
    }

    @Override
    public PreferenceItem getDefault() {
        return DEFAULT;
    }
}