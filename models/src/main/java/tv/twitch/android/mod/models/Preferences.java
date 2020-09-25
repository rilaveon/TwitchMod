package tv.twitch.android.mod.models;


import android.text.TextUtils;

public enum Preferences {
    BTTV_EMOTES("mod_bttv_enabled_v2", "mod_settings_bttv_emotes", "mod_settings_bttv_emotes_desc", Type.BOOLEAN),
    CHAT_TIMESTAMPS("mod_chat_message_timestamp_v2", "mod_settings_message_timestamp", "mod_settings_message_timestamp_desc", Type.BOOLEAN),
    ADBLOCK("mod_player_adblock_v2", "mod_settings_adblock", "mod_settings_adblock_desc", Type.BOOLEAN),
    VOLUME_SWIPER("mod_swipe_volume_v2", "mod_settings_swipe_volume", "mod_settings_swipe_volume_desc", Type.BOOLEAN),
    BRIGHTNESS_SWIPER("mod_swipe_brightness_v2", "mod_settings_swipe_brightness", "mod_settings_swipe_brightness_desc", Type.BOOLEAN),
    HIDE_FOLLOW_RECOMMENDED_STREAMS("mod_hide_follow_recommended_v2", "mod_settings_disable_recommendations", "mod_settings_disable_recommendations_desc", Type.BOOLEAN),
    HIDE_FOLLOW_RESUME_STREAMS("mod_hide_follow_resume_v2", "mod_settings_disable_resume_watching", "mod_settings_disable_resume_watching_desc", Type.BOOLEAN),
    HIDE_FOLLOW_GAMES("mod_hide_follow_games_v2", "mod_settings_disable_followed_games", "mod_settings_disable_followed_games_desc", Type.BOOLEAN),
    HIDE_DISCOVER_TAB("mod_hide_discover_tab_v2", "mod_settings_hide_discover", "mod_settings_hide_discover_desc", Type.BOOLEAN),
    HIDE_ESPORTS_TAB("mod_hide_esports_tab_v2", "mod_settings_hide_esports", "mod_settings_hide_esports_desc", Type.BOOLEAN),
    HIDE_RECENT_SEARCH_RESULTS("mod_hide_recent_search_results_v2", "mod_settings_disable_recent_search", "mod_settings_disable_recent_search_desc", Type.BOOLEAN),
    DEV_MODE("mod_dev_mode_v3", "mod_settings_dev_mode", "mod_settings_dev_mode_desc", Type.BOOLEAN),
    DISABLE_THEATRE_AUTOPLAY("mod_disable_theatre_autoplay_v2", "mod_settings_disable_autoplay", "mod_settings_disable_autoplay_desc", Type.BOOLEAN),
    GIFS("mod_gifs_v2", "mod_settings_gifs", "mod_settings_gifs_desc", Type.INTEGER),
    EMOTE_SIZE("mod_emote_size_v2", "mod_settings_emote_size", "mod_settings_emote_size_desc", Type.INTEGER),
    EMOTE_PICKER_VIEW("mod_emote_picker_view_v2", "mod_settings_old_emote_picker", "mod_settings_old_emote_picker_desc", Type.BOOLEAN),
    MINIPLAYER_SCALE("mod_miniplayer_scale_v2", "mod_settings_miniplayer_size", "mod_settings_miniplayer_size_desc", Type.STRING),
    EXOPLAYER_SPEED("mod_exoplayer_speed_v2", "mod_settings_exoplayer_speed", "mod_settings_exoplayer_speed_desc", Type.INTEGER),
    PLAYER_IMPL("mod_player_impl_v2", "mod_settings_player_implementation", "mod_settings_player_implementation_desc", Type.INTEGER),
    CHAT_MESSAGE_FILTER_LEVEL("mod_chat_message_filter_level_v2", "mod_settings_filtering_level", "mod_settings_filtering_level_desc", Type.INTEGER),
    CHAT_MESSAGE_FILTER_SYSTEM("mod_chat_message_filter_system_v2", "mod_settings_filtering_ignore_system", "mod_settings_filtering_ignore_system_desc", Type.BOOLEAN),
    DEV_ENABLE_INTERCEPTOR("mod_dev_enable_interceptor_v2", "mod_settings_interceptor", "mod_settings_interceptor_desc", Type.BOOLEAN),
    BADGES("mod_badges_v2", "mod_settings_badges", "mod_settings_badges_desc", Type.BOOLEAN),
    BYPASS_CHAT_BAN("mod_bypass_chat_ban_v2", "mod_settings_bypass_chat_ban", "mod_settings_bypass_chat_ban_desc", Type.BOOLEAN),
    HIDE_GPS_ERROR("mod_hide_gps_error_v2", "mod_settings_hide_gs_error", "mod_settings_hide_gs_error_desc", Type.BOOLEAN),
    CHAT_WIDTH_SCALE("mod_chat_width_scale_v2", "mod_settings_landscape_chat_width", "mod_settings_landscape_chat_width_desc", Type.INTEGER),
    CHAR_RED_MENTION("mod_chat_red_mention_v2", "mod_settings_chat_red_mention", "mod_settings_chat_red_mention_desc", Type.BOOLEAN),
    DISABLE_NEW_CLIPS_VIEW("mod_disable_new_clips_view_v2", "mod_settings_disable_new_clips", "mod_settings_disable_new_clips_desc", Type.BOOLEAN),
    FLOAT_CHAT_SIZE("mod_floating_chat_size_v2", "mod_settings_floating_queue", "mod_settings_floating_queue_desc", Type.INTEGER),
    FLOATING_REFRESH("mod_floating_refresh_v2", "mod_settings_floating_refresh", "mod_settings_floating_refresh_desc", Type.INTEGER),
    MSG_DELETE_STRATEGY("mod_msg_delete_strategy_v2", "mod_settings_msg_delete", "mod_settings_msg_delete_desc", Type.INTEGER),
    FLOATING_CHAT("mod_floating_chat_v2", "mod_settings_floating_chat", "mod_settings_floating_chat_desc", Type.BOOLEAN),
    MESSAGE_HISTORY("mod_message_history_v2", "mod_settings_message_history", "mod_settings_message_history_desc", Type.BOOLEAN),
    ROBOTTY_LIMIT("mod_robotty_limit_v2", "mod_settings_message_history_limit", "mod_settings_message_history_limit_desc", Type.INTEGER),
    FILTER_TEXT("mod_filter_text", "mod_settings_blacklist", "mod_settings_blacklist_desc", Type.STRING),
    COMPACT_FOLLOW_VIEW("mod_compact_follow_view", "mod_settings_compact_follow_view", "mod_settings_compact_follow_view_desc", Type.BOOLEAN),
    REFRESH_BUTTON("mod_player_show_refresh_button", "mod_settings_show_refresh_button", "mod_settings_show_refresh_button_desc", Type.BOOLEAN),
    STATS_BUTTON("mod_player_show_stats_button", "mod_settings_show_stats_button", "mod_settings_show_stats_button_desc", Type.BOOLEAN);

    private final String mKey;
    private final String mTitle;
    private final String mDescription;
    private final Type mType;

    public enum Type {
        BOOLEAN,
        INTEGER,
        STRING
    }

    Preferences(String key, String title, String description, Type type) {
        mKey = key;
        mTitle = title;
        mDescription = description;
        mType = type;
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

    public Type getType() {
        return mType;
    }

    public static Preferences findByKey(String key) {
        if (TextUtils.isEmpty(key))
            return null;

        for (Preferences preference : values()) {
            if (preference != null) {
                if (preference.getKey() != null) {
                    if (preference.getKey().equals(key))
                        return preference;
                }
            }
        }

        return null;
    }
}
