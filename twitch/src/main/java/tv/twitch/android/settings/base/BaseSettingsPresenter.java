package tv.twitch.android.settings.base;


import androidx.fragment.app.FragmentActivity;

import java.util.List;

import tv.twitch.android.shared.ui.menus.SettingsPreferencesController;
import tv.twitch.android.shared.ui.menus.core.MenuModel;


public abstract class BaseSettingsPresenter {

    protected abstract SettingsNavigationController getNavController();

    public abstract String getToolbarTitle();

    protected abstract SettingsPreferencesController getPrefController();

    public final FragmentActivity getActivity() {
        return null;
    }

    public abstract void updateSettingModels();

    public final List<MenuModel> getSettingModels() {
        return null;
    }
}
