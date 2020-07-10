package tv.twitch.android.shared.ui.menus;

public interface j {
    enum a { // TODO: __REPLACE_CLASS
        // TWITCH STUFF --->
        b(), // 0
        SmartFeed(), // 1
        c(), // 2
        d(), // 3
        InAppWhispers(), // 4
        InAppFriendRequests(), // 5
        e(), // 6
        f(), // 7
        g(), // 8
        BackgroundAudio(), // 9
        h(), // 10
        i(), // 11
        j(), // 12
        k(), // 13
        l(), // 14
        ShowActivityFeedResubs(), // 15
        m(), // 16
        n(), // 17
        o(), // 18
        p(), // 19
        q(), // 20
        r(), // 21
        s(), // 22
        t(), // 23
        u(), // 24
        v(), // 25
        w(), // 26
        x(), // 27
        y(), // 28
        z(), // 29
        A(), // 30
        B(), // 31
        C(), // 32
        D(), // 33
        E(), // 34
        GameBroadcastingViewerCount(), // 35
        // <--- TWITCH STUFF

        BttvEmotes( "nop_bttv_emote", "mod_bttv_settings_bttv_emotes", "mod_bttv_settings_bttv_emotes_desc"),
        BttvEmotesPicker( "nop_emote_picker", "mod_bttv_settings_bttv_widget", "mod_bttv_settings_bttv_widget_desc"),
        Timestamps( "nop_emote_timestamp", "mod_chat_settings_message_timestamp", "mod_chat_settings_message_timestamp_desc"),
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
        Interceptor( "nop_dev_interceptor", "mod_patches_settings_interceptor", "mod_patches_settings_interceptor_desc");

        private final String mPreferenceKey;
        private final String mPreferenceTitle;
        private final String mPreferenceSummary;

        a() {
            this.mPreferenceKey = null;
            this.mPreferenceTitle = null;
            this.mPreferenceSummary = null;
        }

        a(String prefKey, String title, String summary) {
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

    void a(tv.twitch.android.shared.ui.menus.s.b bVar, boolean z);

    void b(tv.twitch.android.shared.ui.menus.k.b bVar);
}