package tv.twitch.android.shared.ui.menus;

import tv.twitch.android.mod.models.Preferences;
import tv.twitch.android.shared.ui.menus.checkable.CheckableGroupModel;
import tv.twitch.android.shared.ui.menus.togglemenu.ToggleMenuModel;


public interface SettingsPreferencesController {
    enum SettingsPreference { // TODO: __REPLACE_CLASS
        // TWITCH STUFF --->
        DarkMode("DarkMode", 0), // 0
        SmartFeed("SmartFeed", 1), // 1
        InAppAll("InAppAll", 2), // 2
        InAppFriends("InAppFriends", 3), // 3
        InAppWhispers("InAppWhispers", 4), // 4
        InAppFriendRequests("InAppFriendRequests", 5), // 5
        AdTracking("AdTracking", 6), // 6
        BlockWhispersFromStrangers("BlockWhispersFromStrangers", 7), // 7
        PopOutPlayer("PopOutPlayer", 8), // 8
        BackgroundAudio("BackgroundAudio", 9), // 9
        ShareActivity("ShareActivity", 10), // 10
        ShowStatsHeader("ShowStatsHeader", 11), // 11
        ShowActivityFeedFollowers("ShowActivityFeedFollowers", 12), // 12
        ShowActivityFeedSubs("ShowActivityFeedSubs", 13), // 13
        ShowActivityFeedPrimeSubs("ShowActivityFeedPrimeSubs", 14), // 14
        ShowActivityFeedResubs("ShowActivityFeedResubs", 15), // 15
        ShowActivityFeedGiftedSubs("ShowActivityFeedGiftedSubs", 16), // 16
        ShowActivityFeedBits("ShowActivityFeedBits", 17), // 17
        ShowActivityFeedHosts("ShowActivityFeedHosts", 18), // 18
        ShowActivityFeedRaids("ShowActivityFeedRaids", 19), // 19
        ShowActivityFeedRewards("ShowActivityFeedRewards", 20), // 20
        PersonalizedAds("PersonalizedAds", 21), // 21
        PersonalizedAdVendorBranchio("PersonalizedAdVendorBranchio", 22), // 22
        PersonalizedAdVendorComScore("PersonalizedAdVendorComScore", 23), // 23
        PersonalizedAdVendorNielsen("PersonalizedAdVendorNielsen", 24), // 24
        PersonalizedAdVendorSalesforce("PersonalizedAdVendorSalesforce", 25), // 25
        PersonalizedAdVendorGoogle("PersonalizedAdVendorGoogle", 26), // 26
        FilterRiskyChatMessages("FilterRiskyChatMessages", 27), // 27
        FilterIdentityLanguage("FilterIdentityLanguage", 28), // 28
        FilterSexuallyExplicitLanguage("FilterSexuallyExplicitLanguage", 29), // 29
        FilterAggressiveLanguage("FilterAggressiveLanguage", 30), // 30
        FilterProfanity("FilterProfanity", 31), // 31
        BlockGiftedSubs("BlockGiftedSubs", 32), // 32
        HideGiftedSubCount("HideGiftedSubCount", 33), // 33
        AdditionalAccountCreation("AdditionalAccountCreation", 34), // 34
        GameBroadcastingViewerCount("GameBroadcastingViewerCount", 35), // 35
        // <--- TWITCH STUFF

        BttvEmotes(Preferences.BTTV_EMOTES),
        Timestamps(Preferences.CHAT_TIMESTAMPS),
        Adblock(Preferences.ADBLOCK),
        PlayerImpl(Preferences.PLAYER_IMPL),
        VolumeSwipe(Preferences.VOLUME_SWIPER),
        BrightnessSwipe(Preferences.BRIGHTNESS_SWIPER),
        HideRecommendedStreams(Preferences.HIDE_FOLLOW_RECOMMENDED_STREAMS),
        HideResumeWatchingStreams(Preferences.HIDE_FOLLOW_RESUME_STREAMS),
        HideFollowedGames(Preferences.HIDE_FOLLOW_GAMES),
        HideDiscoverTab(Preferences.HIDE_DISCOVER_TAB),
        HideEsportsTab(Preferences.HIDE_ESPORTS_TAB),
        RecentSearch(Preferences.HIDE_RECENT_SEARCH_RESULTS),
        DevMode(Preferences.DEV_MODE),
        AutoPlay(Preferences.DISABLE_THEATRE_AUTOPLAY),
        Gifs(Preferences.GIFS),
        EmoteSize(Preferences.EMOTE_SIZE),
        EmotePickerView(Preferences.EMOTE_PICKER_VIEW),
        MiniplayerSize(Preferences.MINIPLAYER_SCALE),
        ExoplayerSpeed(Preferences.EXOPLAYER_SPEED),
        FilterLevel(Preferences.CHAT_MESSAGE_FILTER_LEVEL),
        FilterSystem(Preferences.CHAT_MESSAGE_FILTER_SYSTEM),
        Interceptor(Preferences.DEV_ENABLE_INTERCEPTOR),
        Badges(Preferences.BADGES),
        BypassChatBan(Preferences.BYPASS_CHAT_BAN),
        HideGs(Preferences.HIDE_GPS_ERROR),
        ChatWidthScale(Preferences.CHAT_WIDTH_SCALE),
        RedChatMention(Preferences.CHAR_RED_MENTION),
        DisableNewClips(Preferences.DISABLE_NEW_CLIPS_VIEW),
        FloatingChatQueueSize(Preferences.FLOAT_CHAT_SIZE),
        FloatingChatRefresh(Preferences.FLOATING_REFRESH),
        MsgDelete(Preferences.MSG_DELETE_STRATEGY),
        FloatingChat(Preferences.FLOATING_CHAT),
        MessageHistory(Preferences.MESSAGE_HISTORY);

        private final Preferences mPreference;

        SettingsPreference(Preferences preference) {
            mPreference = preference;
        }

        SettingsPreference(String orgName, int pos) {
            this();
        }

        SettingsPreference() {
            mPreference = null;
        }

        public Preferences getPreference() {
            return mPreference;
        }
    }

    void updatePreferenceBooleanState(ToggleMenuModel toggleMenuModel, boolean z);

    void updatePreferenceCheckedState(CheckableGroupModel checkableGroupModel);
}