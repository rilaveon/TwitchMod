package tv.twitch.android.shared.chat.chomments;


import android.text.Spanned;
import android.widget.TextView;

import tv.twitch.android.mod.bridges.interfaces.IChatMessageItem;
import tv.twitch.android.mod.utils.Logger;


public class ChommentRecyclerItem implements IChatMessageItem { // TODO: __IMPLEMENT
    private final Spanned msgSpan = null;

    /* ... */

    @Override
    public Spanned getSpanned() { // TODO: __INJECT_METHOD
        return msgSpan;
    }

    @Override
    public void clearTextView() {} // TODO: __INJECT_METHOD

    public static final class ChommentItemViewHolder implements IChatMessageItem { // TODO: __IMPLEMENT
        /* ... */

        @Override
        public Spanned getSpanned() { // TODO: __INJECT_METHOD
            TextView textView = getChommentTextView();
            if (textView != null)
                return (Spanned) textView.getText();

            return null;
        }

        @Override
        public void clearTextView() { // TODO: __INJECT_METHOD
            TextView textView = getChommentTextView();
            if (textView != null)
                textView.setText(null);
        }

        public final TextView getChommentTextView() {
            return null;
        }

        /* ... */
    }

    /* ... */
}
