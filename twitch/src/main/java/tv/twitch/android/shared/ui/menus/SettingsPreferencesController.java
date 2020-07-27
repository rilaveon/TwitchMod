package tv.twitch.android.shared.ui.menus;

import tv.twitch.android.shared.ui.menus.checkable.CheckableGroupModel;
import tv.twitch.android.shared.ui.menus.togglemenu.ToggleMenuModel;


public interface SettingsPreferencesController {
    enum SettingsPreference { // TODO: __REPLACE_CLASS
        // TWITCH STUFF --->
        DarkMode(), // 0
        SmartFeed(), // 1
        InAppAll(), // 2
        InAppFriends(), // 3
        InAppWhispers(), // 4
        InAppFriendRequests(), // 5
        AdTracking(), // 6
        BlockWhispersFromStrangers(), // 7
        PopOutPlayer(), // 8
        BackgroundAudio(), // 9
        ShareActivity(), // 10
        ShowStatsHeader(), // 11
        ShowActivityFeedFollowers(), // 12
        ShowActivityFeedSubs(), // 13
        ShowActivityFeedPrimeSubs(), // 14
        ShowActivityFeedResubs(), // 15
        ShowActivityFeedGiftedSubs(), // 16
        ShowActivityFeedBits(), // 17
        ShowActivityFeedHosts(), // 18
        ShowActivityFeedRaids(), // 19
        ShowActivityFeedRewards(), // 20
        PersonalizedAds(), // 21
        PersonalizedAdVendorBranchio(), // 22
        PersonalizedAdVendorComScore(), // 23
        PersonalizedAdVendorNielsen(), // 24
        PersonalizedAdVendorSalesforce(), // 25
        PersonalizedAdVendorGoogle(), // 26
        FilterRiskyChatMessages(), // 27
        FilterIdentityLanguage(), // 28
        FilterSexuallyExplicitLanguage(), // 29
        FilterAggressiveLanguage(), // 30
        FilterProfanity(), // 31
        BlockGiftedSubs(), // 32
        HideGiftedSubCount(), // 33
        AdditionalAccountCreation(), // 34
        GameBroadcastingViewerCount(), // 35
        // <--- TWITCH STUFF

        BttvEmotes( "nop_bttv_emote", "mod_bttv_settings_bttv_emotes", "mod_bttv_settings_bttv_emotes_desc"),
        BttvEmotesPicker( "nop_emote_picker", "mod_bttv_settings_bttv_widget", "mod_bttv_settings_bttv_widget_desc"),
        Timestamps( "nop_emote_timestamp", "mod_chat_settings_message_timestamp", "mod_chat_settings_message_timestamp_desc"),
        Adblock( "nop_adblock", "mod_player_settings_adblock", "mod_player_settings_adblock_desc"),
        PlayerImpl("nop_player_implementation", "mod_player_settings_player_implementation", "mod_player_settings_player_implementation_desc"),
        FloatingChat("nop_floating_chat", "mod_chat_settings_floating_chat", "mod_chat_settings_floating_chat_desc"),
        VideoDebugPanel( "nop_video_debug_panel", "mod_player_settings_video_debug_panel", "mod_player_settings_video_debug_panel_desc"),
        VolumeSwipe("nop_swipe_volume", "mod_swipe_settings_enable_volume", "mod_swipe_settings_enable_volume_desc"),
        BrightnessSwipe( "nop_swipe_brightness", "mod_swipe_settings_enable_brightness", "mod_swipe_settings_enable_brightness_desc"),
        HideRecommendedStreams("nop_disable_recommendations", "mod_patches_settings_disable_recommendations", "mod_patches_settings_disable_recommendations_desc"),
        HideResumeWatchingStreams( "nop_disable_recent_watching", "mod_patches_settings_disable_resume_watching", "mod_patches_settings_disable_resume_watching_desc"),
        HideFollowedGames("nop_disable_followed_games", "mod_patches_settings_disable_followed_games", "mod_patches_settings_disable_followed_games_desc"),
        HideDiscoverTab( "nop_hide_discover", "mod_patches_settings_hide_discover", "mod_patches_settings_hide_discover_desc"),
        HideEsportsTab("nop_hide_esports", "mod_patches_settings_hide_esports", "mod_patches_settings_hide_esports_desc"),
        RecentSearch( "nop_disable_recent_search", "mod_patches_settings_disable_recent_search", "mod_patches_settings_disable_recent_search_desc"),
        DevMode( "nop_dev", "mod_patches_settings_dev_mode", "mod_patches_settings_dev_mode_desc"),
        AutoPlay("nop_disable_player_autoplay", "mod_player_settings_disable_autoplay", "mod_player_settings_disable_autoplay_desc"),
        Gifs("nop_gifs", "mod_bttv_settings_gifs", "mod_bttv_settings_gifs_desc"),
        EmoteSize("nop_emote_size", "mod_bttv_settings_emote_size", "mod_bttv_settings_emote_size_desc"),
        EmotePickerView("nop_emote_picker_view", "mod_chat_settings_emote_picker", "mod_chat_settings_emote_picker_desc"),
        FollowView("nop_follow_view", "mod_player_settings_follow_view", "mod_player_settings_follow_view_desc"),
        MiniplayerSize("nop_miniplayer_size", "mod_player_settings_miniplayer_size", "mod_player_settings_miniplayer_size_desc"),
        ExoplayerSpeed("nop_exoplayer_speed", "mod_player_settings_exoplayer_speed", "mod_player_settings_exoplayer_speed_desc"),
        FilterLevel("nop_user_messages_filtering", "mod_filtering_settings_level", "mod_filtering_settings_level_desc"),
        FilterSystem("nop_user_messages_filtering_system", "mod_filtering_settings_system", "mod_filtering_settings_system_desc"),
        Interceptor( "nop_dev_interceptor", "mod_patches_settings_interceptor", "mod_patches_settings_interceptor_desc"),
        FfzBadges( "nop_badges", "mod_chat_settings_badges", "mod_chat_settings_badges_desc"),
        BypassChatBan( "nop_bypass_chat_ban", "mod_chat_bypass_chat_ban", "mod_chat_bypass_chat_ban_desc"),
        HideGs( "nop_hide_gs", "mod_patches_nop_hide_gs", "mod_patches_nop_hide_gs_desc"),
        ChatWidthScale( "nop_chat_width_perc", "mod_chat_width_perc", "mod_chat_width_perc_desc");

        private final String mPreferenceKey;
        private final String mPreferenceTitle;
        private final String mPreferenceSummary;

        SettingsPreference() {
            this.mPreferenceKey = null;
            this.mPreferenceTitle = null;
            this.mPreferenceSummary = null;
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