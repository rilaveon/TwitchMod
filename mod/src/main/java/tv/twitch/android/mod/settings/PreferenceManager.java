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
import tv.twitch.android.mod.models.preferences.FontSize;
import tv.twitch.android.mod.models.preferences.Gifs;
import tv.twitch.android.mod.models.preferences.MiniPlayerSize;
import tv.twitch.android.mod.models.preferences.MsgDelete;
import tv.twitch.android.mod.models.preferences.PlayerImpl;
import tv.twitch.android.mod.models.preferences.PlayerSeek;
import tv.twitch.android.mod.models.preferences.RobottyLimit;
import tv.twitch.android.mod.models.preferences.UserMessagesFiltering;
import tv.twitch.android.mod.utils.ChatMesssageFilteringUtil;
import tv.twitch.android.mod.utils.Logger;


public class PreferenceManager implements PreferenceWrapper.PreferenceListener {
    public static final String TWITCH_DARK_THEME_KEY = "dark_theme_enabled";
    public static final String MOD_BANNER_KEY = "mod_show_banner";

    public static final PreferenceManager INSTANCE = new PreferenceManager();

    private boolean showBttvEmoteInChat;
    private boolean isChatTimestampsEnabled;
    private boolean isPlayerAdblockOn;
    private boolean isVolumeSwiperEnabled;
    private boolean isBrightnessSwiperEnabled;
    private boolean hideFollowRecommendation;
    private boolean hideFollowResume;
    private boolean hideFollowGame;
    private boolean hideDiscoverTab;
    private boolean hideEsportsTab;
    private boolean hideRecentSearch;
    private boolean isDevModeOn;
    private boolean disableTheatreAutoplay;
    private boolean forceOldEmotePicker;
    private boolean hideSystemMessagesInChat;
    private boolean isInterceptorEnabled;
    private boolean showCustomBadges;
    private boolean showChatForBannedUser;
    private boolean showMentionHighlightsInChat;
    private boolean disableClipfinity;
    private boolean useRobottyService;
    private boolean showFloatingChat;
    private boolean isCompactPlayerFollowViewEnabled;
    private boolean showPlayerStatButton;
    private boolean showPlayerRefreshButton;
    private boolean hideChatRestriction;
    private boolean showWideEmotes;
    private boolean lockSwiper;
    private boolean disableGoogleBilling;
    private boolean showSwipperLockButton;
    private boolean useAutoclicker;

    private boolean shouldShowBanner;
    private boolean isBannerShown;

    private String userFilterText;

    private @EmoteSize int emoteSize;
    private @ChatWidthScale int landscapeChatScale;
    private @Gifs int gifsRenderType;
    private @MsgDelete int msgDeleteStrategy;
    private @ExoPlayerSpeed String exoPlayerSpeed;
    private @MiniPlayerSize String miniPlayerScale;
    private @PlayerImpl int playerImplemetation;
    private @UserMessagesFiltering int filterChatMessageByLevel;
    private @FloatingChatSize int floatingChatSize;
    private @FloatingChatRefreshDelay int floatingChatRefreshRate;
    private @RobottyLimit int robottyLimit;
    private @PlayerSeek int playerForwardSeek;
    private @PlayerSeek int playerBackwardSeek;
    private @PlayerSeek int chatMessageFontSize;

    private PreferenceWrapper mWrapper;

    private boolean isDarkThemeEnabled;

    private PreferenceManager() {}

    public void initialize(Context context) {
        if (mWrapper != null)
            throw new ExceptionInInitializerError("mWrapper is not null");

        mWrapper = new PreferenceWrapper(context);
        initializePreferences();
    }

    private void initializePreferences() {
        setupPreferences();
        mWrapper.registerPreferenceListener(this);
    }

