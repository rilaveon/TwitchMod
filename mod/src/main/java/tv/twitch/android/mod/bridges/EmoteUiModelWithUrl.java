package tv.twitch.android.mod.bridges;

import tv.twitch.a.k.l.j.d;

public class EmoteUiModelWithUrl extends tv.twitch.a.k.l.j.k.c {
    final String mUrl;

    public EmoteUiModelWithUrl(String id, boolean isLockIconVisible, boolean isLongClickIconVisible, d.d1 clickedEmote, String url) {
        super(id, isLockIconVisible, isLongClickIconVisible, clickedEmote);
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
}
