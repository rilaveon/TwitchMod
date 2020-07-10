package tv.twitch.android.mod.models;


import tv.twitch.android.mod.bridges.ChatFactory;
import tv.twitch.android.mod.models.api.ImageType;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.chat.ChatEmoticon;


public final class BttvEmoteModel implements Emote {
    private static final String sUrlTemplate = "https://cdn.betterttv.net/emote/{id}/{size}";

    private final String mCode;
    private final String mId;
    private final boolean bIsGif;

    private final String mSmallEmoteUrl;
    private final String mMediumEmoteUrl;
    private final String mLargeEmoteUrl;

    private final ChatEmoticon ce;

    public BttvEmoteModel(String code, String id, ImageType imageType) {
        this.mCode = code;
        this.mId = id;
        this.bIsGif = imageType == ImageType.GIF;
        this.mSmallEmoteUrl = getUrl("1x");
        this.mMediumEmoteUrl = getUrl("2x");
        this.mLargeEmoteUrl = getUrl("3x");

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
                return mLargeEmoteUrl;
            default:
            case MEDIUM:
                return mMediumEmoteUrl;
            case SMALL:
                return mSmallEmoteUrl;
        }
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public boolean isGif() {
        return bIsGif;
    }

    @Override
    public ChatEmoticon getChatEmoticon() {
        return ce;
    }

    private String getUrl(String size) {
        return sUrlTemplate.replace("{id}", getId()).replace("{size}", size);
    }

    @Override
    public String toString() {
        return "{Code: " + getCode() + ", Id: " + getId() + ", isGif: " + isGif() + "}";
    }
}
