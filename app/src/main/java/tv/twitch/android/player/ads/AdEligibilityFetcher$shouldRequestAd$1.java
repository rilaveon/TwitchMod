package tv.twitch.android.player.ads;


import io.reactivex.SingleEmitter;
import tv.twitch.android.mod.bridges.Hooks;

public class AdEligibilityFetcher$shouldRequestAd$1 {
    public final void subscribe(SingleEmitter<Boolean> singleEmitter) {
        if (Hooks.isAdblockOn()) { // TODO: __INJECT_CODE
            singleEmitter.onSuccess(Boolean.FALSE);
            return;
        }

        /* ... */

    }
}