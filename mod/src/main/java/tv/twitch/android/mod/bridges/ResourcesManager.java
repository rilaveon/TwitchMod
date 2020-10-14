package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.content.res.Resources;
import android.util.LruCache;

import java.util.HashMap;


public class ResourcesManager {
    public static final ResourcesManager INSTANCE = new ResourcesManager();

    private final HashMap<IdType, PubCache> mCaches = new HashMap<>();


    private enum IdType {
        STRING("string"),
        ID("id"),
        RAW("raw"),
        LAYOUT("layout"),
        DRAWABLE("drawable");

        private final String mType;

        IdType(String type) {
            mType = type;
        }

        public String getType() {
            return mType;
        }
    }

    private ResourcesManager () {
        for (IdType idType : IdType.values()) {
            mCaches.put(idType, new PubCache(200, idType.getType()));
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

    public Integer getId(String name) {
        PubCache cache = mCaches.get(IdType.ID);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public Integer getDrawableId(String name) {
        PubCache cache = mCaches.get(IdType.DRAWABLE);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public Integer getRawId(String name) {
        PubCache cache = mCaches.get(IdType.RAW);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public Integer getLayoutId(String name) {
        PubCache cache = mCaches.get(IdType.LAYOUT);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public Integer getStringId(String name) {
        PubCache cache = mCaches.get(IdType.STRING);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public String getStringById(Integer id) {
        return LoaderLS.getInstance().getResources().getString(id);
    }

    public String getString(String name) {
        PubCache cache = mCaches.get(IdType.STRING);

        int resId = cache != null ? cache.get(name) : 0;
        if (resId == 0) {
            return "STRING RESOURCE NOT FOUND: '" + name + "'";
        } else {
            return LoaderLS.getInstance().getResources().getString(resId);
        }
    }
}
