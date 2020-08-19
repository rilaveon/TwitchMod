package com.google.android.gms.common;


import android.content.Context;


public class GooglePlayServicesUtilLight {
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 12451000;

    @Deprecated
    public static int isGooglePlayServicesAvailableOrg(Context context, int i) { // TODO: __RENAME_METHOD__isGooglePlayServicesAvailable
        /* ... */

        return 0;
    }

    public static int isGooglePlayServicesAvailable(Context context, int i) { // TODO: __REPLACE_METHOD
        //if (Hooks.isHideGsJump()) {
        //    return 0;
        //}

        return isGooglePlayServicesAvailableOrg(context, i);
    }
}
