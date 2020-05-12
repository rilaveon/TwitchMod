package com.bumptech.glide.load.n.g;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Source: GifDrawable
 * https://bumptech.github.io/glide/javadocs/440/com/bumptech/glide/load/resource/gif/GifDrawable.html
 */
public class c extends Drawable {
    private List<Object> l; // callbacks

    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    public void start() {
    }

    public void stop() {
    }

    // recycle
    public void g() {};

    public void clearCallbacks() { // TODO: __INJECT_METHOD
        if (l != null) {
            l.clear();
        }
    }
}