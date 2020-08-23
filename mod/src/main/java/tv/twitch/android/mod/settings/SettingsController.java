package tv.twitch.android.mod.settings;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;

import java.util.Arrays;
import java.util.List;

import tv.twitch.android.mod.BuildConfig;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.models.settings.ChatWidthPercent;
import tv.twitch.android.mod.models.settings.FloatingChatQueueSize;
import tv.twitch.android.mod.models.settings.FloatingChatRefreshDelay;
import tv.twitch.android.mod.models.settings.MsgDelete;
import tv.twitch.android.mod.models.settings.UserMessagesFiltering;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.android.mod.models.settings.ExoPlayerSpeed;
import tv.twitch.android.mod.models.settings.Gifs;
import tv.twitch.android.mod.models.settings.MiniPlayerSize;
import tv.twitch.android.mod.models.settings.PlayerImpl;
import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.shared.ui.menus.SettingsPreferencesController;
import tv.twitch.android.shared.ui.menus.checkable.CheckableGroupModel;
import tv.twitch.android.shared.ui.menus.core.MenuModel;
import tv.twitch.android.shared.ui.menus.dropdown.DropDownMenuModel;
import tv.twitch.android.shared.ui.menus.infomenu.InfoMenuModel;
import tv.twitch.android.shared.ui.menus.togglemenu.ToggleMenuModel;



public class SettingsController {
    private static final String TELEGRAM_URL = "https://t.me/pubTw";
    private static final String GITHUB_URL = "https://github.com/nopbreak/TwitchMod";


    public static final class ModSettingsController implements SettingsPreferencesController {
        private final FragmentActivity mFragmentActivity;

        public ModSettingsController(FragmentActivity fragmentActivity) {
            mFragmentActivity = fragmentActivity;
        }

        @Override
        public void updatePreferenceBooleanState(ToggleMenuModel item, boolean isChecked) {
            SettingsController.updatePreferenceBooleanState(mFragmentActivity, item, isChecked);
        }

        @Override
        public void updatePreferenceCheckedState(CheckableGroupModel checkableGroupModel) {}
    }

    public static final class onMenuItemSelected implements DropDownMenuModel.DropDownMenuItemSelection<PreferenceItem> {
        final ArrayAdapter<PreferenceItem> mAdapter;

        onMenuItemSelected(ArrayAdapter<PreferenceItem> arrayAdapter) {
            this.mAdapter = arrayAdapter;
        }

        @Override
        public void onDropDownItemSelected(DropDownMenuModel<PreferenceItem> dropDownMenuModel, int itemPos) {
            PreferenceItem preference = this.mAdapter.getItem(itemPos);
            if (preference == null) {
                Logger.error("preference is null");
                return;
            }

            PreferenceManager.INSTANCE.updateString(preference.getKey(), preference.getValue());
        }
    }

    public static void updatePreferenceBooleanState(FragmentActivity fragmentActivity, ToggleMenuModel menuItem, boolean isChecked) {
        if (menuItem == null) {
            Logger.error("menuItem is null");
            return;
        }

        SettingsPreferencesController.SettingsPreference prefType = menuItem.getSettingsPref();
        if (prefType == null) {
            Logger.error("prefType is null");
            return;
        }

        final String preferenceKey = prefType.getPreferenceKey();
        if (TextUtils.isEmpty(preferenceKey)) {
            Logger.warning("preferenceKey is null. Object=" + prefType.toString());
            return;
        }

        PreferenceManager.INSTANCE.updateBoolean(preferenceKey, isChecked);
        maybeRestartIfNeed(fragmentActivity, preferenceKey);
    }

    private static void maybeRestartIfNeed(FragmentActivity fragmentActivity, String preferenceKey) {
        switch (preferenceKey) {
            case PreferenceManager.HIDE_DISCOVER:
            case PreferenceManager.HIDE_ESPORTS:
            case PreferenceManager.ADBLOCK:
            case PreferenceManager.BADGES:
                Helper.showRestartDialog(fragmentActivity, ResourcesManager.INSTANCE.getString("restart_dialog_text"));
        }
    }

    private static class OnBuildClickListener implements View.OnClickListener {
        private static final int CLICKS = 5;

        private int clicked = 0;

        @Override
        public void onClick(View v) {
            clicked++;
            if (clicked == CLICKS) {
                if (PreferenceManager.INSTANCE.isDevModeOn()) {
                    PreferenceManager.INSTANCE.updateBoolean(PreferenceManager.DEV, false);
                    Helper.showToast("Developer mode disabled!");
                } else {
                    PreferenceManager.INSTANCE.updateBoolean(PreferenceManager.DEV, true);
                    Helper.showToast("Developer mode enabled!");
                }
                clicked = 0;
            }
        }
    }

    public static class MenuFactory {
        private static MenuModel getInfoMenu(String title, String desc, View.OnClickListener listener) {
            return new InfoMenuModel(title, desc, null, null, null, null, listener);
        }

