package tv.twitch.android.mod.models;

import androidx.annotation.NonNull;

public interface PreferenceItem {
    String getValue();

    String getKey();

    String getName();

    PreferenceItem lookup(String name);

    PreferenceItem getDefault();
}
