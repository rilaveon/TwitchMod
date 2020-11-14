package tv.twitch.android.shared.player.overlay.seekable;


import tv.twitch.android.mod.bridges.Hooks;


public class PlayPauseFastSeekViewDelegate {
    private final void setTouchListenersForFastSeeking() {
        int fastSeekRewind = Hooks.getRewindSeek(); // TODO: __HOOK
        int fastSeekForward = Hooks.getForwardSeek(); // TODO: __HOOK
    }
}
