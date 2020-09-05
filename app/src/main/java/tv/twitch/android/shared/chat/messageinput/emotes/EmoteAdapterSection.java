package tv.twitch.android.shared.chat.messageinput.emotes;


import androidx.recyclerview.widget.RecyclerView;

import tv.twitch.android.mod.bridges.Hooks;


public class EmoteAdapterSection {
    private String emoteSetId = "";

    /* ... */

    private final String constructHeaderFromEmotes() {
        return null;
    }

    public void bindToHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {
        /* ... */

        String str = constructHeaderFromEmotes();
        str = Hooks.hookSetName(str, this.emoteSetId); // TODO: __HOOK

        /* ... */
    }

    /* ... */
}