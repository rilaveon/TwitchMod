package tv.twitch.android.mod.models;


import androidx.annotation.NonNull;

import tv.twitch.android.mod.bridges.ChatFactory;
import tv.twitch.android.mod.models.api.ImageType;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.chat.ChatEmoticon;


public final class BttvEmoteModel implements Emote {
    private static final String sUrlTemplate = "https://cdn.betterttv.net/emote/{id}/{size}";

    private final String mCode;
    private final boolean bIsGif;

    private final String mSmallEmoteUrl;
    private final String mMediumEmoteUrl;
    private final String mLargeEmoteUrl;

    private final ChatEmoticon ce;

    public BttvEmoteModel(String code, String id, ImageType imageType) {
        this.mCode = code;
        this.bIsGif = imageType == ImageType.GIF;
        this.mSmallEmoteUrl = getUrl("1x", id);
        this.mMediumEmoteUrl = getUrl("2x", id);
        this.mLargeEmoteUrl = getUrl("3x", id);

        this.ce = ChatFactory.getEmoticon(this);
    }

    @NonNull
    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getUrl(EmoteSize size) {
        switch (size) {
            case LARGE:
                return mLargeEmoteUrl;
            default:
            case MEDIUM:
                return mMediumEmoteUrl;
            case SMALL:
                return mSmallEmoteUrl;
        }
    }

    @Override
    public boolean isGif() {
        return bIsGif;
    }

    @NonNull
    @Override
    public ChatEmoticon getChatEmoticon() {
        return ce;
    }

    private static String getUrl(String size, String id) {
        return sUrlTemplate.replace("{id}", id).replace("{size}", size);
    }

    @NonNull
    @Override
    public String toString() {
        return "BttvEmoteModel{" +
                "mCode='" + mCode + '\'' +
                ", bIsGif=" + bIsGif +
                ", mSmallEmoteUrl='" + mSmallEmoteUrl + '\'' +
                ", mMediumEmoteUrl='" + mMediumEmoteUrl + '\'' +
                ", mLargeEmoteUrl='" + mLargeEmoteUrl + '\'' +
                ", ce=" + ce +
                '}';
    }
}
