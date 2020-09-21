package tv.twitch.android.mod.models;


public enum Preferences {
    BTTV_EMOTES("mod_bttv_enabled_v2", "mod_settings_bttv_emotes", "mod_settings_bttv_emotes_desc"),
    CHAT_TIMESTAMPS("mod_chat_message_timestamp_v2", "mod_settings_message_timestamp", "mod_settings_message_timestamp_desc"),
    ADBLOCK("mod_player_adblock_v2", "mod_settings_adblock", "mod_settings_adblock_desc"),
    VOLUME_SWIPER("mod_swipe_volume_v2", "mod_settings_swipe_volume", "mod_settings_swipe_volume_desc"),
    BRIGHTNESS_SWIPER("mod_swipe_brightness_v2", "mod_settings_swipe_brightness", "mod_settings_swipe_brightness_desc"),
    HIDE_FOLLOW_RECOMMENDED_STREAMS("mod_hide_follow_recommended_v2", "mod_settings_disable_recommendations", "mod_settings_disable_recommendations_desc"),
    HIDE_FOLLOW_RESUME_STREAMS("mod_hide_follow_resume_v2", "mod_settings_disable_resume_watching", "mod_settings_disable_resume_watching_desc"),
    HIDE_FOLLOW_GAMES("mod_hide_follow_games_v2", "mod_settings_disable_followed_games", "mod_settings_disable_followed_games_desc"),
    HIDE_DISCOVER_TAB("mod_hide_discover_tab_v2", "mod_settings_hide_discover", "mod_settings_hide_discover_desc"),
    HIDE_ESPORTS_TAB("mod_hide_esports_tab_v2", "mod_settings_hide_esports", "mod_settings_hide_esports_desc"),
    HIDE_RECENT_SEARCH_RESULTS("mod_hide_recent_search_results_v2", "mod_settings_disable_recent_search", "mod_settings_disable_recent_search_desc"),
    DEV_MODE("mod_dev_mode_v2", "mod_settings_dev_mode", "mod_settings_dev_mode_desc"),
    DISABLE_THEATRE_AUTOPLAY("mod_disable_theatre_autoplay_v2", "mod_settings_disable_autoplay", "mod_settings_disable_autoplay_desc"),
    GIFS("mod_gifs_v2", "mod_settings_gifs", "mod_settings_gifs_desc"),
    EMOTE_SIZE("mod_emote_size_v2", "mod_settings_emote_size", "mod_settings_emote_size_desc"),
    EMOTE_PICKER_VIEW("mod_emote_picker_view_v2", "mod_settings_old_emote_picker", "mod_settings_old_emote_picker_desc"),
    MINIPLAYER_SCALE("mod_miniplayer_scale_v2", "mod_settings_miniplayer_size", "mod_settings_miniplayer_size_desc"),
    EXOPLAYER_SPEED("mod_exoplayer_speed_v2", "mod_settings_exoplayer_speed", "mod_settings_exoplayer_speed_desc"),
    PLAYER_IMPL("mod_player_impl_v2", "mod_settings_player_implementation", "mod_settings_player_implementation_desc"),
    CHAT_MESSAGE_FILTER_LEVEL("mod_chat_message_filter_level_v2", "mod_settings_filtering_level", "mod_settings_filtering_level_desc"),
    CHAT_MESSAGE_FILTER_SYSTEM("mod_chat_message_filter_system_v2", "mod_settings_filtering_ignore_system", "mod_settings_filtering_ignore_system_desc"),
    DEV_ENABLE_INTERCEPTOR("mod_dev_enable_interceptor_v2", "mod_settings_interceptor", "mod_settings_interceptor_desc"),
    BADGES("mod_badges_v2", "mod_settings_badges", "mod_settings_badges_desc"),
    BYPASS_CHAT_BAN("mod_bypass_chat_ban_v2", "mod_settings_bypass_chat_ban", "mod_settings_bypass_chat_ban_desc"),
    HIDE_GPS_ERROR("mod_hide_gps_error_v2", "mod_settings_hide_gs_error", "mod_settings_hide_gs_error_desc"),
    CHAT_WIDTH_SCALE("mod_chat_width_scale_v2", "mod_settings_landscape_chat_width", "mod_settings_landscape_chat_width_desc"),
    CHAR_RED_MENTION("mod_chat_red_mention_v2", "mod_settings_chat_red_mention", "mod_settings_chat_red_mention_desc"),
    DISABLE_NEW_CLIPS_VIEW("mod_disable_new_clips_view_v2", "mod_settings_disable_new_clips", "mod_settings_disable_new_clips_desc"),
    FLOAT_CHAT_SIZE("mod_floating_chat_size_v2", "mod_settings_floating_queue", "mod_settings_floating_queue_desc"),
    FLOATING_REFRESH("mod_floating_refresh_v2", "mod_settings_floating_refresh", "mod_settings_floating_refresh_desc"),
    MSG_DELETE_STRATEGY("mod_msg_delete_strategy_v2", "mod_settings_msg_delete", "mod_settings_msg_delete_desc"),
    FLOATING_CHAT("mod_floating_chat_v2", "mod_settings_floating_chat", "mod_settings_floating_chat_desc"),
    MESSAGE_HISTORY("mod_message_history_v2", "mod_settings_message_history", "mod_settings_message_history_desc"),
    ROBOTTY_LIMIT("mod_robotty_limit_v2", "mod_settings_message_history_limit", "mod_settings_message_history_limit_desc"),
    FILTER_TEXT("mod_filter_text", "mod_settings_blacklist", "mod_settings_blacklist_desc"),
    COMPACT_FOLLOW_VIEW("mod_compact_follow_view", "mod_settings_compact_follow_view", "mod_settings_compact_follow_view_desc"),
    REFRESH_BUTTON("mod_player_show_refresh_button", "mod_settings_show_refresh_button", "mod_settings_show_refresh_button_desc"),
    STATS_BUTTON("mod_player_show_stats_button", "mod_settings_show_stats_button", "mod_settings_show_stats_button_desc");

    private final String mKey;
    private final String mTitle;
    private final String mDescription;

    Preferences(String key, String title, String description) {
        mKey = key;
        mTitle = title;
        mDescription = description;
    }

    public String getKey() {
        return mKey;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
