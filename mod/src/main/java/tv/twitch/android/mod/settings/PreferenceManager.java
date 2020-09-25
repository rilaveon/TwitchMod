package tv.twitch.android.mod.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import tv.twitch.android.mod.models.Preferences;
import tv.twitch.android.mod.models.preferences.ChatWidthScale;
import tv.twitch.android.mod.models.preferences.EmoteSize;
import tv.twitch.android.mod.models.preferences.ExoPlayerSpeed;
import tv.twitch.android.mod.models.preferences.FloatingChatRefreshDelay;
import tv.twitch.android.mod.models.preferences.FloatingChatSize;
import tv.twitch.android.mod.models.preferences.Gifs;
import tv.twitch.android.mod.models.preferences.MiniPlayerSize;
import tv.twitch.android.mod.models.preferences.MsgDelete;
import tv.twitch.android.mod.models.preferences.PlayerImpl;
import tv.twitch.android.mod.models.preferences.RobottyLimit;
import tv.twitch.android.mod.models.preferences.UserMessagesFiltering;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;


public class PreferenceManager implements PreferenceWrapper.PreferenceListener {
    public static final String TWITCH_DARK_THEME_KEY = "dark_theme_enabled";
    public static final String MOD_BANNER_KEY = "mod_banner";

    public static final PreferenceManager INSTANCE = new PreferenceManager();

    private boolean isBttvEmotesOn;
    private boolean isChatTimestampsOn;
    private boolean isAdblockOn;
    private boolean isVolumeSwiperOn;
    private boolean isBrightnessSwiperOn;
    private boolean isHideFollowRec;
    private boolean isHideFollowResume;
    private boolean isHideFollowGames;
    private boolean isHideDiscoverTab;
    private boolean isHideEsportsTab;
    private boolean isHideRecentSearch;
    private boolean isDevModeOn;
    private boolean isTheatreAutoplayDisabled;
    private boolean isUseOldEmotePicker;
    private boolean isChatSystemFilteringOn;
    private boolean isInterceptorEnabled;
    private boolean isBadgesOn;
    private boolean isBypassChatBan;
    private boolean isHideGps;
    private boolean isRedMentionOn;
    private boolean isNewClipsViewDisabled;
    private boolean isRobottyServiceEnabled;
    private boolean isFloatingChatEnabled;
    private boolean isCompactViewEnabled;
    private boolean isShowStatsButton;
    private boolean isShowRefreshButton;
    private boolean isShowBanner;

    private String mFilterText;

    private @EmoteSize int emoteSize;
    private @ChatWidthScale int chatWidthScale;
    private @Gifs int gifs;
    private @MsgDelete int msgDelete;
    private @ExoPlayerSpeed String exoPlayerSpeed;
    private @MiniPlayerSize String miniPlayerSize;
    private @PlayerImpl int playerImpl;
    private @UserMessagesFiltering int messageFiltering;
    private @FloatingChatSize int floatingChatSize;
    private @FloatingChatRefreshDelay int floatingChatRefresh;
    private @RobottyLimit int robottyLimit;

    private PreferenceWrapper mWrapper;

    private boolean isDarkThemeEnabled;
    private boolean isNeedHideGPS = false;

    private PreferenceManager() {}

    public void initialize(Context context) {
        if (mWrapper != null)
            throw new ExceptionInInitializerError("mWrapper is not null");

        mWrapper = new PreferenceWrapper(context);
        isNeedHideGPS = !Helper.isGooglePlayServicesAvailable(context);
        initializePreferences();
    }

    private void initializePreferences() {
        updatePreferences();
        mWrapper.registerPreferenceListener(this);
    }

