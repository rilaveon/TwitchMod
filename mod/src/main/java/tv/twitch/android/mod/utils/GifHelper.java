package tv.twitch.android.mod.utils;


import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import com.bumptech.glide.load.resource.gif.GifDrawable;

import java.util.List;

import tv.twitch.android.core.adapters.RecyclerAdapterItem;
import tv.twitch.android.shared.ui.elements.span.CenteredImageSpan;
import tv.twitch.android.mod.bridges.interfaces.IDrawable;
import tv.twitch.android.mod.bridges.interfaces.IChatMessageItem;


public class GifHelper {
    public static void recycleItems(List<RecyclerAdapterItem> items) {
        if (items == null || items.size() == 0)
            return;

        for (Object o : items)
            recycleObject(o, true);
    }

    public static void recycleObject(Object item, boolean clear) {
        if (item == null)
            return;

        if (item instanceof IChatMessageItem) {
            recycleGifsInText(((IChatMessageItem) item).getSpanned());
            if (clear)
                ((IChatMessageItem) item).clearTextView();
        } else if (item instanceof TextView) {
            recycleGifsInText(((TextView) item).getText());
            if (clear)
                ((TextView) item).setText(null);
        }
    }

    public static void recycleGifsInText(CharSequence sequence) {
        if (TextUtils.isEmpty(sequence))
            return;

        Spanned message = (Spanned) sequence;

        CenteredImageSpan[] imageSpans = message.getSpans(0, message.length(), CenteredImageSpan.class);
        if (imageSpans.length == 0)
            return;

        for (CenteredImageSpan image: imageSpans) {
            if (image == null)
                continue;

            Drawable drawable = image.getImageDrawable();
            if (!(drawable instanceof IDrawable))
                continue;

            IDrawable drawableContainer = (IDrawable) drawable;

            Drawable gifDrawable = drawableContainer.getDrawable();
            if (gifDrawable instanceof GifDrawable) {
                ((GifDrawable) gifDrawable).stop();
            }
        }
    }
}
