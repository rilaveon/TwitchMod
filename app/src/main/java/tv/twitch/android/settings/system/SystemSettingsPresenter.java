package tv.twitch.android.settings.system;


import java.util.List;

import tv.twitch.android.mod.settings.SettingsController;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.settings.base.BaseSettingsPresenter;
import tv.twitch.android.settings.base.SettingsNavigationController;
import tv.twitch.android.shared.ui.menus.SettingsPreferencesController;
import tv.twitch.android.shared.ui.menus.core.MenuModel;


public class SystemSettingsPresenter extends BaseSettingsPresenter {
    /* ... */

    @Override
    protected SettingsNavigationController getNavController() { // TODO: __REPLACE_METHOD
        return null;
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    protected SettingsPreferencesController getPrefController() { // TODO: __REPLACE_METHOD
        return SettingsController.getPrefController(this);
    }

    @Override
    public void updateSettingModels() { // TODO: __REPLACE_METHOD
        List<MenuModel> models = getSettingModels();
        if (models == null) {
            Logger.error("models is null");
            return;
        }

        SettingsController.initialize(getActivity(), models);
    }

    /* ... */
}
