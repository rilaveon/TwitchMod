package tv.twitch.android.shared.player.presenters;


import tv.twitch.android.mod.bridges.Hooks;


public class VodPlayerPresenter {
    /* ... */

    public final void requestAd(Object iVideoAdManager, Object clientAdRequestMetadata, boolean z) {
        if (Hooks.isAdblockOn()) { // TODO: __INJECT_CODE
            return;
        }

        /* ... */
    }

    /* ... */
}
