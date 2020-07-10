package tv.twitch.android.mod.models;

public interface PreferenceItem {
    String getValue();

    String getKey();

    String getName();

    PreferenceItem lookup(String name);

    PreferenceItem getDefault();
}
