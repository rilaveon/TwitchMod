package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.content.res.Resources;
import android.util.LruCache;

import java.util.HashMap;

import tv.twitch.android.mod.R;
import tv.twitch.android.mod.utils.Logger;


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

    public static Integer getId(String name) {
        PubCache cache = INSTANCE.mCaches.get(IdType.ID);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public static Integer getDrawableId(String name) {
        PubCache cache = INSTANCE.mCaches.get(IdType.DRAWABLE);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public static Integer getRawId(String name) {
        PubCache cache = INSTANCE.mCaches.get(IdType.RAW);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public static Integer getLayoutId(String name) {
        PubCache cache = INSTANCE.mCaches.get(IdType.LAYOUT);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public static Integer getStringId(String name) {
        PubCache cache = INSTANCE.mCaches.get(IdType.STRING);
        if (cache != null)
            return cache.get(name);

        return 0;
    }

    public static String getStringById(Integer id) {
        return LoaderLS.getInstance().getResources().getString(id);
    }

    public static String[] getStringArray(String name) {
        int resId = LoaderLS.getInstance().getResources().getIdentifier(name, "array", LoaderLS.getInstance().getPackageName());
        if (resId == -1) {
            Logger.error("resId == -1, name=" + name);
            return new String[0];
        }

        try {
            return LoaderLS.getInstance().getResources().getStringArray(resId);
        } catch (Resources.NotFoundException ex) {
            ex.printStackTrace();
        }

        return new String[0];
    }

    public static String getString(String name) {
        PubCache cache = INSTANCE.mCaches.get(IdType.STRING);

        int resId = cache != null ? cache.get(name) : 0;
        if (resId == 0) {
            return "STRING RESOURCE NOT FOUND: '" + name + "'";
        } else {
            return LoaderLS.getInstance().getResources().getString(resId);
        }
    }
}
