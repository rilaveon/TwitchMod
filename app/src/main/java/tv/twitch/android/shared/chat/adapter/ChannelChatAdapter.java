package tv.twitch.android.shared.chat.adapter;

import java.util.List;

import tv.twitch.android.adapters.social.MessageRecyclerItem;
import tv.twitch.android.core.adapters.TwitchAdapter;
import tv.twitch.android.core.adapters.RecyclerAdapterItem;
import tv.twitch.android.mod.utils.GifHelper;


public class ChannelChatAdapter extends TwitchAdapter {
    public void tearDown() {
        GifHelper.recycleAdapterItems(getItems()); // TODO: __INJECT_CODE
        /* ... */
    }

    public void clearMessages() {
        GifHelper.recycleAdapterItems(getItems()); // TODO: __INJECT_CODE
        /* ... */
    }

    public void addItems(List<? extends RecyclerAdapterItem> list) {
        /* ... */

        int size = 0;
        if (size > 0) {
            GifHelper.recycleAdapterItems(getItems(), size); // TODO: __INJECT_CODE
        }

        /* ... */
    }

    public void clearMessage(String str) {
        /* ... */

        MessageRecyclerItem messageRecyclerItem2 = null;
        GifHelper.recycleObject(messageRecyclerItem2, true);  // TODO: __INJECT_CODE

        /* ... */
    }
}
