package tv.twitch.android.mod.emotes;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.EmoteSet;
import tv.twitch.android.mod.utils.Logger;


public class BaseEmoteSet implements EmoteSet {
    private final Map<String, Emote> mEmoteMap = Collections.synchronizedMap(new LinkedHashMap<String, Emote>());

    @Override
    public void addEmote(@NonNull Emote emote) {
        if (TextUtils.isEmpty(emote.getCode())) {
            Logger.debug("empty code");
            return;
        }

        mEmoteMap.put(emote.getCode(), emote);
    }

    @Override
    public Emote getEmote(String name) {
        return mEmoteMap.get(name);
    }

    @Override
    @NonNull
    public Collection<Emote> getEmotes() {
        return mEmoteMap.values();
    }

    @Override
    public boolean isEmpty() {
        return mEmoteMap.isEmpty();
    }
}
