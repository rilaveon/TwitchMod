package tv.twitch.android.shared.ui.elements.span;


import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.resource.gif.GifDrawable;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.interfaces.IDrawable;


public class UrlDrawable extends BitmapDrawable implements IDrawable { // TODO: __IMPLEMENT
    public Drawable drawable;
    private String url;
    public MediaSpan$Type type;


    public UrlDrawable(String url, MediaSpan$Type type) {
        this.url = url;
        this.type = type;
    }

    public final void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void draw(Canvas canvas) { // TODO: __REPLACE_METHOD
        Drawable drawable = this.drawable;
        if (drawable != null) {
            drawable.draw(canvas);
            if (drawable instanceof GifDrawable) {
                if (Hooks.isGifsEnabled() && !((GifDrawable) drawable).isStarted()) {
                    ((GifDrawable) drawable).start();
                }
            }
        }
    }

    @Override
    public Drawable getDrawable() { // TODO: __INJECT_METHOD
        return drawable;
    }
}
