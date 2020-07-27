package tv.twitch.android.buildconfig;


import tv.twitch.android.mod.bridges.Hooks;


public class BuildConfigUtil {
    public static final BuildConfigUtil INSTANCE = new BuildConfigUtil();


    public final boolean isAlpha() { // TODO: __REPLACE_METHOD
        return Hooks.isDevModeOn();
    }

    public final boolean isBeta() { // TODO: __REPLACE_METHOD
        return Hooks.isDevModeOn();
    }

    public final boolean isDebugConfigEnabled() { // TODO: __REPLACE_METHOD
        return Hooks.isDevModeOn();
    }
    
    public final boolean shouldShowDebugOptions() { // TODO: __REPLACE_METHOD
        return Hooks.isDevModeOn();
    }
}
