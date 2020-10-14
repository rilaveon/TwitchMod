package tv.twitch.android.shared.subscriptions.purchasers;


import android.content.Context;

import tv.twitch.android.mod.bridges.Hooks;

public class GooglePlaySubscriptionPurchaser {
    /* ... */

    public boolean isAvailable(Context context) {
        if (Hooks.isDisableGoogleBillingJump()) // TODO: __INJECT_CODE
            return false;

        /* ... */

        return false;
    }

    /* ... */
}
