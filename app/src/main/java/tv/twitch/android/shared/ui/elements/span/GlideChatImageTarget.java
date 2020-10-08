package tv.twitch.android.shared.ui.elements.span;


import android.content.Context;
import android.graphics.Point;
import android.view.View;

import java.lang.ref.WeakReference;

public class GlideChatImageTarget {
    private UrlDrawable mUrlDrawable;
    private WeakReference<View> parentView; // TODO: __INJECT_FIELD

    public GlideChatImageTarget(Context context, UrlDrawable urlDrawable, int i) {
        parentView = null;  // TODO: __INJECT_CODE
    }

    public void setParentView(View view) { // TODO: __INJECT_METHOD
        if (view == null)
            return;

        parentView = new WeakReference<>(view);
    }

    private float calcMin(float f1, float f2) { // TODO: __INJECT_METHOD
        if (mUrlDrawable.isWideEmote()) {
            return f1;
        } else {
            return Math.min(f1, f2);
        }
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

        maybeInvalidateParentView(); // TODO: __INJECT_CODE
    }

    void maybeInvalidateParentView() { // TODO: __INJECT_METHOD
        if (mUrlDrawable.isBadge() || !mUrlDrawable.isWideEmote())
            return;

        if (parentView != null) {
            View view = parentView.get();
            if (view != null) {
                view.invalidate();
                view.requestLayout();
            }
        }
    }
}
