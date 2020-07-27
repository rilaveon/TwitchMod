package tv.twitch.android.shared.ui.elements.span;


import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;


public class CenteredImageSpan extends ImageSpan {
    private final Drawable imageDrawable = null;

    public CenteredImageSpan(Drawable drawable) {
        super(drawable);
    }

    public final Drawable getImageDrawable() {
        return imageDrawable;
    }
}
