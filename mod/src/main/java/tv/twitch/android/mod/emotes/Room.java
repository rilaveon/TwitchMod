package tv.twitch.android.mod.emotes;


import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;

import tv.twitch.android.mod.emotes.fetchers.BttvChannelFetcher;
import tv.twitch.android.mod.emotes.fetchers.FfzChannelFetcher;
import tv.twitch.android.mod.models.Emote;


class Room implements BttvChannelFetcher.Callback, FfzChannelFetcher.Callback {
    private final int mChannelId;

    private final BttvChannelFetcher bttvChannelFetcher;
    private final FfzChannelFetcher ffzChannelFetcher;

    private BaseEmoteSet mBttvSet;
    private BaseEmoteSet mFfzSet;


    public Room(int channelId) {
        mChannelId = channelId;
        bttvChannelFetcher = new BttvChannelFetcher(mChannelId, this);
        ffzChannelFetcher = new FfzChannelFetcher(mChannelId, this);
    }

    public synchronized void requestEmotes() {
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
    public void bttvParsed(BaseEmoteSet set) {
        mBttvSet = set;
    }

    @Override
    public void ffzParsed(BaseEmoteSet set) {
        mFfzSet = set;
    }
}
