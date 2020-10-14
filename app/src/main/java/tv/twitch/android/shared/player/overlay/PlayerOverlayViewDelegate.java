package tv.twitch.android.shared.player.overlay;


import io.reactivex.subjects.PublishSubject;
import tv.twitch.android.core.mvp.viewdelegate.BaseViewDelegate;

public class PlayerOverlayViewDelegate extends BaseViewDelegate {
    /* ... */

    public final OverlayLayoutController getOverlayLayoutController() {
        return null;
    }

    public final PublishSubject<PlayerOverlayEvents> getPlayerOverlayEventsSubject() {
        return null;
    }

    public final void setLockButtonVisible(boolean z) { // TODO: __INJECT_METHOD
        getBottomPlayerControlOverlayViewDelegate().setLockButtonVisible(z);
    }

    private final BottomPlayerControlOverlayViewDelegate getBottomPlayerControlOverlayViewDelegate() {
        return null;
    }
}
