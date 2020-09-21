package tv.twitch.android.mod.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;


import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.models.Preferences;
import tv.twitch.android.mod.models.preferences.ChatWidthScale;
import tv.twitch.android.mod.models.preferences.FloatingChatSize;
import tv.twitch.android.mod.models.preferences.FloatingChatRefreshDelay;
import tv.twitch.android.mod.models.preferences.MsgDelete;
import tv.twitch.android.mod.models.preferences.RobottyLimit;
import tv.twitch.android.mod.models.preferences.UserMessagesFiltering;
import tv.twitch.android.mod.models.preferences.EmoteSize;
import tv.twitch.android.mod.models.preferences.ExoPlayerSpeed;
import tv.twitch.android.mod.models.preferences.Gifs;
import tv.twitch.android.mod.models.preferences.MiniPlayerSize;
import tv.twitch.android.mod.models.preferences.PlayerImpl;
import tv.twitch.android.mod.utils.ChatMesssageFilteringUtil;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.settings.base.BaseSettingsPresenter;
import tv.twitch.android.shared.ui.menus.SettingsPreferencesController;
import tv.twitch.android.shared.ui.menus.checkable.CheckableGroupModel;
import tv.twitch.android.shared.ui.menus.core.MenuModel;
import tv.twitch.android.shared.ui.menus.dropdown.DropDownMenuModel;
import tv.twitch.android.shared.ui.menus.togglemenu.ToggleMenuModel;


public class SettingsController {
    private static final List<Preferences> RESTART_LIST = new ArrayList<>();

    public static final SettingsController INSTANCE = new SettingsController();

    private static final String TELEGRAM_URL = "https://t.me/pubTw";
    private static final String RECENT_MESSAGES_SERVICE_URL = "https://recent-messages.robotty.de";
    private static final String GITHUB_URL = "https://github.com/nopbreak/TwitchMod";


    private SettingsController() {
        RESTART_LIST.add(Preferences.ADBLOCK);
        RESTART_LIST.add(Preferences.HIDE_ESPORTS_TAB);
        RESTART_LIST.add(Preferences.HIDE_DISCOVER_TAB);
        RESTART_LIST.add(Preferences.DEV_ENABLE_INTERCEPTOR);
        RESTART_LIST.add(Preferences.BADGES);
        RESTART_LIST.add(Preferences.HIDE_GPS_ERROR);
        RESTART_LIST.add(Preferences.DEV_MODE);
    }

    public static final class ModSettingsPreferencesController implements SettingsPreferencesController {
        private final Activity mActivity;

        public ModSettingsPreferencesController(BaseSettingsPresenter presenter) {
            if (presenter == null)
                throw new IllegalArgumentException("presenter is null");

            if (presenter.getActivity() == null)
                throw new ExceptionInInitializerError("getActivity() is null");

            mActivity = presenter.getActivity();
        }

        @Override
        public void updatePreferenceBooleanState(ToggleMenuModel item, boolean isChecked) {
            if (item == null) {
                Logger.error("item is null");
                return;
            }

            Logger.debug("item=" + item.toString() + ", isChecked=" + isChecked);

            SettingsPreferencesController.SettingsPreference prefType = item.getSettingsPref();
            if (prefType == null) {
                Logger.error("prefType is null");
                return;
            }

            Preferences preference = prefType.getPreference();
            if (preference == null) {
                Logger.error("prefType is null");
                return;
            }

            final String preferenceKey = preference.getKey();
            if (TextUtils.isEmpty(preferenceKey)) {
                Logger.warning("empty preferenceKey. prefType=" + prefType.toString());
                return;
            }

            Logger.debug("preferenceKey=" + preferenceKey + ", isChecked=" + isChecked);
            PreferenceManager.INSTANCE.updateBoolean(preferenceKey, isChecked);
            maybeShowRestartDialog(mActivity, preference);
        }

        @Override
        public void updatePreferenceCheckedState(CheckableGroupModel checkableGroupModel) {
            Logger.debug(checkableGroupModel.toString());
        }
    }

