package tv.twitch.android.shared.player.overlay;


import io.reactivex.subjects.PublishSubject;

public class PlayerOverlayViewDelegate {
    /* ... */

    public final OverlayLayoutController getOverlayLayoutController() {
        return null;
    }

    public final PublishSubject<PlayerOverlayEvents> getPlayerOverlayEventsSubject() {
        return null;
    }

    /* ... */
}
