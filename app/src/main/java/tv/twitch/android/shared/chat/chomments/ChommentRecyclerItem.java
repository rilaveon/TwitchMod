package tv.twitch.android.shared.chat.chomments;


import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.interfaces.IChatMessageItem;


public class ChommentRecyclerItem {
    private final Spanned msgSpan = null;

    /* ... */

    public static final class ChommentItemViewHolder extends RecyclerView.ViewHolder implements IChatMessageItem { // TODO: __IMPLEMENT
        private final TextView chommentTextView = null;
        /* ... */

        public ChommentItemViewHolder(View view) {
            super(view);

            /* ... */

            Hooks.setChatMessageFontSize(chommentTextView); // TODO: __INJECT_CODE
        }

        public final TextView getChommentTextView() {
            return null;
        }

        @Override
        public TextView getTextView() { // TODO: __INJECT_METHOD
            return getChommentTextView();
        }

        /* ... */
    }

    /* ... */
}
