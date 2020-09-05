package tv.twitch.android.mod.models;


import androidx.annotation.NonNull;

import tv.twitch.android.mod.models.api.ImageType;
import tv.twitch.android.mod.models.preferences.EmoteSize;


public final class BttvEmoteModel implements Emote {
    private static final String sUrlTemplate = "https://cdn.betterttv.net/emote/{id}/{size}";

    private final String mCode;
    private final boolean bIsGif;

    private final String mSmallEmoteUrl;
    private final String mMediumEmoteUrl;
    private final String mLargeEmoteUrl;

    public BttvEmoteModel(String code, String id, ImageType imageType) {
        this.mCode = code;
        this.bIsGif = imageType == ImageType.GIF;
        this.mSmallEmoteUrl = getUrl("1x", id);
        this.mMediumEmoteUrl = getUrl("2x", id);
        this.mLargeEmoteUrl = getUrl("3x", id);
    }

    @NonNull
    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getUrl(@EmoteSize int size) {
        switch (size) {
            case EmoteSize.LARGE:
                return mLargeEmoteUrl;
            default:
            case EmoteSize.MEDIUM:
                return mMediumEmoteUrl;
            case EmoteSize.SMALL:
                return mSmallEmoteUrl;
        }
    }

    @Override
    public boolean isGif() {
        return bIsGif;
    }

    private static String getUrl(String size, String id) {
        return sUrlTemplate.replace("{id}", id).replace("{size}", size);
    }

    @Override
    public String toString() {
        return "BttvEmoteModel{" +
                "mCode='" + mCode + '\'' +
                ", bIsGif=" + bIsGif +
                ", mSmallEmoteUrl='" + mSmallEmoteUrl + '\'' +
                ", mMediumEmoteUrl='" + mMediumEmoteUrl + '\'' +
                ", mLargeEmoteUrl='" + mLargeEmoteUrl + '\'' +
                '}';
    }
}
