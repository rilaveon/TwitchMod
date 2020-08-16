package tv.twitch.android.shared.chat.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tv.twitch.android.adapters.social.MessageRecyclerItem;
import tv.twitch.android.core.adapters.TwitchAdapter;
import tv.twitch.android.core.adapters.RecyclerAdapterItem;
import tv.twitch.android.mod.utils.GifHelper;


public class ChannelChatAdapter extends TwitchAdapter {
    public void tearDown() {
        /* ... */
        GifHelper.forceRecycleItems(getItems());
    }

    public void addItems(List<RecyclerAdapterItem> list) {
        /* ... */
    }

    public void clearMessagesFromUser(int i, int i2) {
        /* ... */
    }

    public void clearMessage(String str) {
        /* ... */
    }

    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) { // TODO: __INJECT_METHOD
        GifHelper.recycleObject(viewHolder, false);
    }
}
