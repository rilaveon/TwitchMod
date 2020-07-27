package tv.twitch.android.shared.preferences;


import tv.twitch.android.mod.bridges.Hooks;


public class VideoDebugConfig {
    public final boolean org() { // TODO: __RENAME__shouldShowVideoDebugPanel
        return false;
    }

    public final boolean shouldShowVideoDebugPanel() { // TODO: __INJECT_METHOD
        return Hooks.hookVideoDebugPanel(org());
    }
}