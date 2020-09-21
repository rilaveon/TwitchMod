package tv.twitch.android.shared.preferences;


import tv.twitch.android.mod.bridges.Hooks;

public class VideoDebugConfig {
    /* ... */

    public final boolean shouldShowVideoDebugPanel() { // TODO: __REPLACE_METHOD
        return Hooks.shouldShowStatsButton();
    }

    /* ... */
}