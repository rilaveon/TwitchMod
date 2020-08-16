package com.google.android.gms.common;

import android.content.Context;

import tv.twitch.android.mod.bridges.Hooks;

public class GooglePlayServicesUtilLight {
    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context, int i) {
        if (Hooks.isHideGsJump()) { // TODO: __INJECT_CODE
            return 0;
        }

        /* ... */

        return 0;
    }
}
