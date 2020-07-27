package tv.twitch.android.mod.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.models.settings.ChatWidthPercent;
import tv.twitch.android.mod.models.settings.UserMessagesFiltering;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.android.mod.models.settings.ExoPlayerSpeed;
import tv.twitch.android.mod.models.settings.Gifs;
import tv.twitch.android.mod.models.settings.MiniPlayerSize;
import tv.twitch.android.mod.models.settings.PlayerImpl;
import tv.twitch.android.mod.utils.Logger;


public class PreferenceManager implements PreferenceWrapper.PreferenceListener {
    public static final PreferenceManager INSTANCE = new PreferenceManager();

    public static final String BTTV_EMOTES = "nop_bttv_emote";
    public static final String EMOTE_PICKER = "nop_emote_picker";
    public static final String MESSAGE_TIMESTAMP = "nop_emote_timestamp";
    public static final String PLAYER_IMPLEMENTATION = "nop_player_implementation";
    public static final String DISABLE_PLAYER_AUTOPLAY = "nop_disable_player_autoplay";
    public static final String DISABLE_RECENT_SEARCH = "nop_disable_recent_search";
    public static final String HIDE_GS = "nop_hide_gs";
    public static final String EXOPLAYER_SPEED = "nop_exoplayer_speed";
    public static final String CHAT_WIDTH_PERC = "nop_chat_width_perc";
    public static final String MINIPLAYER_SIZE = "nop_miniplayer_size";
    public static final String EMOTE_SIZE = "nop_emote_size";
    public static final String VIDEO_DEBUG_PANEL = "nop_video_debug_panel";
    public static final String SWIPE_VOLUME = "nop_swipe_volume";
    public static final String SWIPE_BRIGHTNESS = "nop_swipe_brightness";
    public static final String DEV = "nop_dev";
    public static final String DEV_INTERCEPTOR = "nop_dev_interceptor";
    public static final String HIDE_DISCOVER = "nop_hide_discover";
    public static final String HIDE_ESPORTS = "nop_hide_esports";
    public static final String FLOATING_CHAT = "nop_floating_chat";
    public static final String GIFS = "nop_gifs";
    public static final String USER_MESSAGES_FILTERING = "nop_user_messages_filtering";
    public static final String USER_MESSAGES_FILTERING_SYSTEM = "nop_user_messages_filtering_system";
    public static final String EMOTE_PICKER_VIEW = "nop_emote_picker_view";
    public static final String FOLLOW_VIEW = "nop_follow_view";
    public static final String ADBLOCK = "nop_adblock";
    public static final String BADGES = "nop_badges";
    public static final String BYPASS_CHAT_BAN = "nop_bypass_chat_ban";

    public static final String DISABLE_RECOMMENDATIONS = "nop_disable_recommendations";
    public static final String DISABLE_FOLLOWED_GAMES = "nop_disable_followed_games";
    public static final String DISABLE_RECENT_WATCHING = "nop_disable_recent_watching";

    public static final String TWITCH_DARK_THEME = "dark_theme_enabled";

    private boolean isDarkThemeEnabled;
    private boolean isAdblockEnabled;

    private PreferenceWrapper mWrapper;


    private PreferenceManager() {}

    public void initialize(Context context) {
        mWrapper = new PreferenceWrapper(context);
        registerLocalPreferences();
    }

    private void registerLocalPreferences() {
        mWrapper.registerLocalPreference(EmoteSize.MEDIUM);
        mWrapper.registerLocalPreference(Gifs.STATIC);
        mWrapper.registerLocalPreference(UserMessagesFiltering.DISABLED);
        mWrapper.registerLocalPreference(PlayerImpl.AUTO);
        mWrapper.registerLocalPreference(ExoPlayerSpeed.DEFAULT);
        mWrapper.registerLocalPreference(MiniPlayerSize.DEFAULT);
        mWrapper.registerLocalPreference(ChatWidthPercent.DEFAULT);
        mWrapper.registerPreferenceListener(this);

        isDarkThemeEnabled = getBoolean(TWITCH_DARK_THEME, false);
        isAdblockEnabled = getBoolean(ADBLOCK, true);
    }

    public void updateBoolean(String key, boolean val) {
        if (mWrapper == null) {
            Logger.error("mWrapper is null");
            return;
        }

        mWrapper.updateBoolean(key, val);
    }

    public void updateString(String key, String val) {
        if (mWrapper == null) {
            Logger.error("mWrapper is null");
            return;
        }

        mWrapper.updateString(key, val);
    }

    private boolean getBoolean(String key, boolean def) {
        if (mWrapper == null) {
            Logger.error("wrapper is null");
            return def;
        }

        return mWrapper.getBoolean(key, def);
    }

