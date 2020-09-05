package tv.twitch.android.mod.models;


import androidx.annotation.NonNull;

import tv.twitch.android.mod.models.preferences.EmoteSize;


public interface Emote {
    @NonNull
    String getCode();

    String getUrl(@EmoteSize int size);

    boolean isGif();
}
