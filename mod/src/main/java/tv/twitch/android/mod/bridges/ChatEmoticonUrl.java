package tv.twitch.android.mod.bridges;

import tv.twitch.chat.ChatEmoticon;

public class ChatEmoticonUrl extends ChatEmoticon {
    private final String mUrl;


    public ChatEmoticonUrl(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
}

