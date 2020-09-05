package tv.twitch.android.models.ads;


public class VASTManagement {
    /* ... */

    public enum AdDeclineReason {
        VAST_AD_API_ERROR,
        VAST_AD_INELIGIBLE_TURBO,
        VAST_AD_INELIGIBLE_CHANSUB,
        VAST_AD_INELIGIBLE_SPACING,
        VAST_AD_INELIGIBLE_DISABLED,
        VAST_AD_INELIGIBLE_CHROMECAST,
        VAST_AD_INELIGIBLE_PIP,
        VAST_AD_INELIGIBLE_RAIDS,
        VAST_AD_VOD_ADS_DISABLED,
        VAST_AD_PROPERTIES_UNAVAILABLE,
        VAST_AD_INELIGIBLE_SQUAD_SECONDARY,
        VAST_AD_INELIGIBLE_AUDIO_ONLY,
        LONG_TAIL,
        REASON_OTHER

        /* ... */
    }

    /* ... */
}
