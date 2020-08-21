package tv.twitch.android.shared.ui.menus;

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

        BttvEmotes( "nop_bttv_emote", "mod_settings_bttv_emotes", "mod_settings_bttv_emotes_desc"),
        BttvEmotesPicker( "nop_emote_picker", "mod_settings_bttv_widget", "mod_settings_bttv_widget_desc"),
        Timestamps( "nop_emote_timestamp", "mod_settings_message_timestamp", "mod_settings_message_timestamp_desc"),
        Adblock( "nop_adblock", "mod_settings_adblock", "mod_settings_adblock_desc"),
        PlayerImpl("nop_player_implementation", "mod_settings_player_implementation", "mod_settings_player_implementation_desc"),
        FloatingChat("nop_floating_chat", "mod_settings_floating_chat", "mod_settings_floating_chat_desc"),
        VolumeSwipe("nop_swipe_volume", "mod_settings_swipe_volume", "mod_settings_swipe_volume_desc"),
        BrightnessSwipe( "nop_swipe_brightness", "mod_settings_swipe_brightness", "mod_settings_swipe_brightness_desc"),
        HideRecommendedStreams("nop_disable_recommendations", "mod_settings_disable_recommendations", "mod_settings_disable_recommendations_desc"),
        HideResumeWatchingStreams( "nop_disable_recent_watching", "mod_settings_disable_resume_watching", "mod_settings_disable_resume_watching_desc"),
        HideFollowedGames("nop_disable_followed_games", "mod_settings_disable_followed_games", "mod_settings_disable_followed_games_desc"),
        HideDiscoverTab( "nop_hide_discover", "mod_settings_hide_discover", "mod_settings_hide_discover_desc"),
        HideEsportsTab("nop_hide_esports", "mod_settings_hide_esports", "mod_settings_hide_esports_desc"),
        RecentSearch( "nop_disable_recent_search", "mod_settings_disable_recent_search", "mod_settings_disable_recent_search_desc"),
        DevMode( "nop_dev", "mod_settings_dev_mode", "mod_settings_dev_mode_desc"),
        AutoPlay("nop_disable_player_autoplay", "mod_settings_disable_autoplay", "mod_settings_disable_autoplay_desc"),
        Gifs("nop_gifs", "mod_settings_gifs", "mod_settings_gifs_desc"),
        EmoteSize("nop_emote_size", "mod_settings_emote_size", "mod_settings_emote_size_desc"),
        EmotePickerView("nop_emote_picker_view", "mod_settings_old_emote_picker", "mod_settings_old_emote_picker_desc"),
        MiniplayerSize("nop_miniplayer_size", "mod_settings_miniplayer_size", "mod_settings_miniplayer_size_desc"),
        ExoplayerSpeed("nop_exoplayer_speed", "mod_settings_exoplayer_speed", "mod_settings_exoplayer_speed_desc"),
        FilterLevel("nop_user_messages_filtering", "mod_settings_filtering_level", "mod_settings_filtering_level_desc"),
        FilterSystem("nop_user_messages_filtering_system", "mod_settings_filtering_ignore_system", "mod_settings_filtering_ignore_system_desc"),
        Interceptor( "nop_dev_interceptor", "mod_settings_interceptor", "mod_settings_interceptor_desc"),
        FfzBadges( "nop_badges", "mod_settings_badges", "mod_settings_badges_desc"),
        BypassChatBan( "nop_bypass_chat_ban", "mod_settings_bypass_chat_ban", "mod_settings_bypass_chat_ban_desc"),
        HideGs( "nop_hide_gs3", "mod_settings_hide_gs_error", "mod_settings_hide_gs_error_desc"),
        ChatWidthScale( "nop_chat_width_scale", "mod_settings_landscape_chat_width", "mod_settings_landscape_chat_width_desc"),
        RedChatMention( "nop_chat_red_mention", "mod_settings_chat_red_mention", "mod_settings_chat_red_mention_desc"),
        DisableNewClips( "nop_disable_new_clips", "mod_settings_disable_new_clips", "mod_settings_disable_new_clips_desc"),
        FloatingChatQueueSize("nop_floating_queue_size", "mod_settings_floating_queue", "mod_settings_floating_queue_desc"),
        FloatingChatRefresh("nop_floating_refresh", "mod_settings_floating_refresh", "mod_settings_floating_refresh_desc");

        private final String mPreferenceKey;
        private final String mPreferenceTitle;
        private final String mPreferenceSummary;

        SettingsPreference() {
            this.mPreferenceKey = null;
            this.mPreferenceTitle = null;
            this.mPreferenceSummary = null;
        }

        SettingsPreference(String orgName, int pos) {
            this();
        }

        SettingsPreference(String prefKey, String title, String summary) {
            this.mPreferenceKey = prefKey;
            this.mPreferenceTitle = title;
            this.mPreferenceSummary = summary;
        }

        public String getPreferenceKey() {
            return mPreferenceKey;
        }

        public String getNameKey() {
            return mPreferenceTitle;
        }

        public String getDescriptionKey() {
            return mPreferenceSummary;
        }
    }

    void updatePreferenceBooleanState(ToggleMenuModel toggleMenuModel, boolean z);

    void updatePreferenceCheckedState(CheckableGroupModel checkableGroupModel);
}