    private void setupPreferences() {
        showBttvEmoteInChat = getBoolean(Preferences.BTTV_EMOTES, true);
        isChatTimestampsEnabled = getBoolean(Preferences.CHAT_TIMESTAMPS, false);
        isPlayerAdblockOn = getBoolean(Preferences.PLAYER_ADBLOCK, true);
        isVolumeSwiperEnabled = getBoolean(Preferences.VOLUME_SWIPER, false);
        isBrightnessSwiperEnabled = getBoolean(Preferences.BRIGHTNESS_SWIPER, false);
        isVolumeSwiperEnabled = getBoolean(Preferences.VOLUME_SWIPER, false);
        hideFollowRecommendation = getBoolean(Preferences.HIDE_FOLLOW_RECOMMENDED_STREAMS, false);
        hideFollowResume = getBoolean(Preferences.HIDE_FOLLOW_RESUME_STREAMS, false);
        hideFollowGame = getBoolean(Preferences.HIDE_FOLLOW_GAMES, false);
        hideDiscoverTab = getBoolean(Preferences.HIDE_DISCOVER_TAB, false);
        hideEsportsTab = getBoolean(Preferences.HIDE_ESPORTS_TAB, false);
        hideRecentSearch = getBoolean(Preferences.HIDE_RECENT_SEARCH_RESULTS, false);
        disableTheatreAutoplay = getBoolean(Preferences.DISABLE_THEATRE_AUTOPLAY, false);
        forceOldEmotePicker = getBoolean(Preferences.OLD_EMOTE_PICKER, false);
        hideSystemMessagesInChat = getBoolean(Preferences.CHAT_MESSAGE_FILTER_SYSTEM, false);
        isInterceptorEnabled = getBoolean(Preferences.DEV_INTERCEPTOR, false);
        showCustomBadges = getBoolean(Preferences.CUSTOM_BADGES, false);
        showChatForBannedUser = getBoolean(Preferences.SHOW_CHAT_FOR_BANNED_USER, false);
        showMentionHighlightsInChat = getBoolean(Preferences.CHAT_MENTION_HIGHLIGHTS, true);
        disableClipfinity = getBoolean(Preferences.DISABLE_CLIPFINITY, false);
        isDevModeOn = getBoolean(Preferences.DEV_MODE, false);
        useRobottyService = getBoolean(Preferences.ROBOTTY_SERVICE, false);
        showFloatingChat = getBoolean(Preferences.PLAYER_FLOATING_CHAT, false);
        isCompactPlayerFollowViewEnabled = getBoolean(Preferences.COMPACT_PLAYER_FOLLOW_VIEW, false);
        showPlayerStatButton = getBoolean(Preferences.PLAYER_STAT_BUTTON, true);
        showPlayerRefreshButton = getBoolean(Preferences.PLAYER_REFRESH_BUTTON, true);
        shouldShowBanner = getBoolean(MOD_BANNER_KEY, true);
        hideChatRestriction = getBoolean(Preferences.HIDE_CHAT_RESTRICTION, false);
        showWideEmotes = getBoolean(Preferences.SHOW_WIDE_EMOTES, false);
        disableGoogleBilling = getBoolean(Preferences.DISABLE_GOOGLE_BILLING, false);
        showSwipperLockButton = getBoolean(Preferences.SWIPPER_LOCK_BUTTON, false);
        useAutoclicker = getBoolean(Preferences.AUTOCLICKER, true);

        userFilterText = getString(Preferences.USER_FILTER_TEXT, null);

        emoteSize = getInt(Preferences.EMOTE_SIZE, EmoteSize.MEDIUM);
        landscapeChatScale = getInt(Preferences.LANDSCAPE_CHAT_SCALE, ChatWidthScale.DEFAULT);
        gifsRenderType = getInt(Preferences.GIFS_RENDER_TYPE, Gifs.STATIC);
        msgDeleteStrategy = getInt(Preferences.MSG_DELETE_STRATEGY, MsgDelete.DEFAULT);
        exoPlayerSpeed = getString(Preferences.EXOPLAYER_SPEED, ExoPlayerSpeed.DEFAULT);
        miniPlayerScale = getString(Preferences.MINIPLAYER_SCALE, MiniPlayerSize.DEFAULT);
        playerImplemetation = getInt(Preferences.PLAYER_IMPELEMTATION, PlayerImpl.AUTO);
        filterChatMessageByLevel = getInt(Preferences.CHAT_MESSAGE_FILTER_LEVEL, UserMessagesFiltering.DISABLED);
        floatingChatSize = getInt(Preferences.FLOAT_CHAT_SIZE, FloatingChatSize.DEFAULT);
        floatingChatRefreshRate = getInt(Preferences.FLOATING_CHAT_REFRESH_RATE, FloatingChatRefreshDelay.DEFAULT);
        robottyLimit = getInt(Preferences.ROBOTTY_LIMIT, RobottyLimit.LIMIT1);
        playerForwardSeek = getInt(Preferences.PLAYER_FORWARD_SEEK, PlayerSeek.THIRTY);
        playerBackwardSeek = getInt(Preferences.PLAYER_BACKWARD_SEEK, PlayerSeek.TEN);
        chatMessageFontSize = getInt(Preferences.CHAT_MESSAGE_FONT_SIZE, FontSize.DEFAULT);

        isDarkThemeEnabled = getBoolean(TWITCH_DARK_THEME_KEY, false);

        lockSwiper = false;
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


    public boolean showBttvEmotesInChat() {
        return showBttvEmoteInChat;
    }

    public boolean highlightMentionMessage() {
        return showMentionHighlightsInChat;
    }

    public boolean disableClipfinity() {
        return disableClipfinity;
    }

    public boolean showCustomBadges() {
        return showCustomBadges;
    }

    public boolean showChatForBannedUser() {
        return showChatForBannedUser;
    }

    public boolean isChatTimestampsEnabled() {
        return isChatTimestampsEnabled;
    }

    public boolean disableTheatreAutoplay() {
        return disableTheatreAutoplay;
    }
    
    public boolean hideRecentSearchResult() {
        return hideRecentSearch;
    }

    public boolean hideFollowResumeSection() {
        return hideFollowResume;
    }

    public boolean hideFollowRecommendationSection() {
        return hideFollowRecommendation;
    }

    public boolean hideFollowGameSection() {
        return hideFollowGame;
    }

    public boolean isVolumeSwiperEnabled() {
        return isVolumeSwiperEnabled;
    }

    public boolean isBrightnessSwiperEnabled() {
        return isBrightnessSwiperEnabled;
    }

    public boolean forceOldEmotePickerView() {
        return forceOldEmotePicker;
    }

    public boolean isDevModeOn() {
        return isDevModeOn;
    }

    public boolean hideSystemMessagesInChat() {
        return hideSystemMessagesInChat;
    }

    public boolean isInterceptorEnabled() {
        return isInterceptorEnabled;
    }

    public boolean hideDiscoverTab() {
        return hideDiscoverTab;
    }

    public boolean hideEsportsTab() {
        return hideEsportsTab;
    }

    public boolean showFloatingChat() {
        return showFloatingChat;
    }

    public boolean isDarkThemeEnabled() {
        return isDarkThemeEnabled;
    }

    public boolean isPlayerAdblockOn() {
        return isPlayerAdblockOn;
    }

    public boolean showMessageHistory() {
        return useRobottyService;
    }

    public boolean isCompactPlayerFollowViewEnabled() {
        return isCompactPlayerFollowViewEnabled;
    }

    public boolean shouldShowPlayerRefreshButton() {
        return showPlayerRefreshButton;
    }

    public boolean shouldShowPlayerStatButton() {
        return showPlayerStatButton;
    }

    public boolean isBannerShown() {
        return isBannerShown;
    }

    public void setBannerShown(boolean z) {
        isBannerShown = z;
    }

    public boolean shouldShowBanner() {
        return shouldShowBanner;
    }

    public void setShouldShowBanner(boolean z) {
        updateBoolean(MOD_BANNER_KEY, z);
    }

    public boolean hideChatRestriction() {
        return hideChatRestriction;
    }

    public boolean fixWideEmotes() {
        return showWideEmotes;
    }

    public boolean isGoogleBillingDisabled() {
        return disableGoogleBilling;
    }

    public boolean isAutoclickerEnabled() {
        return useAutoclicker;
    }

    public @EmoteSize int getEmoteSize() {
        return emoteSize;
    }

    public @PlayerImpl int getPlayerImplementation() {
        return playerImplemetation;
    }

    public @UserMessagesFiltering int getFilterMessageLevel() {
        return filterChatMessageByLevel;
    }

    public @ExoPlayerSpeed String getExoplayerSpeed() {
        return exoPlayerSpeed;
    }

    public @ChatWidthScale int getLandscapeChatScale() {
        return landscapeChatScale;
    }

    public @MiniPlayerSize String getMiniPlayerScale() {
        return miniPlayerScale;
    }

    public @FloatingChatRefreshDelay int getFloatingChatRefreshRate() {
        return floatingChatRefreshRate;
    }

    public @FloatingChatSize int getFloatingChatQueueSize() {
        return floatingChatSize;
    }

    public @MsgDelete int getMsgDelete() {
        return msgDeleteStrategy;
    }

    public @Gifs int getGifsStrategy() {
        return gifsRenderType;
    }

    public @RobottyLimit int getMessageHistoryLimit() {
        return robottyLimit;
    }

    public @PlayerSeek int getPlayerForwardSeek() {
        return playerForwardSeek;
    }

    public @FontSize int getChatMessageFontSize() {
        return chatMessageFontSize;
    }

    public @PlayerSeek int getPlayerBackwardSeek() {
        return playerBackwardSeek;
    }

    public String getUserFilterText() {
        return userFilterText;
    }

    public boolean isUserFilterTextEmpty() {
        return getUserFilterText().isEmpty();
    }

    public boolean shouldLockSwiper() {
        return lockSwiper;
    }

    public void setLockSwiper(boolean z) {
        lockSwiper = z;
    }

    public boolean shouldShowLockButton() {
        return showSwipperLockButton;
    }

    @Override
    public void onPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case TWITCH_DARK_THEME_KEY:
                isDarkThemeEnabled = getBoolean(TWITCH_DARK_THEME_KEY, false);
                return;
            case MOD_BANNER_KEY:
                shouldShowBanner = sharedPreferences.getBoolean(MOD_BANNER_KEY, false);
                return;
        }

