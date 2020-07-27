package tv.twitch.android.mod.emotes;


import android.text.TextUtils;
import android.util.LruCache;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;

import tv.twitch.android.mod.emotes.fetchers.BttvGlobalFetcher;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.utils.Logger;


public class EmoteManager implements BttvGlobalFetcher.Callback {
    private static final int MAX_CACHE_SIZE = 20;

    private final RoomCache mRoomCache = new RoomCache(MAX_CACHE_SIZE);
    private final BttvGlobalFetcher mBttvGlobalFetcher;

    private BaseEmoteSet mBttvGlobalSet;


    public static final EmoteManager INSTANCE = new EmoteManager();

    private EmoteManager() {
        mBttvGlobalFetcher = new BttvGlobalFetcher(this);
    }

    @Override
    public void globalBttvParsed(BaseEmoteSet set) {
        mBttvGlobalSet = set;
    }

    private static class RoomCache extends LruCache<Integer, Room> {
        public RoomCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected Room create(Integer key) {
            Room room = new Room(key);
            room.requestEmotes();

            return room;
        }
    }

    public void fetchGlobalEmotes() {
        if (mBttvGlobalSet == null) {
            mBttvGlobalFetcher.fetch();
        }
    }

    public void requestEmotes(int channelId) {
        if (channelId == 0) {
            Logger.warning("Cannot request emotes: channelId==0");
            return;
        }

        if (channelId == -1) {
            Logger.warning("Cannot request emotes: channelId==-1");
            return;
        }

        Logger.debug("New emote request: " + channelId);
        mRoomCache.get(channelId);
    }

    @NonNull
    public Collection<Emote> getGlobalEmotes() {
        if (mBttvGlobalSet != null) {
            return mBttvGlobalSet.getEmotes();
        }

        return Collections.emptyList();
    }

    @NonNull
    public Collection<Emote> getBttvEmotes(int channelId) {
        if (channelId == 0) {
            return Collections.emptyList();
        }

        return mRoomCache.get(channelId).getBttvEmotes();
    }

    @NonNull
    public Collection<Emote> getFfzEmotes(int channelId) {
        if (channelId == 0) {
            return Collections.emptyList();
        }

        return mRoomCache.get(channelId).getFfzEmotes();
    }

    public Emote getEmote(String code, int channelId) {
        if (TextUtils.isEmpty(code))
            return null;

        if (channelId != 0) {
            Emote emote = mRoomCache.get(channelId).findEmote(code);
            if (emote != null)
                return emote;
        }

        if (mBttvGlobalSet != null)
            return mBttvGlobalSet.getEmote(code);

        return null;
    }
}
