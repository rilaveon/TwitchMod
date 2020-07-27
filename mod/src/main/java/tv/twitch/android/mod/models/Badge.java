package tv.twitch.android.mod.models;


public final class Badge {
    private final String mName;
    private final String mUrl;
    private final String mReplaces;


    public Badge(String name, String url, String replaces) {
        mName = name;
        mUrl = url;
        mReplaces = replaces;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getName() {
        return mName;
    }

    public String getReplaces() {
        return mReplaces;
    }
}
