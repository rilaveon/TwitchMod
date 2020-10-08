package tv.twitch.android.settings;


import android.app.Activity;
import android.os.Bundle;

import tv.twitch.android.models.settings.SettingsDestination;


public final class SettingsActivity extends Activity {
    /* ... */

    public SettingsDestination settingsDestination;


    /* ... */

    @SuppressWarnings("ConstantConditions")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        /* ... */

        // SettingsDestination settingsDestination3 = this.settingsDestination; // TODO: __INJECT_CODE
        if (getIntent().getExtras().getBoolean("OPEN_MOD_SETTINGS", false)) {
            settingsDestination = getSettingsDestination(SettingsDestination.System);
        }

        /* ... */
    }

    private SettingsDestination getSettingsDestination(SettingsDestination org) { // TODO: __INJECT_METHOD
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean("OPEN_MOD_SETTINGS", false)) {
                return SettingsDestination.System;
            }
        }

        return org;
    }


    /* ... */
}