        Preferences preference = Preferences.lookupKey(key);
        if (preference == null) {
            Logger.warning("Preference not found. Key: " + key);
            return;
        }

        switch (preference) {
            case CUSTOM_BADGES:
                showCustomBadges = sharedPreferences.getBoolean(key, showCustomBadges);
                break;
            case GIFS_RENDER_TYPE:
                gifsRenderType = sharedPreferences.getInt(key, gifsRenderType);
                break;
            case PLAYER_ADBLOCK:
                isPlayerAdblockOn = sharedPreferences.getBoolean(key, isPlayerAdblockOn);
                break;
            case DEV_MODE:
                isDevModeOn = sharedPreferences.getBoolean(key, isDevModeOn);
                break;
            case EMOTE_SIZE:
                emoteSize = sharedPreferences.getInt(key, emoteSize);
                break;
            case BTTV_EMOTES:
                showBttvEmoteInChat = sharedPreferences.getBoolean(key, showBttvEmoteInChat);
                break;
            case USER_FILTER_TEXT:
                userFilterText = sharedPreferences.getString(key, userFilterText);
                ChatMesssageFilteringUtil.INSTANCE.updateBlocklist(userFilterText);
                break;
            case PLAYER_IMPELEMTATION:
                playerImplemetation = sharedPreferences.getInt(key, playerImplemetation);
                break;
            case PLAYER_STAT_BUTTON:
                showPlayerStatButton = sharedPreferences.getBoolean(key, showPlayerStatButton);
                break;
            case PLAYER_FLOATING_CHAT:
                showFloatingChat = sharedPreferences.getBoolean(key, showFloatingChat);
                break;
            case ROBOTTY_LIMIT:
                robottyLimit = sharedPreferences.getInt(key, robottyLimit);
                break;
            case VOLUME_SWIPER:
                isVolumeSwiperEnabled = sharedPreferences.getBoolean(key, isVolumeSwiperEnabled);
                break;
            case PLAYER_REFRESH_BUTTON:
                showPlayerRefreshButton = sharedPreferences.getBoolean(key, showPlayerRefreshButton);
                break;
            case SHOW_CHAT_FOR_BANNED_USER:
                showChatForBannedUser = sharedPreferences.getBoolean(key, showChatForBannedUser);
                break;
            case CHAT_TIMESTAMPS:
                isChatTimestampsEnabled = sharedPreferences.getBoolean(key, isChatTimestampsEnabled);
                break;
            case EXOPLAYER_SPEED:
                exoPlayerSpeed = sharedPreferences.getString(key, exoPlayerSpeed);
                break;
            case FLOAT_CHAT_SIZE:
                floatingChatSize = sharedPreferences.getInt(key, floatingChatSize);
                break;
            case ROBOTTY_SERVICE:
                useRobottyService = sharedPreferences.getBoolean(key, useRobottyService);
                break;
            case CHAT_MENTION_HIGHLIGHTS:
                showMentionHighlightsInChat = sharedPreferences.getBoolean(key, showMentionHighlightsInChat);
                break;
            case LANDSCAPE_CHAT_SCALE:
                landscapeChatScale = sharedPreferences.getInt(key, landscapeChatScale);
                break;
            case FLOATING_CHAT_REFRESH_RATE:
                floatingChatRefreshRate = sharedPreferences.getInt(key, floatingChatRefreshRate);
                break;
            case HIDE_ESPORTS_TAB:
                hideEsportsTab = sharedPreferences.getBoolean(key, hideEsportsTab);
                break;
            case MINIPLAYER_SCALE:
                miniPlayerScale = sharedPreferences.getString(key, miniPlayerScale);
                break;
            case BRIGHTNESS_SWIPER:
                isBrightnessSwiperEnabled = sharedPreferences.getBoolean(key, isBrightnessSwiperEnabled);
                break;
            case OLD_EMOTE_PICKER:
                forceOldEmotePicker = sharedPreferences.getBoolean(key, forceOldEmotePicker);
                break;
            case HIDE_DISCOVER_TAB:
                hideDiscoverTab = sharedPreferences.getBoolean(key, hideDiscoverTab);
                break;
            case HIDE_FOLLOW_GAMES:
                hideFollowGame = sharedPreferences.getBoolean(key, hideFollowGame);
                break;
            case COMPACT_PLAYER_FOLLOW_VIEW:
                isCompactPlayerFollowViewEnabled = sharedPreferences.getBoolean(key, isCompactPlayerFollowViewEnabled);
                break;
            case MSG_DELETE_STRATEGY:
                msgDeleteStrategy = sharedPreferences.getInt(key, msgDeleteStrategy);
                break;
            case SHOW_WIDE_EMOTES:
                showWideEmotes = sharedPreferences.getBoolean(key, showWideEmotes);
                break;
            case HIDE_CHAT_RESTRICTION:
                hideChatRestriction = sharedPreferences.getBoolean(key, hideChatRestriction);
                break;
            case DEV_INTERCEPTOR:
                isInterceptorEnabled = sharedPreferences.getBoolean(key, isInterceptorEnabled);
                break;
            case DISABLE_CLIPFINITY:
                disableClipfinity = sharedPreferences.getBoolean(key, disableClipfinity);
                break;
            case DISABLE_THEATRE_AUTOPLAY:
                disableTheatreAutoplay = sharedPreferences.getBoolean(key, disableTheatreAutoplay);
                break;
            case CHAT_MESSAGE_FILTER_LEVEL:
                filterChatMessageByLevel = sharedPreferences.getInt(key, filterChatMessageByLevel);
                break;
            case CHAT_MESSAGE_FILTER_SYSTEM:
                hideSystemMessagesInChat = sharedPreferences.getBoolean(key, hideSystemMessagesInChat);
                break;
            case HIDE_FOLLOW_RESUME_STREAMS:
                hideFollowResume = sharedPreferences.getBoolean(key, hideFollowResume);
                break;
            case HIDE_RECENT_SEARCH_RESULTS:
                hideRecentSearch = sharedPreferences.getBoolean(key, hideRecentSearch);
                break;
            case HIDE_FOLLOW_RECOMMENDED_STREAMS:
                hideFollowRecommendation = sharedPreferences.getBoolean(key, hideFollowRecommendation);
                break;
            case DISABLE_GOOGLE_BILLING:
                disableGoogleBilling = sharedPreferences.getBoolean(key, disableGoogleBilling);
                break;
            case SWIPPER_LOCK_BUTTON:
                showSwipperLockButton = sharedPreferences.getBoolean(key, showSwipperLockButton);
                break;
            case PLAYER_FORWARD_SEEK:
                playerForwardSeek = sharedPreferences.getInt(key, playerForwardSeek);
                break;
            case PLAYER_BACKWARD_SEEK:
                playerBackwardSeek = sharedPreferences.getInt(key, playerBackwardSeek);
                break;
            case CHAT_MESSAGE_FONT_SIZE:
                chatMessageFontSize = sharedPreferences.getInt(key, chatMessageFontSize);
                break;
            case AUTOCLICKER:
                useAutoclicker = sharedPreferences.getBoolean(key, useAutoclicker);
                break;
            default:
                Logger.warning("Check key: " + key);
        }
    }
}
