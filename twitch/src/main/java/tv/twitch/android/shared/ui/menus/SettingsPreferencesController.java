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
        Adblock(Preferences.PLAYER_ADBLOCK),
        PlayerImplementation(Preferences.PLAYER_IMPELEMTATION),
        VolumeSwiper(Preferences.VOLUME_SWIPER),
        BrightnessSwiper(Preferences.BRIGHTNESS_SWIPER),
        HideRecommendedStreams(Preferences.HIDE_FOLLOW_RECOMMENDED_STREAMS),
        HideResumeWatchingStreams(Preferences.HIDE_FOLLOW_RESUME_STREAMS),
        HideFollowedGames(Preferences.HIDE_FOLLOW_GAMES),
        HideDiscoverTab(Preferences.HIDE_DISCOVER_TAB),
        HideEsportsTab(Preferences.HIDE_ESPORTS_TAB),
        HideRecentSearch(Preferences.HIDE_RECENT_SEARCH_RESULTS),
        DevMode(Preferences.DEV_MODE),
        TheatreAutoPlay(Preferences.DISABLE_THEATRE_AUTOPLAY),
        GifsRenderType(Preferences.GIFS_RENDER_TYPE),
        EmoteSize(Preferences.EMOTE_SIZE),
        OldEmotePicker(Preferences.OLD_EMOTE_PICKER),
        MiniplayerScale(Preferences.MINIPLAYER_SCALE),
        ExoplayerSpeed(Preferences.EXOPLAYER_SPEED),
        FilterLevel(Preferences.CHAT_MESSAGE_FILTER_LEVEL),
        FilterSystem(Preferences.CHAT_MESSAGE_FILTER_SYSTEM),
        Interceptor(Preferences.DEV_INTERCEPTOR),
        CustomBadges(Preferences.CUSTOM_BADGES),
        BypassChatBan(Preferences.SHOW_CHAT_FOR_BANNED_USER),
        ChatWidthScale(Preferences.LANDSCAPE_CHAT_SCALE),
        ChatMentionHighlights(Preferences.CHAT_MENTION_HIGHLIGHTS),
        DisableClipfinity(Preferences.DISABLE_CLIPFINITY),
        FloatingChatQueueSize(Preferences.FLOAT_CHAT_SIZE),
        FloatingChatRefreshRate(Preferences.FLOATING_CHAT_REFRESH_RATE),
        MsgDelete(Preferences.MSG_DELETE_STRATEGY),
        FloatingChat(Preferences.PLAYER_FLOATING_CHAT),
        MessageHistory(Preferences.ROBOTTY_SERVICE),
        MessageHistoryLimit(Preferences.ROBOTTY_LIMIT),
        BetterFollowView(Preferences.COMPACT_PLAYER_FOLLOW_VIEW),
        RefreshButton(Preferences.PLAYER_REFRESH_BUTTON),
        StatButton(Preferences.PLAYER_STAT_BUTTON),
        HideChatRestriction(Preferences.HIDE_CHAT_RESTRICTION),
        WideEmotes(Preferences.SHOW_WIDE_EMOTES);

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