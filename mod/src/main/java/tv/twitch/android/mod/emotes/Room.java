package tv.twitch.android.mod.emotes;


import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;

import tv.twitch.android.mod.emotes.fetchers.BttvChannelFetcher;
import tv.twitch.android.mod.emotes.fetchers.FfzChannelFetcher;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.utils.Logger;


class Room implements BttvChannelFetcher.Callback, FfzChannelFetcher.Callback {
    private final BttvChannelFetcher bttvChannelFetcher;
    private final FfzChannelFetcher ffzChannelFetcher;

    private BaseEmoteSet mBttvSet;
    private BaseEmoteSet mFfzSet;

    public Room(int channelId) {
        bttvChannelFetcher = new BttvChannelFetcher(channelId, this);
        ffzChannelFetcher = new FfzChannelFetcher(channelId, this);
    }

    public synchronized void fetchEmotes() {
        bttvChannelFetcher.fetch();
        ffzChannelFetcher.fetch();
    }

    public final Emote findEmote(String emoteName) {
        if (mBttvSet != null) {
            Emote emote = mBttvSet.getEmote(emoteName);
            if (emote != null)
                return emote;
        }

        if (mFfzSet != null) {
            return mFfzSet.getEmote(emoteName);
        }

        return null;
    }

    @NonNull
    public final Collection<Emote> getBttvEmotes() {
        if (mBttvSet != null)
            return mBttvSet.getEmotes();

        return Collections.emptyList();
    }

    @NonNull
    public final Collection<Emote> getFfzEmotes() {
        if (mFfzSet != null)
            return mFfzSet.getEmotes();

        return Collections.emptyList();
    }

    @Override
    public void onBttvEmotesParsed(BaseEmoteSet set) {
        if (set == null) {
            Logger.error("set is null");
            return;
        }

        mBttvSet = set;
    }

    @Override
    public void onFfzEmotesParsed(BaseEmoteSet set) {
        if (set == null) {
            Logger.error("set is null");
            return;
        }

        mFfzSet = set;
    }
}
