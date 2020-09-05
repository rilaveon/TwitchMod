package tv.twitch.android.shared.chat.adapter;

import androidx.recyclerview.widget.RecyclerView;

import tv.twitch.android.core.adapters.TwitchAdapter;
import tv.twitch.android.mod.utils.GifHelper;


public class ChannelChatAdapter extends TwitchAdapter {
    public void tearDown() {
        /* ... */

        GifHelper.recycleItems(getItems()); // TODO: __INJECT_CODE
    }

    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) { // TODO: __INJECT_METHOD
        GifHelper.recycleObject(viewHolder);
    }
}
