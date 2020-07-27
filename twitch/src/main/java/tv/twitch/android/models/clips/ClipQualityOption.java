package tv.twitch.android.models.clips;


public final class ClipQualityOption {
    private Integer frameRate;
    private String quality;
    private String source;


    public ClipQualityOption(String str, String str2, Integer num) {
        this.quality = str;
        this.source = str2;
        this.frameRate = num;
    }

    public int describeContents() {
        return 0;
    }

    public final Integer getFrameRate() {
        return this.frameRate;
    }

    public final String getQuality() {
        return this.quality;
    }

    public final String getSource() {
        return this.source;
    }

    public String toString() {
        return "ClipQualityOption(quality=" + this.quality + ", source=" + this.source + ", frameRate=" + this.frameRate + ")";
    }
}
