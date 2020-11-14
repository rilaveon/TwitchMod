package tv.twitch.android.shared.player.overlay;


import tv.twitch.android.core.mvp.viewdelegate.BaseViewDelegate;


public class OverlayLayoutController {
    private BaseViewDelegate overlayViewDelegate;

    private boolean lockButtonVisible; // TODO: __INJECT_FIELD

    /* ... */

    public final void hideOverlay() {/* ... */}


    public final void setLockButtonVisible(boolean z) { // TODO: __INJECT_METHOD
        this.lockButtonVisible = z;
        BaseViewDelegate baseViewDelegate = this.overlayViewDelegate;
        if (baseViewDelegate != null && (baseViewDelegate instanceof PlayerOverlayViewDelegate)) {
            ((PlayerOverlayViewDelegate) baseViewDelegate).setLockButtonVisible(z);
        }
    }

    public final void setViewDelegate(BaseViewDelegate baseViewDelegate) {
        /* ... */

        PlayerOverlayViewDelegate playerOverlayViewDelegate = (PlayerOverlayViewDelegate) (!(baseViewDelegate instanceof PlayerOverlayViewDelegate) ? null : baseViewDelegate);
        if (playerOverlayViewDelegate != null) {
            /* ... */

            playerOverlayViewDelegate.setLockButtonVisible(this.lockButtonVisible); // TODO: __INJECT_CODE
        }

        /* ... */
    }

    /* ... */
}
