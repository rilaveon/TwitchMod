package tv.twitch.android.shared.chat.adapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tv.twitch.android.core.adapters.TwitchAdapter;
import tv.twitch.android.mod.utils.GifHelper;
import tv.twitch.android.mod.utils.Logger;


public class ChannelChatAdapter extends TwitchAdapter {
    /* ... */

    public void tearDown() {
        GifHelper.recycleItems(getItems()); // TODO: __INJECT_CODE

        /* ... */
    }

    /* ... */

    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) { // TODO: __INJECT_METHOD
        super.onViewRecycled(holder);
        GifHelper.recycleObject(holder, true);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) { // TODO: __INJECT_METHOD
        super.onViewDetachedFromWindow(holder);
        GifHelper.recycleObject(holder, false);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) { // TODO: __INJECT_METHOD
        Logger.debug("onDetachedFromRecyclerView");
        super.onDetachedFromRecyclerView(recyclerView);
        GifHelper.recycleItems(getItems());
    }
}
