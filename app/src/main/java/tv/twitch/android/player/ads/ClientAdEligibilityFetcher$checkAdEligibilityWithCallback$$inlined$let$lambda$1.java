package tv.twitch.android.player.ads;


import java.util.LinkedHashSet;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.subscriptions.SubscriptionStatusModel;

import static tv.twitch.android.models.ads.VASTManagement.AdDeclineReason.REASON_OTHER;


public class ClientAdEligibilityFetcher$checkAdEligibilityWithCallback$$inlined$let$lambda$1 {
    public final void accept(SubscriptionStatusModel subscriptionStatusModel) {
        /* ... */

        LinkedHashSet linkedHashSet = new LinkedHashSet();

        /* ... */

        // TODO: __INJECT_CODE
        if (Hooks.isAdblockOn()) {
            if (!linkedHashSet.contains(REASON_OTHER))
                linkedHashSet.add(REASON_OTHER);
        }

        // this.$adEligibilityCallback$inlined.eligibilityDetermined(linkedHashSet);
    }
}