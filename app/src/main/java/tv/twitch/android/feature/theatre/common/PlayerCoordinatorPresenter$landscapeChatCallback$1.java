package tv.twitch.android.feature.theatre.common;


import tv.twitch.android.shared.player.overlay.OverlayLayoutController;


public class PlayerCoordinatorPresenter$landscapeChatCallback$1 {
    final PlayerCoordinatorPresenter this$0 = null;

    /* ... */

    public void onLandscapeChatAvailabilityChanged(boolean z) {
        /* ... */

        setLockButtonVisibility(z); // TODO: __INJECT_CODE

        /* ... */
    }

    private void setLockButtonVisibility(boolean visibility) { // TODO: __INJECT_METHOD
        if (this.this$0 != null) {
            OverlayLayoutController controller = this.this$0.getOverlayLayoutController();
            if (controller != null) {
                controller.setLockButtonVisible(visibility);
            }
        }
    }
}
