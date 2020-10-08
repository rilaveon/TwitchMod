package tv.twitch.android.shared.ui.elements.span;


import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;


public final class CenteredImageSpan extends ImageSpan {
    private final Drawable imageDrawable = null;

    /* ... */

    public CenteredImageSpan(Drawable imageDrawable, Integer backgroundColor) {
        super(imageDrawable);

        /* ... */
    }

    public final Drawable getImageDrawable() {
        return imageDrawable;
    }

    /* ... */
}
