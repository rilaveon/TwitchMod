package tv.twitch.android.mod.models;


import android.text.TextUtils;

import java.util.HashMap;

import tv.twitch.android.mod.bridges.ChatFactory;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.chat.ChatEmoticon;


public class FfzEmoteModel implements Emote {
    private final String mCode;
    private final String mId;
    
    private final String mSmallEmoteUrl;
    private final String mMediumEmoteUrl;
    private final String mLargeEmoteUrl;

    private final ChatEmoticon ce;

    public FfzEmoteModel(String code, String id, HashMap<String, String> urls) {
        this.mCode = code;
        this.mId = id;

        this.mSmallEmoteUrl = getUrl("1x", urls);
        this.mMediumEmoteUrl = getUrl("2x", urls);
        this.mLargeEmoteUrl = getUrl("4x", urls);

        this.ce = ChatFactory.getEmoticon(this);
    }

    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getUrl(EmoteSize size) {
        switch (size) {
            case LARGE:
                if (mLargeEmoteUrl != null)
                    return mLargeEmoteUrl;
            default:
            case MEDIUM:
                if (mMediumEmoteUrl != null)
                    return mMediumEmoteUrl;
            case SMALL:
                if (mSmallEmoteUrl != null)
                    return mSmallEmoteUrl;
        }

        return "";
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public boolean isGif() {
        return false;
    }

    @Override
    public ChatEmoticon getChatEmoticon() {
        return ce;
    }

    private String getUrl(String size, HashMap<String, String> urls) {
        if (urls.containsKey(size)) {
            String url = urls.get(size);
            if (TextUtils.isEmpty(url))
                return null;

            if (url.startsWith("//"))
                url = "https:" + url;

            return url;
        }

        return null;
    }

    @Override
    public String toString() {
        return "FfzEmoteModel{" +
                "mCode='" + mCode + '\'' +
                ", mId='" + mId + '\'' +
                ", mSmallEmoteUrl='" + mSmallEmoteUrl + '\'' +
                ", mMediumEmoteUrl='" + mMediumEmoteUrl + '\'' +
                ", mLargeEmoteUrl='" + mLargeEmoteUrl + '\'' +
                ", ce=" + ce +
                '}';
    }
}