    private PreferenceItem getLocalPreference(String key, PreferenceItem def) {
        if (mWrapper == null) {
            Logger.error("wrapper is null");
            return def;
        }

        return mWrapper.getLocalPreference(key);
    }

    public boolean isBttvOn() {
        return getBoolean(BTTV_EMOTES, true);
    }

    public boolean isFfzBadgesOn() {
        return getBoolean(BADGES, false);
    }

    public boolean isBypassChatBan() {
        return getBoolean(BYPASS_CHAT_BAN, false);
    }

    public boolean isEmotePickerOn() {
        return getBoolean(EMOTE_PICKER, true);
    }

    public boolean isMessageTimestampOn() {
        return getBoolean(MESSAGE_TIMESTAMP, false);
    }

    public boolean isDisableAutoplay() {
        return getBoolean(DISABLE_PLAYER_AUTOPLAY, false);
    }

    public boolean isShowVideoDebugPanel() {
        return getBoolean(VIDEO_DEBUG_PANEL, false);
    }

    public boolean isDisableRecentSearch() {
        return getBoolean(DISABLE_RECENT_SEARCH, false);
    }

    public boolean isHideGs() {
        return getBoolean(HIDE_GS, false);
    }

    public boolean isDisableRecentWatching() {
        return getBoolean(DISABLE_RECENT_WATCHING, false);
    }

    public boolean isDisableRecommendations() {
        return getBoolean(DISABLE_RECOMMENDATIONS, false);
    }

    public boolean isDisableFollowedGames() {
        return getBoolean(DISABLE_FOLLOWED_GAMES, false);
    }

    public boolean isVolumeSwipeEnabled() {
        return getBoolean(SWIPE_VOLUME, false);
    }

    public boolean isBrightnessSwipeEnabled() {
        return getBoolean(SWIPE_BRIGHTNESS, false);
    }

    public boolean isOldEmotePicker() {
        return getBoolean(EMOTE_PICKER_VIEW, false);
    }

    public boolean isOldFollowButton() {
        return getBoolean(FOLLOW_VIEW, false);
    }

    public boolean isDevModeOn() {
        return getBoolean(DEV, false);
    }

    public boolean isIgnoreSystemMessages() {
        return getBoolean(USER_MESSAGES_FILTERING_SYSTEM, false);
    }

    public boolean isInterceptorOn() {
        return getBoolean(DEV_INTERCEPTOR, false);
    }

    public boolean isHideDiscoverTab() {
        return getBoolean(HIDE_DISCOVER, false);
    }

    public boolean isHideEsportsTab() {
        return getBoolean(HIDE_ESPORTS, false);
    }

    public boolean isFloatingChatEnabled() {
        return getBoolean(FLOATING_CHAT, true);
    }

    public boolean isDarkThemeEnabled() {
        return isDarkThemeEnabled;
    }

    public boolean isAdblockEnabled() {
        return isAdblockEnabled;
    }

    // LOCAL

    public EmoteSize getEmoteSize() {
        return (EmoteSize) getLocalPreference(EMOTE_SIZE, EmoteSize.MEDIUM);
    }

    public PlayerImpl getPlayerImplementation() {
        return (PlayerImpl) getLocalPreference(PLAYER_IMPLEMENTATION, PlayerImpl.AUTO);
    }

    public UserMessagesFiltering getChatFiltering() {
        return (UserMessagesFiltering) getLocalPreference(USER_MESSAGES_FILTERING, UserMessagesFiltering.DISABLED);
    }

    public ExoPlayerSpeed getExoplayerSpeed() {
        return (ExoPlayerSpeed) getLocalPreference(EXOPLAYER_SPEED, ExoPlayerSpeed.DEFAULT);
    }

    public ChatWidthPercent getChatWidthPercent() {
        return (ChatWidthPercent) getLocalPreference(CHAT_WIDTH_PERC, ChatWidthPercent.DEFAULT);
    }

    public MiniPlayerSize getMiniPlayerSize() {
        return (MiniPlayerSize) getLocalPreference(MINIPLAYER_SIZE, MiniPlayerSize.DEFAULT);
    }

    public Gifs getGifsStrategy() {
        return (Gifs) getLocalPreference(GIFS, Gifs.STATIC);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String preferenceKey) {
        if (TextUtils.isEmpty(preferenceKey)) {
            Logger.error("Empty key");
            return;
        }

        switch (preferenceKey) {
            case PreferenceManager.TWITCH_DARK_THEME:
                isDarkThemeEnabled = sharedPreferences.getBoolean(TWITCH_DARK_THEME, false);
            case PreferenceManager.ADBLOCK:
                isAdblockEnabled = sharedPreferences.getBoolean(ADBLOCK, false);
        }
    }
}
