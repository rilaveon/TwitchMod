package tv.twitch.android.settings.system;


import android.content.Context;

import java.util.List;

import tv.twitch.android.mod.bridges.interfaces.ISettingsPresenter;
import tv.twitch.android.mod.settings.SettingsController;
import tv.twitch.android.settings.base.BaseSettingsPresenter;
import tv.twitch.android.settings.base.SettingsNavigationController;
import tv.twitch.android.shared.ui.menus.SettingsPreferencesController;
import tv.twitch.android.shared.ui.menus.core.MenuModel;


public class SystemSettingsPresenter extends BaseSettingsPresenter implements ISettingsPresenter { // TODO: __IMPLEMENT
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
        SettingsController.initialize(this);
    }

    public List<MenuModel> xGetSettingModels() { // TODO: __INJECT_METHOD
        return this.getSettingModels();
    }

    public void xBindSettings() { // TODO: __INJECT_METHOD
        this.bindSettings();
    }

    @Override
    public Context xGetContext() { // TODO: __INJECT_METHOD
        return this.getActivity();
    }

    /* ... */
}
