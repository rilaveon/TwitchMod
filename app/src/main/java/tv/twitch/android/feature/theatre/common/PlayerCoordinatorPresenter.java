package tv.twitch.android.feature.theatre.common;


import tv.twitch.android.shared.player.overlay.OverlayLayoutController;
import tv.twitch.android.shared.player.presenters.PlayerPresenter;


public abstract class PlayerCoordinatorPresenter {
    /* ... */

    private final PlayerPresenter playerPresenter = null;

    /* ... */

    protected final OverlayLayoutController getOverlayLayoutController() {return null;}

    private final void playWithCurrentModeAndQuality() {/* ... */}

    public final void refreshStream() { // TODO: __INJECT_METHOD
        if (playerPresenter != null)
            playerPresenter.stop();
        playWithCurrentModeAndQuality();
    }
}
