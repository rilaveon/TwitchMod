package tv.twitch.android.shared.emotes.emotepicker.adapter;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.shared.emotes.emotepicker.models.EmoteUiModel;


public class EmoteAdapterItem {
    private Context context;
    public EmoteUiModel model;


    public void bindToViewHolder(RecyclerView.ViewHolder viewHolder) {
        /* ... */

        // String url = EmoteUrlUtil.getEmoteUrl(this.context, this.model.getId());
       String url = Hooks.hookEmoteAdapterItem(context, this.model); // TODO: __HOOK
    }
}
