package tv.twitch.android.settings.z;


import tv.twitch.android.mod.settings.SettingsController;
import tv.twitch.android.shared.ui.menus.j;


/**
 * Source: SystemSettingsPresenter
 */
public class d extends tv.twitch.android.settings.l.b {
    @Override
    protected tv.twitch.android.settings.l.d V1() {
        return null;
    }

    @Override
    public String Y1() {
        return null;
    }

    @Override
    protected j W1() { // TODO: __REPLACE_METHOD
        return SettingsController.getMenuListener(S1());
    }

    @Override
    public void d2() { // TODO: __REPLACE_METHOD
        SettingsController.initialize(S1(), X1());
    }
}
