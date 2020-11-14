package tv.twitch.android.shared.chat.messagefactory.adapteritem;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tv.twitch.android.mod.bridges.Hooks;

public class UserNoticeRecyclerItem {
    /* ... */

    public static final class UserNoticeViewHolder extends RecyclerView.ViewHolder {
        private final TextView chatMessage = null;
        private final TextView systemMessage = null;

        /* ... */

        public UserNoticeViewHolder(View view) {
            super(view);

            Hooks.setChatMessageFontSize(chatMessage); // TODO: __INJECT_CODE
            Hooks.setChatMessageFontSize(systemMessage); // TODO: __INJECT_CODE
            /* ... */
        }

        /* ... */
    }

    /* ... */
}
