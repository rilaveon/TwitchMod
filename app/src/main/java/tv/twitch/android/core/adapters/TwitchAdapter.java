package tv.twitch.android.core.adapters;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import tv.twitch.android.mod.utils.GifHelper;


public class TwitchAdapter {
    private List<RecyclerAdapterItem> items;

    protected final List<RecyclerAdapterItem> getItems() {
        return this.items;
    }

    public final Pair<RecyclerAdapterItem, Integer> removeItem(Function1<? super RecyclerAdapterItem, Boolean> function1) {
        int itemIndex = 0;
        /* ... */

        GifHelper.recycleObject(items.get(itemIndex), true); // TODO: __INJECT_CODE
        this.items.remove(itemIndex);

        /* ... */
        return null;
    }

    public void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
        GifHelper.recycleObject(viewHolder, false); // TODO: __INJECT_CODE
    }

    public final void clear() {
        GifHelper.recycleAdapterItems(this.items); // TODO: __INJECT_CODE

        /* ... */
    }

    public void addItems(List<? extends RecyclerAdapterItem> list)  {/* ... */}
}
