package tv.twitch.android.mod.models;


import androidx.annotation.NonNull;

import java.util.Collection;


public interface EmoteSet {
    void addEmote(@NonNull Emote emote);

    Emote getEmote(String name);

    @NonNull
    Collection<Emote> getEmotes();

    boolean isEmpty();
}
