package tv.twitch.android.shared.chat.chomments;


import android.text.Spanned;
import android.widget.TextView;

import tv.twitch.android.mod.bridges.interfaces.IChatMessageItem;


public class ChommentRecyclerItem implements IChatMessageItem { // TODO: __IMPLEMENT
    private Spanned msgSpan;

    /* ... */

    @Override
    public Spanned getSpanned() { // TODO: __INJECT_METHOD
        return msgSpan;
    }

    public static final class ChommentItemViewHolder implements IChatMessageItem { // TODO: __IMPLEMENT
        /* ... */

        @Override
        public Spanned getSpanned() { // TODO: __INJECT_METHOD
            TextView textView = getChommentTextView();
            if (textView != null)
                return (Spanned) textView.getText();

            return null;
        }

        public final TextView getChommentTextView() {
            return null;
        }

        /* ... */
    }

    /* ... */
}
