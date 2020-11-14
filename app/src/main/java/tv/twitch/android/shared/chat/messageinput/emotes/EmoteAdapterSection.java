package tv.twitch.android.shared.chat.messageinput.emotes;


import tv.twitch.android.mod.bridges.Hooks;


public class EmoteAdapterSection {
    private String emoteSetId;

    /* ... */

    private String constructHeaderFromEmotes() {
        String res = null;

        /* ... */

        return Hooks.hookSetName(res, this.emoteSetId); // TODO: __INJECT_CODE
    }

    /* ... */
}