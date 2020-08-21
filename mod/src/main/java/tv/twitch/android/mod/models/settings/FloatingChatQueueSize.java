package tv.twitch.android.mod.models.settings;


import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PreferenceManager;


public enum FloatingChatQueueSize implements PreferenceItem {
    THREE("3", "3"),
    FOUR("4", "4"),
    FIVE("5", "5"),
    SIX("6", "6"),
    SEVEN("7", "7");

    public final String mPreferenceName;
    public final String mPreferenceValue;

    FloatingChatQueueSize(String name, String preferenceValue) {
        this.mPreferenceName = name;
        this.mPreferenceValue = preferenceValue;
    }

    @Override
    public String getValue() {
        return this.mPreferenceValue;
    }

    @Override
    public String getKey() {
        return PreferenceManager.FLOATING_CHAT_QSIZE;
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
        return THREE;
    }
}