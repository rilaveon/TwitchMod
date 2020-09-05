package tv.twitch.android.models.privacy;


import tv.twitch.android.mod.bridges.Hooks;


public class VendorConsentSetting {
    /* ... */

    public final VendorConsentStatus getConsentStatus() {
        if (Hooks.isAdblockOn()) { // TODO: __INJECT_CODE
            return VendorConsentStatus.Denied;
        }

        /* ... */

        return null;
    }

    /* ... */
}
