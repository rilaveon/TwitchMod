package tv.twitch.android.mod.bridges.models;


import tv.twitch.chat.ChatEmoticon;


public final class ChatEmoticonUrl extends ChatEmoticon {
    private final String mUrl;


    public ChatEmoticonUrl(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
}

