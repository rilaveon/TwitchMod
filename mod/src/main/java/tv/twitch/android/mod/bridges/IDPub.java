package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.content.res.Resources;
import android.util.LruCache;


public class IDPub {
    public final PubCache mStringIdsCache = new PubCache(200, "string");

    public enum Ids { // TODO: __UPDATE
        PLAYER_OVERLAY_ID(0x7f0b0643),
        DEBUG_PANEL_CONTAINER_ID(0x7f0b02cc),
        FLOATING_CHAT_CONTAINER_ID(0x7f0b03c4),
        VIDEO_DEBUG_LIST_ID(0x7f0b0906),
        MESSAGES_CONTAINER_ID(0x7f0b04fd);

        private final int mId;

        Ids(int id) {
            mId = id;
        }

        public int getId() {
            return mId;
        }
    }

    private static class PubCache extends LruCache<String, Integer> {
        private final String mDefType;

        public PubCache(int maxSize, String defType) {
            super(maxSize);
            mDefType = defType;
        }

        @Override
        protected Integer create(String key) {
            Context context = LoaderLS.getInstance();
            Resources resources = context.getResources();
            return resources.getIdentifier(key, mDefType, context.getPackageName());
        }
    }

    public Integer getStringId(String name) {
        return mStringIdsCache.get(name);
    }

    public String getString(String name) {
        int resId = mStringIdsCache.get(name);
        if (resId == 0) {
            return "RESOURCE ID NOT FOUND: '" + name + "'";
        } else {
            return LoaderLS.getInstance().getResources().getString(resId);
        }
    }
}
