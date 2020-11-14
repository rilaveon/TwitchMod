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
import tv.twitch.android.mod.bridges.interfaces.ISettingsPresenter;
import tv.twitch.android.mod.models.Preferences;
import tv.twitch.android.mod.models.preferences.ChatWidthScale;
import tv.twitch.android.mod.models.preferences.FloatingChatSize;
import tv.twitch.android.mod.models.preferences.FloatingChatRefreshDelay;
import tv.twitch.android.mod.models.preferences.FontSize;
import tv.twitch.android.mod.models.preferences.MsgDelete;
import tv.twitch.android.mod.models.preferences.PlayerSeek;
import tv.twitch.android.mod.models.preferences.RobottyLimit;
import tv.twitch.android.mod.models.preferences.UserMessagesFiltering;
import tv.twitch.android.mod.models.preferences.EmoteSize;
import tv.twitch.android.mod.models.preferences.ExoPlayerSpeed;
import tv.twitch.android.mod.models.preferences.Gifs;
import tv.twitch.android.mod.models.preferences.MiniPlayerSize;
import tv.twitch.android.mod.models.preferences.PlayerImpl;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
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
    private static final String DONATE_URL_CIS = "https://www.donationalerts.com/r/nopbreak";
    private static final String DONATE_URL = "https://streamlabs.com/n0pbreak/tip";

    static {
        RESTART_LIST.add(Preferences.PLAYER_ADBLOCK);
        RESTART_LIST.add(Preferences.HIDE_ESPORTS_TAB);
        RESTART_LIST.add(Preferences.HIDE_DISCOVER_TAB);
        RESTART_LIST.add(Preferences.DEV_INTERCEPTOR);
        RESTART_LIST.add(Preferences.CUSTOM_BADGES);
        RESTART_LIST.add(Preferences.DEV_MODE);
        RESTART_LIST.add(Preferences.CHAT_MESSAGE_FONT_SIZE);
    }

    public static final class ModSettingsController implements SettingsPreferencesController {
        private final ISettingsPresenter presenter;

        public ModSettingsController(ISettingsPresenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public void updatePreferenceBooleanState(ToggleMenuModel item, boolean isChecked) {
            if (item == null) {
                Logger.error("item is null");
                return;
            }

            SettingsPreferencesController.SettingsPreference settingPref = item.getSettingsPref();
            if (settingPref == null) {
                Logger.error("settingPref is null");
                return;
            }

            Preferences preference = settingPref.getPreference();
            if (preference == null) {
                Logger.error("prefType is null");
                return;
            }

            final String preferenceKey = preference.getKey();
            if (TextUtils.isEmpty(preferenceKey)) {
                Logger.warning("empty preferenceKey. prefType=" + settingPref.toString());
                return;
            }

            PreferenceManager.INSTANCE.updateBoolean(preferenceKey, isChecked);
            if (presenter != null && RESTART_LIST.indexOf(preference) != -1) {
                showRestartDialog((Activity) presenter.xGetContext(), preference);
            }
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

    private static void showRestartDialog(Activity activity, Preferences preference) {
        Helper.showRestartDialog(activity, ResourcesManager.getString("restart_dialog_text"));
    }

    public static class OnBuildClickListener implements View.OnClickListener {
        private static final int CLICKS = 15;

        private int clicked = 0;

        private void disableDevMod() {
            PreferenceManager.INSTANCE.updateBoolean(Preferences.DEV_MODE.getKey(), false);
            Helper.showToast("Developer mode disabled!");
        }

        private void enableDevMod() {
            PreferenceManager.INSTANCE.updateBoolean(Preferences.DEV_MODE.getKey(), true);
            Helper.showToast("Developer mode enabled!");
        }

        @Override
        public void onClick(View v) {
            clicked++;
            if (clicked == CLICKS) {
                if (PreferenceManager.INSTANCE.isDevModeOn()) {
                    disableDevMod();
                } else {
                    enableDevMod();
                }
                clicked = 0;
            } else if (clicked % 5 == 0 && clicked > 0) {
                if (PreferenceManager.INSTANCE.isDevModeOn()) {
                    disableDevMod();
                    clicked = 0;
                } else {
                    if (clicked == 10) {
                        Helper.showToast("monkaHmm");
                    } else {
                        Helper.showToast("Jebaited");
                    }
                }
            }
        }
    }

    public static SettingsPreferencesController getPrefController(ISettingsPresenter presenter) {
        if (presenter == null) {
            Logger.error("presenter is null");
            return null;
        }

        return new ModSettingsController(presenter);
    }

    public final static class OpenUserFilterBlocklistDialog implements View.OnClickListener {
        private final Context mContext;

        public OpenUserFilterBlocklistDialog(Context context) {
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(ResourcesManager.getString("mod_settings_blocklist"));

            View viewInflated = LayoutInflater.from(mContext).inflate(ResourcesManager.getLayoutId("mod_filter_blocklist"), null, false);

            final TextInputEditText input = viewInflated.findViewById(ResourcesManager.getId("input"));
            final String text = PreferenceManager.INSTANCE.getUserFilterText();
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
                    if (inputText == null)
                        inputText = "";

                    String text = String.valueOf(inputText);

                    PreferenceManager.INSTANCE.updateString(Preferences.USER_FILTER_TEXT.getKey(), text);
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

    private static void showCategoryMenu(final ISettingsPresenter presenter) {
        List<MenuModel> items = presenter.xGetSettingModels();
        items.clear();

        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_category_settings_chat_bttv"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBttvMenu(presenter);
            }
        }));
        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_category_settings_chat_category"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChatMenu(presenter);
            }
        }));
        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_category_settings_player_category"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlayerMenu(presenter);
            }
        }));
        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_category_settings_adblock_category"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdblockMenu(presenter);
            }
        }));
        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_category_settings_swipe"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSwiperMenu(presenter);
            }
        }));
        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_category_view"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewMenu(presenter);
            }
        }));
        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_category_settings_patch"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPatchesMenu(presenter);
            }
        }));

        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;

        if (preferenceManager.isDevModeOn()) {
            items.add(MenuFactory.getInfoMenu("Dev", null, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDevMenu(presenter);
                }
            }));
        }

        items.add(MenuFactory.getInfoMenu("TwitchMod v" + LoaderLS.getVersionName(), LoaderLS.getBuildInfo(), new OnBuildClickListener()));
        items.add(MenuFactory.getClickableUrlInfoMenu(ResourcesManager.getString("mod_info_open_telegram"), ResourcesManager.getString("mod_info_open_telegram_desc"), TELEGRAM_URL));
        items.add(MenuFactory.getClickableUrlInfoMenu(ResourcesManager.getString("mod_info_open_github"), ResourcesManager.getString("mod_info_open_github_desc"), GITHUB_URL));

        presenter.xBindSettings();
    }

    private static void showBttvMenu(final ISettingsPresenter presenter) {
        prepareSettingMenu(presenter);

        List<MenuModel> items = presenter.xGetSettingModels();

        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;

        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BttvEmotes, preferenceManager.showBttvEmotesInChat()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.CustomBadges, preferenceManager.showCustomBadges()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.WideEmotes, preferenceManager.fixWideEmotes()));

        PreferenceArrayAdapter gifsAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.GIFS_RENDER_TYPE.getKey());
        gifsAdapter.fill(new Integer[]{Gifs.DISABLED, Gifs.STATIC, Gifs.ANIMATED}, ResourcesManager.getStringArray("gifs_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.GifsRenderType, gifsAdapter, preferenceManager.getGifsStrategy()));

        PreferenceArrayAdapter emoteSizeAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.EMOTE_SIZE.getKey());
        emoteSizeAdapter.fill(new Integer[]{EmoteSize.SMALL, EmoteSize.MEDIUM, EmoteSize.LARGE}, ResourcesManager.getStringArray("source_emote_size_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.EmoteSize, emoteSizeAdapter, preferenceManager.getEmoteSize()));

        presenter.xBindSettings();
    }

    private static void showChatMenu(final ISettingsPresenter presenter) {
        prepareSettingMenu(presenter);

        List<MenuModel> items = presenter.xGetSettingModels();

        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;

        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Timestamps, preferenceManager.isChatTimestampsEnabled()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.ChatMentionHighlights, preferenceManager.highlightMentionMessage()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BypassChatBan, preferenceManager.showChatForBannedUser()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.ChatAutoclicker, preferenceManager.isAutoclickerEnabled()));

        PreferenceArrayAdapter msgDeleteAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.MSG_DELETE_STRATEGY.getKey());
        msgDeleteAdapter.fill(new Integer[]{MsgDelete.DEFAULT, MsgDelete.MOD, MsgDelete.STRIKETHROUGH}, ResourcesManager.getStringArray("msg_del_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.MsgDelete, msgDeleteAdapter, preferenceManager.getMsgDelete()));

        PreferenceArrayAdapter msgFontSizeAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.CHAT_MESSAGE_FONT_SIZE.getKey());
        msgFontSizeAdapter.fill(new Integer[]{FontSize.EIGHT, FontSize.NINE, FontSize.TEN, FontSize.ELEVEN, FontSize.TWELVE, FontSize.DEFAULT, FontSize.FOURTEEN, FontSize.SIXTEEN, FontSize.EIGHTEEN, FontSize.TWENTY}, ResourcesManager.getStringArray("font_size_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.ChatMessageFontSize, msgFontSizeAdapter, preferenceManager.getChatMessageFontSize()));

        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_category_chat_filtering")));

        PreferenceArrayAdapter messagesFilteringAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.CHAT_MESSAGE_FILTER_LEVEL.getKey());
        messagesFilteringAdapter.fill(new Integer[]{UserMessagesFiltering.DISABLED, UserMessagesFiltering.SUBS, UserMessagesFiltering.MODS, UserMessagesFiltering.BROADCASTER}, ResourcesManager.getStringArray("filter_level_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.FilterLevel, messagesFilteringAdapter, preferenceManager.getFilterMessageLevel()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.FilterSystem, preferenceManager.hideSystemMessagesInChat()));
        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_settings_blocklist"), ResourcesManager.getString("mod_settings_blocklist_desc"), new OpenUserFilterBlocklistDialog(presenter.xGetContext())));

        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_settings_recent_messages_service_category")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.MessageHistory, preferenceManager.showMessageHistory()));

        PreferenceArrayAdapter robottyLimit = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.ROBOTTY_LIMIT.getKey());
        robottyLimit.fill(new Integer[]{RobottyLimit.LIMIT1, RobottyLimit.LIMIT2, RobottyLimit.LIMIT3}, new String[]{String.valueOf(RobottyLimit.LIMIT1), String.valueOf(RobottyLimit.LIMIT2), String.valueOf(RobottyLimit.LIMIT3)});

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.MessageHistoryLimit, robottyLimit, preferenceManager.getMessageHistoryLimit()));
        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_settings_about_service"), ResourcesManager.getString("mod_settings_visit_website"), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.openUrl(RECENT_MESSAGES_SERVICE_URL);
            }
        }));

        items.add(MenuFactory.getInfoMenu(ResourcesManager.getString("mod_category_settings_floating_chat")));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.FloatingChat, preferenceManager.showFloatingChat()));

        PreferenceArrayAdapter floatingChatQueueSizeAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.FLOAT_CHAT_SIZE.getKey());
        floatingChatQueueSizeAdapter.fill(new Integer[]{FloatingChatSize.DEFAULT, FloatingChatSize.FOUR, FloatingChatSize.FIVE, FloatingChatSize.SIX, FloatingChatSize.SEVEN}, ResourcesManager.getStringArray("floating_chat_queue_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.FloatingChatQueueSize, floatingChatQueueSizeAdapter, preferenceManager.getFloatingChatQueueSize()));

        PreferenceArrayAdapter floatingChatRefreshAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.FLOATING_CHAT_REFRESH_RATE.getKey());
        floatingChatRefreshAdapter.fill(new Integer[]{FloatingChatRefreshDelay.DEFAULT, FloatingChatRefreshDelay.MUL1, FloatingChatRefreshDelay.MUL2, FloatingChatRefreshDelay.MUL3, FloatingChatRefreshDelay.MUL4}, ResourcesManager.getStringArray("floating_chat_rate_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.FloatingChatRefreshRate, floatingChatRefreshAdapter, preferenceManager.getFloatingChatRefreshRate()));

        presenter.xBindSettings();
    }

    private static void showPlayerMenu(final ISettingsPresenter presenter) {
        prepareSettingMenu(presenter);

        List<MenuModel> items = presenter.xGetSettingModels();

        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;

        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.TheatreAutoPlay, preferenceManager.disableTheatreAutoplay()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.StatButton, preferenceManager.shouldShowPlayerStatButton()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.RefreshButton, preferenceManager.shouldShowPlayerRefreshButton()));

        PreferenceArrayAdapter playerImplAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.PLAYER_IMPELEMTATION.getKey());
        playerImplAdapter.fill(new Integer[]{PlayerImpl.AUTO, PlayerImpl.CORE, PlayerImpl.EXO}, ResourcesManager.getStringArray("player_impl_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.PlayerImplementation, playerImplAdapter, preferenceManager.getPlayerImplementation()));

        PreferenceArrayAdapter playerForwardSeek = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.PLAYER_FORWARD_SEEK.getKey());
        playerForwardSeek.fill(new Integer[]{PlayerSeek.FIVE, PlayerSeek.TEN, PlayerSeek.FIFTEEN, PlayerSeek.THIRTY, PlayerSeek.SIXTY}, ResourcesManager.getStringArray("player_forward_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.PlayerForwardSeek, playerForwardSeek, preferenceManager.getPlayerForwardSeek()));

        PreferenceArrayAdapter playerBackwardSeek = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.PLAYER_BACKWARD_SEEK.getKey());
        playerBackwardSeek.fill(new Integer[]{PlayerSeek.FIVE, PlayerSeek.TEN, PlayerSeek.FIFTEEN, PlayerSeek.THIRTY, PlayerSeek.SIXTY}, ResourcesManager.getStringArray("player_backward_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.PlayerBackwardSeek, playerBackwardSeek, preferenceManager.getPlayerBackwardSeek()));

        PreferenceArrayAdapter miniPlayerSizeAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.MINIPLAYER_SCALE.getKey());
        miniPlayerSizeAdapter.fill(new String[] {MiniPlayerSize.DEFAULT, MiniPlayerSize.SIZE1, MiniPlayerSize.SIZE2, MiniPlayerSize.SIZE3, MiniPlayerSize.SIZE4}, ResourcesManager.getStringArray("miniplayer_scale_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.MiniplayerScale, miniPlayerSizeAdapter, preferenceManager.getMiniPlayerScale()));

        PreferenceArrayAdapter exoPlayerVodSpeedAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.EXOPLAYER_SPEED.getKey());
        exoPlayerVodSpeedAdapter.fill(new String[]{ExoPlayerSpeed.DEFAULT, ExoPlayerSpeed.SPEED125, ExoPlayerSpeed.SPEED150, ExoPlayerSpeed.SPEED175, ExoPlayerSpeed.SPEED200}, ResourcesManager.getStringArray("exoplayer_speed_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.ExoplayerSpeed, exoPlayerVodSpeedAdapter, preferenceManager.getExoplayerSpeed()));

        presenter.xBindSettings();
    }

    private static void showAdblockMenu(final ISettingsPresenter presenter) {
        prepareSettingMenu(presenter);

        List<MenuModel> items = presenter.xGetSettingModels();

        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;

        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Adblock, preferenceManager.isPlayerAdblockOn()));
        items.add(MenuFactory.getInfoMenu("SureStream", "SureStream is a new twitch technology able to inject ads directly into the HLS stream. You can try block ads with dedicated ad blocking software."));
        items.add(MenuFactory.getClickableUrlInfoMenu("AdAway", ResourcesManager.getString("mod_settings_visit_website"), "http://f-droid.org/repository/browse/?fdid=org.adaway"));
        items.add(MenuFactory.getClickableUrlInfoMenu("AdGuard", ResourcesManager.getString("mod_settings_visit_website"), "https://adguard.com"));

        presenter.xBindSettings();
    }

    private static void showSwiperMenu(final ISettingsPresenter presenter) {
        prepareSettingMenu(presenter);

        List<MenuModel> items = presenter.xGetSettingModels();

        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;

        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.VolumeSwiper, preferenceManager.isVolumeSwiperEnabled()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BrightnessSwiper, preferenceManager.isBrightnessSwiperEnabled()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.SwipperLockButton, preferenceManager.shouldShowLockButton()));

        presenter.xBindSettings();
    }

    private static void showViewMenu(final ISettingsPresenter presenter) {
        prepareSettingMenu(presenter);

        List<MenuModel> items = presenter.xGetSettingModels();

        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;

        PreferenceArrayAdapter chatWidthScaleAdapter = new PreferenceArrayAdapter(presenter.xGetContext(), Preferences.LANDSCAPE_CHAT_SCALE.getKey());
        chatWidthScaleAdapter.fill(new Integer[]{ChatWidthScale.DEFAULT, ChatWidthScale.SCALE10, ChatWidthScale.SCALE15, ChatWidthScale.SCALE20, ChatWidthScale.SCALE25, ChatWidthScale.SCALE30, ChatWidthScale.SCALE35, ChatWidthScale.SCALE40}, ResourcesManager.getStringArray("chat_scale_choices"));

        items.add(MenuFactory.getDropDownMenu(SettingsPreferencesController.SettingsPreference.ChatWidthScale, chatWidthScaleAdapter, preferenceManager.getLandscapeChatScale()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideChatRestriction, preferenceManager.hideChatRestriction()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.BetterFollowView, preferenceManager.isCompactPlayerFollowViewEnabled()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.OldEmotePicker, preferenceManager.forceOldEmotePickerView()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.DisableClipfinity, preferenceManager.disableClipfinity()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideRecommendedStreams, preferenceManager.hideFollowRecommendationSection()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideResumeWatchingStreams, preferenceManager.hideFollowResumeSection()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideFollowedGames, preferenceManager.hideFollowGameSection()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideDiscoverTab, preferenceManager.hideDiscoverTab()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideEsportsTab, preferenceManager.hideEsportsTab()));

        presenter.xBindSettings();
    }

    private static void showPatchesMenu(final ISettingsPresenter presenter) {
        prepareSettingMenu(presenter);

        List<MenuModel> items = presenter.xGetSettingModels();

        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;

        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.HideRecentSearch,  preferenceManager.hideRecentSearchResult()));
        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.DisableGoogleBilling,  preferenceManager.isGoogleBillingDisabled()));

        presenter.xBindSettings();
    }

    private static void prepareSettingMenu(final ISettingsPresenter presenter) {
        List<MenuModel> items = presenter.xGetSettingModels();

        items.clear();
        items.add(getActionBack(presenter));
    }

    private static void showDevMenu(final ISettingsPresenter presenter) {
        prepareSettingMenu(presenter);

        List<MenuModel> items = presenter.xGetSettingModels();

        final PreferenceManager preferenceManager = PreferenceManager.INSTANCE;

        items.add(MenuFactory.getToggleMenu(SettingsPreferencesController.SettingsPreference.Interceptor, preferenceManager.isInterceptorEnabled()));

        presenter.xBindSettings();
    }

    private static MenuModel getActionBack(final ISettingsPresenter presenter) {
        return MenuFactory.getInfoMenu(ResourcesManager.getString("mod_settings_action_back"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryMenu(presenter);
            }
        });
    }

    public static void initialize(final ISettingsPresenter presenter) {
        showCategoryMenu(presenter);
    }
}
