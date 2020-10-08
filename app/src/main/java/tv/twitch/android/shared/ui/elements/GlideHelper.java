package tv.twitch.android.shared.ui.elements;


import android.content.Context;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import tv.twitch.android.shared.ui.elements.span.GlideChatImageTarget;

public class GlideHelper {
    /* ... */


    public static void loadImagesFromSpanned(Context context, Spanned spanned, TextView textView, View parentView) { // TODO: __INJECT_ARG
        /* ... */

        GlideChatImageTarget res = new GlideChatImageTarget(context, null, 0);
        res.setParentView(parentView); // TODO: __INJECT_CODE
    }

    /* ... */
}
