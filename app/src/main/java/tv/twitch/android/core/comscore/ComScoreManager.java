package tv.twitch.android.core.comscore;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.gdpr.GdprConsentStatus;


public class ComScoreManager {
    public final void onGdprConsentStatusChanged(GdprConsentStatus gdprConsentStatus) {
        /* ... */

        if (!Hooks.isAdblockOn()) { // TODO: __JUMP_HOOK

        }

        /* ... */
    }
}