    public static final class onMenuItemSelected implements DropDownMenuModel.DropDownMenuItemSelection<PreferenceArrayAdapter.AdapterItem> {
        final PreferenceArrayAdapter mAdapter;
        private int mSelected = -1;

        onMenuItemSelected(PreferenceArrayAdapter arrayAdapter) {
            this.mAdapter = arrayAdapter;
        }

        @Override
        public void onDropDownItemSelected(DropDownMenuModel<PreferenceArrayAdapter.AdapterItem> dropDownMenuModel, int itemPos) {
            if (itemPos == mSelected) {
                return;
            }

            mSelected = itemPos;
            PreferenceArrayAdapter.AdapterItem item = this.mAdapter.getItem(itemPos);
            if (item == null) {
                Logger.error("item is null");
                return;
            }

            Object val = item.getVal();
            if (val instanceof String) {
                PreferenceManager.INSTANCE.updateString(mAdapter.getPrefKey(), (String) val);
            } else {
                PreferenceManager.INSTANCE.updateInt(mAdapter.getPrefKey(), (int) val);
            }

            dropDownMenuModel.setSelectedOption(itemPos);
        }
    }


    private static void maybeShowRestartDialog(Activity activity, Preferences preference) {
        if (RESTART_LIST.indexOf(preference) == -1)
            return;

        Helper.showRestartDialog(activity, ResourcesManager.INSTANCE.getString("restart_dialog_text"));
    }

    public static class OnBuildClickListener implements View.OnClickListener {
        private static final int CLICKS = 5;

        private int clicked = 0;

        @Override
        public void onClick(View v) {
            clicked++;
            if (clicked == CLICKS) {
                if (PreferenceManager.INSTANCE.isDevModeOn()) {
                    PreferenceManager.INSTANCE.updateBoolean(Preferences.DEV_MODE.getKey(), false);
                    Helper.showToast("Developer mode disabled!");
                } else {
                    PreferenceManager.INSTANCE.updateBoolean(Preferences.DEV_MODE.getKey(), true);
                    Helper.showToast("Developer mode enabled!");
                }
                clicked = 0;
            }
        }
    }

    public static SettingsPreferencesController getPrefController(BaseSettingsPresenter presenter) {
        if (presenter == null) {
            Logger.debug("presenter is null");

            return null;
        }

        return new ModSettingsPreferencesController(presenter);
    }

    public final static class OpenFilterBlacklistDialog implements View.OnClickListener {
        private final Context mContext;

        public OpenFilterBlacklistDialog(Context context) {
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(ResourcesManager.INSTANCE.getString("mod_settings_blacklist"));

            View viewInflated = LayoutInflater.from(mContext).inflate(ResourcesManager.INSTANCE.getLayoutId("mod_filter_blacklist"), null, false);

            final TextInputEditText input = viewInflated.findViewById(ResourcesManager.INSTANCE.getId("input"));
            final String text = PreferenceManager.INSTANCE.getFilterText();
            if (TextUtils.isEmpty(text)) {
                input.setText("");
            } else {
                input.setText(text);
            }

            builder.setView(viewInflated);

            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    CharSequence inputText = input.getText();
                    if (text == null)
                        inputText = "";

                    String text = String.valueOf(inputText);

                    PreferenceManager.INSTANCE.updateString(Preferences.FILTER_TEXT.getKey(), text);
                    ChatMesssageFilteringUtil.INSTANCE.updateBlacklist(text);
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }

    public static void initialize(final Context context, List<MenuModel> items) {
        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;
        final ResourcesManager resourcesManager = ResourcesManager.INSTANCE;

        items.clear();

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_chat_bttv")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BttvEmotes, resourcesManager, preferenceManager.isBttvOn()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Badges, resourcesManager, preferenceManager.isThirdPartyBadgesOn()));

        PreferenceArrayAdapter gifsAdapter = new PreferenceArrayAdapter(context, Preferences.GIFS.getKey());
        gifsAdapter.add(new PreferenceArrayAdapter.AdapterItem("Disabled", Gifs.DISABLED));
        gifsAdapter.add(new PreferenceArrayAdapter.AdapterItem("Static", Gifs.STATIC));
        gifsAdapter.add(new PreferenceArrayAdapter.AdapterItem("Animated", Gifs.ANIMATED));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.Gifs, resourcesManager, gifsAdapter, preferenceManager.getGifsStrategy()));

