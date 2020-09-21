package com.bumptech.glide.load.resource.gif;


import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;


/**
 * https://bumptech.github.io/glide/javadocs/440/com/bumptech/glide/load/resource/gif/GifDrawable.html
 */
public class GifDrawable extends Drawable {
    private boolean isStarted;

    /* ... */

    @Override
    public void draw(Canvas canvas) {/* ... */}

    @Override
    public void setAlpha(int alpha) {/* ... */}

    @Override
    public void setColorFilter(ColorFilter colorFilter) {/* ... */}

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    public void start() {/* ... */}

    public void stop() {/* ... */}

    public boolean isStarted() { // TODO: __INJECT_METHOD
        return isStarted;
    }
}
