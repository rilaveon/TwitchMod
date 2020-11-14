package tv.twitch.android.shared.ads;


import tv.twitch.android.mod.bridges.Hooks;


public class AdsPlayerPresenter {
    /* ... */

    private final void requestAd(Object iVideoAdManager, Object clientAdRequestMetadata, boolean z, boolean z2) {
        if (Hooks.isAdblockOn()) { // TODO: __INJECT_CODE
            return;
        }

        /* ... */
    }

    /* ... */
}
