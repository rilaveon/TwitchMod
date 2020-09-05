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
    private final Object lock = new Object();

    private static final int MAX_ROOMS = 20;

    private final RoomCache mRoomCache = new RoomCache(MAX_ROOMS);

    private final BttvGlobalFetcher mBttvGlobalFetcher;
    private BaseEmoteSet mBttvGlobalSet;

    private boolean isGlobalSetRequested = false;

    public static final EmoteManager INSTANCE = new EmoteManager();

    private EmoteManager() {
        mBttvGlobalFetcher = new BttvGlobalFetcher(this);
    }

    @Override
    public void onGlobalBttvEmotesParsed(BaseEmoteSet set) {
        if (set == null) {
            Logger.error("set is null");
            return;
        }

        mBttvGlobalSet = set;
        isGlobalSetRequested = false;
    }

    private static class RoomCache extends LruCache<Integer, Room> {
        public RoomCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected Room create(Integer key) {
            Room room = new Room(key);
            room.fetchEmotes();

            return room;
        }
    }

    public void requestRoomEmotes(int channelId) {
        if (channelId <= 0) {
            Logger.error("Bad channel id: " + channelId);
            return;
        }

        mRoomCache.get(channelId);
    }

    public void fetchGlobalEmotes() {
        if (mBttvGlobalSet == null && !isGlobalSetRequested) {
            synchronized (lock) {
                if (mBttvGlobalSet == null || mBttvGlobalSet.isEmpty()) {
                    mBttvGlobalFetcher.fetch();
                    isGlobalSetRequested = true;
                }
            }
        }
    }

    @NonNull
    public Collection<Emote> getGlobalEmotes() {
        if (mBttvGlobalSet == null)
            return Collections.emptyList();

        return mBttvGlobalSet.getEmotes();
    }

    @NonNull
    public Collection<Emote> getBttvEmotes(int channelId) {
        if (channelId <= 0) {
            Logger.error("Bad channel id: " + channelId);
            return Collections.emptyList();
        }

        return mRoomCache.get(channelId).getBttvEmotes();
    }

    @NonNull
    public Collection<Emote> getFfzEmotes(int channelId) {
        if (channelId <= 0) {
            Logger.error("Bad channel id: " + channelId);
            return Collections.emptyList();
        }

        return mRoomCache.get(channelId).getFfzEmotes();
    }

    public Emote findEmote(String code, int channelId) {
        if (TextUtils.isEmpty(code))
            return null;

        if (channelId > 0) {
            Emote emote = mRoomCache.get(channelId).findEmote(code);
            if (emote != null)
                return emote;
        }

        if (mBttvGlobalSet != null)
            return mBttvGlobalSet.getEmote(code);

        return null;
    }
}
