package tv.twitch.android.settings.system;


import tv.twitch.android.mod.settings.SettingsController;
import tv.twitch.android.settings.base.BaseSettingsPresenter;
import tv.twitch.android.settings.base.SettingsNavigationController;
import tv.twitch.android.shared.ui.menus.SettingsPreferencesController;


public class SystemSettingsPresenter extends BaseSettingsPresenter {
    @Override
    protected SettingsNavigationController getNavController() {
        return null;
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    protected SettingsPreferencesController getPrefController() { // TODO: __REPLACE_METHOD
        return SettingsController.getMenuListener(getActivity());
    }

    @Override
    public void updateSettingModels() { // TODO: __REPLACE_METHOD
        getSettingModels().clear();
        SettingsController.initialize(getActivity(), getSettingModels());
    }
}
