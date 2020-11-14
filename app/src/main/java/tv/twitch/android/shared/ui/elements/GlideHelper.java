package tv.twitch.android.shared.ui.elements;


import android.content.Context;
import android.text.Spanned;
import android.widget.TextView;

import tv.twitch.android.shared.ui.elements.span.GlideChatImageTarget;


public class GlideHelper {
    /* ... */

    public static final void loadImagesFromSpanned(Context context, Spanned spanned, TextView textView) {
        /* ... */

        GlideChatImageTarget target = new GlideChatImageTarget(null, null, 0);
        target.setContainerView(textView); // TODO: __INJECT_CODE

        /* ... */
    }

    /* ... */
}
