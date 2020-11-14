package tv.twitch.android.mod.bridges.interfaces;


import android.content.Context;

import java.util.List;

import tv.twitch.android.shared.ui.menus.core.MenuModel;


public interface ISettingsPresenter {
    List<MenuModel> xGetSettingModels();

    void xBindSettings();

    Context xGetContext();
}
