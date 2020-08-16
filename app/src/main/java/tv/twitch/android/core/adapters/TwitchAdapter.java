package tv.twitch.android.core.adapters;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tv.twitch.android.mod.utils.GifHelper;


public class TwitchAdapter {
    private List<RecyclerAdapterItem> items;

    protected final List<RecyclerAdapterItem> getItems() {
        return this.items;
    }
}