    private void updatePreferences() {
        isBttvEmotesOn = getBoolean(Preferences.BTTV_EMOTES, true);
        isChatTimestampsOn = getBoolean(Preferences.CHAT_TIMESTAMPS, false);
        isAdblockOn = getBoolean(Preferences.ADBLOCK, true);
        isVolumeSwiperOn = getBoolean(Preferences.VOLUME_SWIPER, false);
        isBrightnessSwiperOn = getBoolean(Preferences.BRIGHTNESS_SWIPER, false);
        isVolumeSwiperOn = getBoolean(Preferences.VOLUME_SWIPER, false);
        isHideFollowRec = getBoolean(Preferences.HIDE_FOLLOW_RECOMMENDED_STREAMS, false);
        isHideFollowResume = getBoolean(Preferences.HIDE_FOLLOW_RESUME_STREAMS, false);
        isHideFollowGames = getBoolean(Preferences.HIDE_FOLLOW_GAMES, false);
        isHideDiscoverTab = getBoolean(Preferences.HIDE_DISCOVER_TAB, false);
        isHideEsportsTab = getBoolean(Preferences.HIDE_ESPORTS_TAB, false);
        isHideRecentSearch = getBoolean(Preferences.HIDE_RECENT_SEARCH_RESULTS, false);
        isTheatreAutoplayDisabled = getBoolean(Preferences.DISABLE_THEATRE_AUTOPLAY, false);
        isUseOldEmotePicker = getBoolean(Preferences.EMOTE_PICKER_VIEW, false);
        isChatSystemFilteringOn = getBoolean(Preferences.CHAT_MESSAGE_FILTER_SYSTEM, false);
        isInterceptorEnabled = getBoolean(Preferences.DEV_ENABLE_INTERCEPTOR, false);
        isBadgesOn = getBoolean(Preferences.BADGES, false);
        isBypassChatBan = getBoolean(Preferences.BYPASS_CHAT_BAN, false);
        isHideGps = getBoolean(Preferences.HIDE_GPS_ERROR, isNeedHideGPS);
        isRedMentionOn = getBoolean(Preferences.CHAR_RED_MENTION, true);
        isNewClipsViewDisabled = getBoolean(Preferences.DISABLE_NEW_CLIPS_VIEW, false);
        isDevModeOn = getBoolean(Preferences.DEV_MODE, false);
        isRobottyServiceEnabled = getBoolean(Preferences.MESSAGE_HISTORY, false);
        isFloatingChatEnabled = getBoolean(Preferences.FLOATING_CHAT, false);
        isCompactViewEnabled = getBoolean(Preferences.COMPACT_FOLLOW_VIEW, false);
        isShowStatsButton = getBoolean(Preferences.STATS_BUTTON, true);
        isShowRefreshButton = getBoolean(Preferences.REFRESH_BUTTON, true);
        isShowBanner = getBoolean(MOD_BANNER_KEY, true);

        mFilterText = getString(Preferences.FILTER_TEXT, null);

        emoteSize = getInt(Preferences.EMOTE_SIZE, EmoteSize.MEDIUM);
        chatWidthScale = getInt(Preferences.CHAT_WIDTH_SCALE, ChatWidthScale.DEFAULT);
        gifs = getInt(Preferences.GIFS, Gifs.STATIC);
        msgDelete = getInt(Preferences.MSG_DELETE_STRATEGY, MsgDelete.DEFAULT);
        exoPlayerSpeed = getString(Preferences.EXOPLAYER_SPEED, ExoPlayerSpeed.DEFAULT);
        miniPlayerSize = getString(Preferences.MINIPLAYER_SCALE, MiniPlayerSize.DEFAULT);
        playerImpl = getInt(Preferences.PLAYER_IMPL, PlayerImpl.AUTO);
        messageFiltering = getInt(Preferences.CHAT_MESSAGE_FILTER_LEVEL, UserMessagesFiltering.DISABLED);
        floatingChatSize = getInt(Preferences.FLOAT_CHAT_SIZE, FloatingChatSize.DEFAULT);
        floatingChatRefresh = getInt(Preferences.FLOATING_REFRESH, FloatingChatRefreshDelay.DEFAULT);
        robottyLimit = getInt(Preferences.ROBOTTY_LIMIT, RobottyLimit.LIMIT1);

        isDarkThemeEnabled = getBoolean(TWITCH_DARK_THEME_KEY, false);
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

    public void updateInt(String key, int val) {
        if (mWrapper == null) {
            Logger.error("mWrapper is null");
            return;
        }

        mWrapper.updateInt(key, val);
    }

    private int getInt(Preferences preference, int def) {
        if (preference == null) {
            throw new IllegalArgumentException("preference is null");
        }

        if (TextUtils.isEmpty(preference.getKey())) {
            Logger.error("empty key");
            return def;
        }

        return mWrapper.getInt(preference.getKey(), def);
    }

    private boolean getBoolean(Preferences preference, boolean def) {
        if (preference == null) {
            throw new IllegalArgumentException("preference is null");
        }

        if (TextUtils.isEmpty(preference.getKey())) {
            Logger.error("empty key");
            return def;
        }

        return mWrapper.getBoolean(preference.getKey(), def);
    }

    private String getString(Preferences preference, String def) {
        if (preference == null) {
            throw new IllegalArgumentException("preference is null");
        }

        if (TextUtils.isEmpty(preference.getKey())) {
            Logger.error("empty key");
            return def;
        }

        return mWrapper.getString(preference.getKey(), def);
    }

    private boolean getBoolean(String key, boolean def) {
        if (mWrapper == null) {
            Logger.error("wrapper is null");
            return def;
        }

        return mWrapper.getBoolean(key, def);
    }


    public boolean isBttvOn() {
        return isBttvEmotesOn;
    }

    public boolean isRedMentionOn() {
        return isRedMentionOn;
    }

    public boolean isForceOldClips() {
        return isNewClipsViewDisabled;
    }

    public boolean isThirdPartyBadgesOn() {
        return isBadgesOn;
    }

    public boolean isBypassChatBan() {
        return isBypassChatBan;
    }

    public boolean isMessageTimestampOn() {
        return isChatTimestampsOn;
    }

    public boolean isDisableAutoplay() {
        return isTheatreAutoplayDisabled;
    }
    
    public boolean isDisableRecentSearch() {
        return isHideRecentSearch;
    }

    public boolean isHideGs() {
        return isHideGps;
    }

    public boolean isDisableRecentWatching() {
        return isHideFollowResume;
    }

    public boolean isDisableRecommendations() {
        return isHideFollowRec;
    }

    public boolean isDisableFollowedGames() {
        return isHideFollowGames;
    }

    public boolean isVolumeSwipeEnabled() {
        return isVolumeSwiperOn;
    }

    public boolean isBrightnessSwipeEnabled() {
        return isBrightnessSwiperOn;
    }

    public boolean isForceOldEmotePicker() {
        return isUseOldEmotePicker;
    }

    public boolean isDevModeOn() {
        return isDevModeOn;
    }

    public boolean isIgnoreSystemMessages() {
        return isChatSystemFilteringOn;
    }

    public boolean isInterceptorOn() {
        return isInterceptorEnabled;
    }

    public boolean isHideDiscoverTab() {
        return isHideDiscoverTab;
    }

    public boolean isHideEsportsTab() {
        return isHideEsportsTab;
    }

    public boolean isFloatingChatEnabled() {
        return isFloatingChatEnabled;
    }

    public boolean isDarkThemeEnabled() {
        return isDarkThemeEnabled;
    }

    public boolean isAdblockEnabled() {
        return isAdblockOn;
    }

    public boolean isMessageHistoryEnabled() {
        return isRobottyServiceEnabled;
    }

    public boolean isCompactViewEnabled() {
        return isCompactViewEnabled;
    }

    public boolean isShowRefreshButton() {
        return isShowRefreshButton;
    }

    public boolean isShowStatsButton() {
        return isShowStatsButton;
    }

    public boolean isShowBanner() {
        return false;

        // return isShowBanner;
    }

    public void disableBanner() {
        updateBoolean(MOD_BANNER_KEY, false);
    }

    public @EmoteSize int getEmoteSize() {
        return emoteSize;
    }

    public @PlayerImpl int getPlayerImplementation() {
        return playerImpl;
    }

    public @UserMessagesFiltering int getChatFiltering() {
        return messageFiltering;
    }

    public @ExoPlayerSpeed String getExoplayerSpeed() {
        return exoPlayerSpeed;
    }

    public @ChatWidthScale int getChatWidthScale() {
        return chatWidthScale;
    }

    public @MiniPlayerSize String getMiniPlayerSize() {
        return miniPlayerSize;
    }

    public @FloatingChatRefreshDelay int getFloatingChatRefresh() {
        return floatingChatRefresh;
    }

    public @FloatingChatSize int getFloatingChatQueueSize() {
        return floatingChatSize;
    }

    public @MsgDelete int getMsgDelete() {
        return msgDelete;
    }

    public @Gifs int getGifsStrategy() {
        return gifs;
    }

    public @RobottyLimit int getMessageHistoryLimit() {
        return robottyLimit;
    }

    public String getFilterText() {
        return mFilterText;
    }

    @Override
    public void onPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TWITCH_DARK_THEME_KEY.equals(key)) {
            isDarkThemeEnabled = getBoolean(TWITCH_DARK_THEME_KEY, false);
        } else {
            updatePreferences();
        }
    }
}
