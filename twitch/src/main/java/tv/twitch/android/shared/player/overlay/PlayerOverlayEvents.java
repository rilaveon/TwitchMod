package tv.twitch.android.shared.player.overlay;

public class PlayerOverlayEvents {
    public static final class Refresh extends PlayerOverlayEvents {
        public static final Refresh INSTANCE = new Refresh();

        private Refresh() {
        }
    }
}
