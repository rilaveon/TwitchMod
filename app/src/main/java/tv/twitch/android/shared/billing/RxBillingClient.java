package tv.twitch.android.shared.billing;


import tv.twitch.android.mod.bridges.Hooks;


public class RxBillingClient {
    /* ... */

    public final boolean isAvailable() {
        if (Hooks.isDisableGoogleBillingJump()) // TODO: __INJECT_CODE
            return false;

        /* ... */

        return false;
    }

    /* ... */
}
