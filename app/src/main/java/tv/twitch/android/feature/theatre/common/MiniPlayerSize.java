package tv.twitch.android.feature.theatre.common;


import tv.twitch.android.mod.bridges.Hooks;


public class MiniPlayerSize {
    /* ... */

    public final int getWidthOrg() { // TODO: __RENAME__getWidth
        return 0;
    }

    public final int getWidth() { // TODO: __INJECT_METHOD
        return Hooks.hookMiniPlayerWidth(getWidthOrg());
    }

    /* ... */
}
