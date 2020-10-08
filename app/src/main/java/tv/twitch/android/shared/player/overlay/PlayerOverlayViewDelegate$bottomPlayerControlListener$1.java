package tv.twitch.android.shared.player.overlay;


public class PlayerOverlayViewDelegate$bottomPlayerControlListener$1 implements BottomPlayerControlOverlayViewDelegate.BottomPlayerControlListener {
    final PlayerOverlayViewDelegate this$0 = null;

    /* ... */

    @Override
    public void onRefreshClicked() {
        this.this$0.getOverlayLayoutController().hideOverlay();
        this.this$0.getPlayerOverlayEventsSubject().onNext(PlayerOverlayEvents.Refresh.INSTANCE);
    }
}
