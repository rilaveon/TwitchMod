package tv.twitch.android.mod.models.settings;


import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PreferenceManager;


public enum MsgDelete implements PreferenceItem {
    DEFAULT("Default", "default"),
    MOD("Reveal", "mod"),
    STRIKETHROUGH("Strikethrough", "strikethrough");

    public final String mPreferenceName;
    public final String mPreferenceValue;


    MsgDelete(String name, String preferenceValue) {
        this.mPreferenceName = name;
        this.mPreferenceValue = preferenceValue;
    }

    @Override
    public String getValue() {
        return mPreferenceValue;
    }

    @Override
    public String getKey() {
        return PreferenceManager.MSG_DELETE;
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