        PreferenceArrayAdapter emoteSizeAdapter = new PreferenceArrayAdapter(context, Preferences.EMOTE_SIZE.getKey());
        emoteSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("Small", EmoteSize.SMALL));
        emoteSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("Medium", EmoteSize.MEDIUM));
        emoteSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("Large", EmoteSize.LARGE));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.EmoteSize, resourcesManager, emoteSizeAdapter, preferenceManager.getEmoteSize()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_chat_category")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Timestamps, resourcesManager, preferenceManager.isMessageTimestampOn()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.RedChatMention, resourcesManager, preferenceManager.isRedMentionOn()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BypassChatBan, resourcesManager, preferenceManager.isBypassChatBan()));

        PreferenceArrayAdapter msgDeleteAdapter = new PreferenceArrayAdapter(context, Preferences.MSG_DELETE_STRATEGY.getKey());
        msgDeleteAdapter.add(new PreferenceArrayAdapter.AdapterItem("Default", MsgDelete.DEFAULT));
        msgDeleteAdapter.add(new PreferenceArrayAdapter.AdapterItem("Legacy", MsgDelete.MOD));
        msgDeleteAdapter.add(new PreferenceArrayAdapter.AdapterItem("Strikethrough", MsgDelete.STRIKETHROUGH));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.MsgDelete, resourcesManager, msgDeleteAdapter, preferenceManager.getMsgDelete()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_chat_filtering")));

        PreferenceArrayAdapter messagesFilteringAdapter = new PreferenceArrayAdapter(context, Preferences.CHAT_MESSAGE_FILTER_LEVEL.getKey());
        messagesFilteringAdapter.add(new PreferenceArrayAdapter.AdapterItem("Disabled", UserMessagesFiltering.DISABLED));
        messagesFilteringAdapter.add(new PreferenceArrayAdapter.AdapterItem("Subs", UserMessagesFiltering.SUBS));
        messagesFilteringAdapter.add(new PreferenceArrayAdapter.AdapterItem("Mods", UserMessagesFiltering.MODS));
        messagesFilteringAdapter.add(new PreferenceArrayAdapter.AdapterItem("Broadcaster", UserMessagesFiltering.BROADCASTER));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.FilterLevel, resourcesManager, messagesFilteringAdapter, preferenceManager.getChatFiltering()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.FilterSystem, resourcesManager, preferenceManager.isIgnoreSystemMessages()));
        items.add(MenuFactory.getInfoMenu(ResourcesManager.INSTANCE.getString("mod_settings_blacklist"), ResourcesManager.INSTANCE.getString("mod_settings_blacklist_desc"), new OpenFilterBlacklistDialog(context)));

        items.add(MenuFactory.getInfoMenu("Recent-messages service"));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.MessageHistory, resourcesManager, preferenceManager.isMessageHistoryEnabled()));

        PreferenceArrayAdapter robottyLimit = new PreferenceArrayAdapter(context, Preferences.ROBOTTY_LIMIT.getKey());
        robottyLimit.add(new PreferenceArrayAdapter.AdapterItem(String.valueOf(RobottyLimit.LIMIT1), RobottyLimit.LIMIT1));
        robottyLimit.add(new PreferenceArrayAdapter.AdapterItem(String.valueOf(RobottyLimit.LIMIT2), RobottyLimit.LIMIT2));
        robottyLimit.add(new PreferenceArrayAdapter.AdapterItem(String.valueOf(RobottyLimit.LIMIT3), RobottyLimit.LIMIT3));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.RobottyLimit, resourcesManager, robottyLimit, preferenceManager.getMessageHistoryLimit()));
        items.add(MenuFactory.getInfoMenu("About the service", "Visit website", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.openUrl(RECENT_MESSAGES_SERVICE_URL);
            }
        }));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_floating_chat")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.FloatingChat, resourcesManager, preferenceManager.isFloatingChatEnabled()));

        PreferenceArrayAdapter floatingChatQueueSizeAdapter = new PreferenceArrayAdapter(context, Preferences.FLOAT_CHAT_SIZE.getKey());
        floatingChatQueueSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("Default", FloatingChatSize.DEFAULT));
        floatingChatQueueSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("4", FloatingChatSize.FOUR));
        floatingChatQueueSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("5", FloatingChatSize.FIVE));
        floatingChatQueueSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("6", FloatingChatSize.SIX));
        floatingChatQueueSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("7", FloatingChatSize.SEVEN));
        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.FloatingChatQueueSize, resourcesManager, floatingChatQueueSizeAdapter, preferenceManager.getFloatingChatQueueSize()));

        PreferenceArrayAdapter floatingChatRefreshAdapter = new PreferenceArrayAdapter(context, Preferences.FLOATING_REFRESH.getKey());
        floatingChatRefreshAdapter.add(new PreferenceArrayAdapter.AdapterItem("Default", FloatingChatRefreshDelay.DEFAULT));
        floatingChatRefreshAdapter.add(new PreferenceArrayAdapter.AdapterItem("1.25x", FloatingChatRefreshDelay.MUL1));
        floatingChatRefreshAdapter.add(new PreferenceArrayAdapter.AdapterItem("1.5x", FloatingChatRefreshDelay.MUL2));
        floatingChatRefreshAdapter.add(new PreferenceArrayAdapter.AdapterItem("1.75x", FloatingChatRefreshDelay.MUL3));
        floatingChatRefreshAdapter.add(new PreferenceArrayAdapter.AdapterItem("2x", FloatingChatRefreshDelay.MUL4));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.FloatingChatRefresh, resourcesManager, floatingChatRefreshAdapter, preferenceManager.getFloatingChatRefresh()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_player_category")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Adblock, resourcesManager, preferenceManager.isAdblockEnabled()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.AutoPlay, resourcesManager, preferenceManager.isDisableAutoplay()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.StatsButton, resourcesManager, preferenceManager.isShowStatsButton()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.RefreshButton, resourcesManager, preferenceManager.isShowRefreshButton()));


        PreferenceArrayAdapter playerImplAdapter = new PreferenceArrayAdapter(context, Preferences.PLAYER_IMPL.getKey());
        playerImplAdapter.add(new PreferenceArrayAdapter.AdapterItem("Auto", PlayerImpl.AUTO));
        playerImplAdapter.add(new PreferenceArrayAdapter.AdapterItem("TwitchCore", PlayerImpl.CORE));
        playerImplAdapter.add(new PreferenceArrayAdapter.AdapterItem("ExoPlayer V2", PlayerImpl.EXO));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.PlayerImpl, resourcesManager, playerImplAdapter, preferenceManager.getPlayerImplementation()));

        PreferenceArrayAdapter miniPlayerSizeAdapter = new PreferenceArrayAdapter(context, Preferences.MINIPLAYER_SCALE.getKey());
        miniPlayerSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("Default", MiniPlayerSize.DEFAULT));
        miniPlayerSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("1.25x", MiniPlayerSize.SIZE1));
        miniPlayerSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("1.5x", MiniPlayerSize.SIZE2));
        miniPlayerSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("1.75x", MiniPlayerSize.SIZE3));
        miniPlayerSizeAdapter.add(new PreferenceArrayAdapter.AdapterItem("2x", MiniPlayerSize.SIZE4));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.MiniplayerSize, resourcesManager, miniPlayerSizeAdapter, preferenceManager.getMiniPlayerSize()));

        PreferenceArrayAdapter exoPlayerVodSpeedAdapter = new PreferenceArrayAdapter(context, Preferences.EXOPLAYER_SPEED.getKey());
        exoPlayerVodSpeedAdapter.add(new PreferenceArrayAdapter.AdapterItem("Default", ExoPlayerSpeed.DEFAULT));
        exoPlayerVodSpeedAdapter.add(new PreferenceArrayAdapter.AdapterItem("1.25x", ExoPlayerSpeed.SPEED125));
        exoPlayerVodSpeedAdapter.add(new PreferenceArrayAdapter.AdapterItem("1.5x", ExoPlayerSpeed.SPEED150));
        exoPlayerVodSpeedAdapter.add(new PreferenceArrayAdapter.AdapterItem("1.75x", ExoPlayerSpeed.SPEED175));
        exoPlayerVodSpeedAdapter.add(new PreferenceArrayAdapter.AdapterItem("2x", ExoPlayerSpeed.SPEED200));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.ExoplayerSpeed, resourcesManager, exoPlayerVodSpeedAdapter, preferenceManager.getExoplayerSpeed()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_swipe")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.VolumeSwipe, resourcesManager, preferenceManager.isVolumeSwipeEnabled()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BrightnessSwipe, resourcesManager, preferenceManager.isBrightnessSwipeEnabled()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_view")));

        PreferenceArrayAdapter chatWidthScaleAdapter = new PreferenceArrayAdapter(context, Preferences.CHAT_WIDTH_SCALE.getKey());
        chatWidthScaleAdapter.add(new PreferenceArrayAdapter.AdapterItem("Default", ChatWidthScale.DEFAULT));
        chatWidthScaleAdapter.add(new PreferenceArrayAdapter.AdapterItem("10%", ChatWidthScale.SCALE10));
        chatWidthScaleAdapter.add(new PreferenceArrayAdapter.AdapterItem("15%", ChatWidthScale.SCALE15));
        chatWidthScaleAdapter.add(new PreferenceArrayAdapter.AdapterItem("20%", ChatWidthScale.SCALE20));
        chatWidthScaleAdapter.add(new PreferenceArrayAdapter.AdapterItem("25%", ChatWidthScale.SCALE25));
        chatWidthScaleAdapter.add(new PreferenceArrayAdapter.AdapterItem("30%", ChatWidthScale.SCALE30));
        chatWidthScaleAdapter.add(new PreferenceArrayAdapter.AdapterItem("35%", ChatWidthScale.SCALE35));
        chatWidthScaleAdapter.add(new PreferenceArrayAdapter.AdapterItem("40%", ChatWidthScale.SCALE40));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.ChatWidthScale, resourcesManager, chatWidthScaleAdapter, preferenceManager.getChatWidthScale()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BetterFollowView, resourcesManager, preferenceManager.isCompactViewEnabled()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.EmotePickerView, resourcesManager, preferenceManager.isForceOldEmotePicker()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.DisableNewClips, resourcesManager, preferenceManager.isForceOldClips()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideRecommendedStreams, resourcesManager, preferenceManager.isDisableRecommendations()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideResumeWatchingStreams, resourcesManager, preferenceManager.isDisableRecentWatching()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideFollowedGames, resourcesManager, preferenceManager.isDisableFollowedGames()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideDiscoverTab, resourcesManager, preferenceManager.isHideDiscoverTab()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideEsportsTab, resourcesManager, preferenceManager.isHideEsportsTab()));

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_settings_patch")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.RecentSearch, resourcesManager,  preferenceManager.isDisableRecentSearch()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideGs, resourcesManager,  preferenceManager.isHideGs()));

        if (preferenceManager.isDevModeOn()) {
            items.add(MenuFactory.getInfoMenu("Dev"));
            items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Interceptor, resourcesManager, preferenceManager.isInterceptorOn()));
        }

        items.add(MenuFactory.getInfoMenu(resourcesManager.getString("mod_category_info")));

        items.add(MenuFactory.getInfoMenu("TwitchMod v" + LoaderLS.getVersion(), LoaderLS.getBuildInfo(), new OnBuildClickListener()));
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
