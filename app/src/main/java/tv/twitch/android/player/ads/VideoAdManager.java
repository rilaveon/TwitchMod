package tv.twitch.android.player.ads;


import tv.twitch.android.mod.bridges.Hooks;


public class VideoAdManager {
    /* ... */

    public void requestAds(Object vASTAdPosition, Object videoAdRequestInfo2) {
        if (Hooks.isAdblockOn()) { // TODO: __INJECT_CODE
            return;
        }

        /* ... */
    }

}
