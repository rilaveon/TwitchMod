package tv.twitch.android.shared.ui.elements.span;


import android.content.Context;
import android.graphics.Point;
import android.view.View;

import java.lang.ref.WeakReference;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.utils.Logger;


public class GlideChatImageTarget {
    private UrlDrawable mUrlDrawable;
    private WeakReference<View> mContainerView; // TODO: __INJECT_FIELD

    public GlideChatImageTarget(Context context, UrlDrawable urlDrawable, int i) {}


    private float calcMin(float f1, float f2) { // TODO: __INJECT_METHOD
        if (mUrlDrawable.isTwitchEmote() || !mUrlDrawable.shouldWide())
            return Math.min(f1, f2);

        return f1;
    }

    public void setContainerView(View view) { // TODO: __INJECT_METHOD
        if (view == null) {
            Logger.debug("view is null");
            return;
        }

        mContainerView = new WeakReference<>(view);
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

        Hooks.maybeInvalidateContainerView(mContainerView, mUrlDrawable); // TODO: __INJECT_CODE
    }
}
