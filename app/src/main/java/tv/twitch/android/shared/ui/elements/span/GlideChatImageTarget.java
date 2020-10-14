package tv.twitch.android.shared.ui.elements.span;


import android.content.Context;
import android.graphics.Point;
import android.view.View;

import java.lang.ref.WeakReference;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.utils.Logger;


public class GlideChatImageTarget {
    private UrlDrawable mUrlDrawable;
    private WeakReference<View> mView; // TODO: __INJECT_FIELD

    public GlideChatImageTarget(Context context, UrlDrawable urlDrawable, int i) {}


    private float calcMin(float f1, float f2) { // TODO: __INJECT_METHOD
        if (mUrlDrawable.isWideEmote()) {
            return f1;
        } else {
            return Math.min(f1, f2);
        }
    }

    public void setView(View view) { // TODO: __INJECT_METHOD
        if (view == null) {
            Logger.debug("view is null");
            return;
        }

        mView = new WeakReference<>(view);
    }

    private Point scaleSquared(float f, float f2, float f3) {
        float f4 = 1.0f;
        float f5 = 1.0f;

        /* ... */

        float min = calcMin(f4, f5); // TODO: __INJECT_CODE

        /* ... */

        return null;
    }

    public void onResourceReady(Object drawable, Object transition) {
        /* ... */

        maybeFixWideEmotes(); // TODO: __INJECT_CODE
    }

    private void maybeFixWideEmotes() {
        if (mUrlDrawable.isBadge() || !mUrlDrawable.isWideEmote())
            return;

        Hooks.tryFixWideEmotesInView(mView);
    }
}
