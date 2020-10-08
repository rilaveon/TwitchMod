package tv.twitch.android.shared.chat.chomments;


import android.text.Spanned;
import android.widget.TextView;

import tv.twitch.android.mod.bridges.interfaces.IChatMessageItem;
import tv.twitch.android.mod.utils.Logger;


public class ChommentRecyclerItem {
    private final Spanned msgSpan = null;

    /* ... */

    public static final class ChommentItemViewHolder implements IChatMessageItem { // TODO: __IMPLEMENT
        /* ... */

        public final TextView getChommentTextView() {
            return null;
        }

        @Override
        public TextView getTextView() {
            return getChommentTextView();
        }

        /* ... */
    }

    /* ... */
}
