package tv.twitch.android.models.privacy;


public enum VendorConsentStatus {
    Given,
    Denied,
    Unknown;

    public final boolean isGiven() {
        return this == Given;
    }
}