        private static MenuModel getInfoMenu(String title) {
            return new InfoMenuModel(title, null, null, null, null, null, null);
        }

        private static MenuModel getToggleMenu(SettingsPreferencesController.SettingsPreference controller, boolean state) {
            ResourcesManager manager = ResourcesManager.INSTANCE;
            return new ToggleMenuModel(manager.getString(controller.getNameKey()), manager.getString(controller.getDescriptionKey()), null, state, true, null, null, false, null, null, null, controller, null);
        }

        private static MenuModel getDropDownMenu(SettingsPreferencesController.SettingsPreference controller, Context context, ResourcesManager resourcesManager, PreferenceItem[] items, PreferenceItem state) {
            ArrayAdapter<PreferenceItem> adapter = new ArrayAdapter<>(context, ResourcesManager.INSTANCE.getLayoutId("twitch_spinner_dropdown_item"), items);
            return new DropDownMenuModel<>(adapter, Arrays.asList(items).indexOf(state), resourcesManager.getString(controller.getNameKey()), resourcesManager.getString(controller.getDescriptionKey()), null, null, new onMenuItemSelected(adapter));
        }
    }

    public static SettingsPreferencesController getMenuListener(FragmentActivity activity) {
        return new ModSettingsController(activity);
    }

    public static void initialize(final Context context, List<MenuModel> items) {
        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;
        final ResourcesManager resourcesManager = ResourcesManager.INSTANCE;

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_chat_bttv")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BttvEmotes, preferenceManager.isBttvOn()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.FfzBadges, preferenceManager.isThirdPartyBadgesOn()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BttvEmotesPicker, preferenceManager.isEmotePickerOn()));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.Gifs, context, resourcesManager, Gifs.values(), preferenceManager.getGifsStrategy()));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.EmoteSize, context, resourcesManager, EmoteSize.values(), preferenceManager.getEmoteSize()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_chat_category")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Timestamps, preferenceManager.isMessageTimestampOn()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.RedChatMention, preferenceManager.isRedMentionOn()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BypassChatBan, preferenceManager.isBypassChatBan()));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.MsgDelete, context, resourcesManager, MsgDelete.values(), preferenceManager.getMsgDelete()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_floating_chat")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.FloatingChat, preferenceManager.isFloatingChatEnabled()));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.FloatingChatQueueSize, context, resourcesManager, FloatingChatQueueSize.values(), preferenceManager.getFloatingChatQueueSize()));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.FloatingChatRefresh, context, resourcesManager, FloatingChatRefreshDelay.values(), preferenceManager.getFloatingChatRefresh()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_player_category")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Adblock, preferenceManager.isAdblockEnabled()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.AutoPlay, preferenceManager.isDisableAutoplay()));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.PlayerImpl, context, resourcesManager, PlayerImpl.values(), preferenceManager.getPlayerImplementation()));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.MiniplayerSize, context, resourcesManager, MiniPlayerSize.values(), preferenceManager.getMiniPlayerSize()));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.ExoplayerSpeed, context, resourcesManager, ExoPlayerSpeed.values(), preferenceManager.getExoplayerSpeed()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_swipe")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.VolumeSwipe, preferenceManager.isVolumeSwipeEnabled()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BrightnessSwipe, preferenceManager.isBrightnessSwipeEnabled()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_chat_filtering")));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.FilterLevel, context, resourcesManager, UserMessagesFiltering.values(), preferenceManager.getChatFiltering()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.FilterSystem, preferenceManager.isIgnoreSystemMessages()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_view")));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.ChatWidthScale, context, resourcesManager, ChatWidthPercent.values(), preferenceManager.getChatWidthPercent()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.EmotePickerView, preferenceManager.isOldEmotePicker()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.DisableNewClips, preferenceManager.isForceOldClips()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideRecommendedStreams, preferenceManager.isDisableRecommendations()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideResumeWatchingStreams, preferenceManager.isDisableRecentWatching()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideFollowedGames, preferenceManager.isDisableFollowedGames()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideDiscoverTab, preferenceManager.isHideDiscoverTab()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideEsportsTab, preferenceManager.isHideEsportsTab()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_patch")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.RecentSearch,  preferenceManager.isDisableRecentSearch()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideGs,  preferenceManager.isHideGs()));

        if (preferenceManager.isDevModeOn()) {
            items.add(MenuFactory.getInfoMenu("Dev"));
            items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Interceptor, preferenceManager.isInterceptorOn()));
        }

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_info")));

        items.add(MenuFactory.getInfoMenu("TwitchMod v" + BuildConfig.VERSION_NAME, LoaderLS.APK_BUILD_INFO, new OnBuildClickListener()));
        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_info_open_telegram"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.openUrl(TELEGRAM_URL);
            }
        }));
        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_info_open_github"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.openUrl(GITHUB_URL);
            }
        }));
    }
}
