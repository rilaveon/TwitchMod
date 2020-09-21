package tv.twitch.android.shared.ui.menus;

import tv.twitch.android.mod.models.Preferences;
import tv.twitch.android.shared.ui.menus.checkable.CheckableGroupModel;
import tv.twitch.android.shared.ui.menus.togglemenu.ToggleMenuModel;


public interface SettingsPreferencesController {
    enum SettingsPreference { // TODO: __REPLACE_CLASS
        // TWITCH STUFF --->
        DarkMode,
        SmartFeed,
        InAppAll,
        InAppFriends,
        InAppWhispers,
        InAppFriendRequests,
        AdTracking,
        BlockWhispersFromStrangers,
        PopOutPlayer,
        BackgroundAudio,
        ShareActivity,
        ShowStatsHeader,
        ShowActivityFeedFollowers,
        ShowActivityFeedSubs,
        ShowActivityFeedPrimeSubs,
        ShowActivityFeedResubs,
        ShowActivityFeedGiftedSubs,
        ShowActivityFeedBits,
        ShowActivityFeedHosts,
        ShowActivityFeedRaids,
        ShowActivityFeedRewards,
        PersonalizedAds,
        PersonalizedAdVendorBranchio,
        PersonalizedAdVendorComScore,
        PersonalizedAdVendorNielsen,
        PersonalizedAdVendorSalesforce,
        PersonalizedAdVendorGoogle,
        FilterRiskyChatMessages,
        FilterIdentityLanguage,
        FilterSexuallyExplicitLanguage,
        FilterAggressiveLanguage,
        FilterProfanity,
        BlockGiftedSubs,
        HideGiftedSubCount,
        AdditionalAccountCreation,
        GameBroadcastingViewerCount,
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
        MessageHistory(Preferences.MESSAGE_HISTORY),
        RobottyLimit(Preferences.ROBOTTY_LIMIT),
        BetterFollowView(Preferences.COMPACT_FOLLOW_VIEW),
        RefreshButton(Preferences.REFRESH_BUTTON),
        StatsButton(Preferences.STATS_BUTTON);

        private final Preferences mPreference;

        SettingsPreference(Preferences preference) {
            mPreference = preference;
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