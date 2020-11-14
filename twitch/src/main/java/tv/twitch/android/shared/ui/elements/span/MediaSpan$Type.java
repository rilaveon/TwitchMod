package tv.twitch.android.shared.ui.elements.span;


public enum MediaSpan$Type {
    AnimatedBit(24.0f),
    Emote(24.0f),
    Badge(18.0f),
    Reward(18.0f),
    SmallEmote(14.0f);

    private final float sizeDp = 0;

    MediaSpan$Type(float size) {/* ... */}

    public final float getSizeDp() { // TODO: __REPLACE_METHOD
        return 0;
        // return Hooks.hookMediaSpanDpSize(sizeDp);
    }

    /* ... */
}
