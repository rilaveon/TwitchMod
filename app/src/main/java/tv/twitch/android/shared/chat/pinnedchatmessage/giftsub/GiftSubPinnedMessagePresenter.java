package tv.twitch.android.shared.chat.pinnedchatmessage.giftsub;


import tv.twitch.android.mod.bridges.Hooks;


public class GiftSubPinnedMessagePresenter {
    /* ... */

    public final void maybeShowGiftSubBanner(String str, int i) {
        if (Hooks.isJumpSystemIgnore()) // TODO: __INJECT_CODE
            return;

        /* ... */
    }

    /* ... */
}