package tv.twitch.android.models.chat;

public class AutoModMessageFlags {
    private final int aggressiveLevel;
    private final int identityLevel;
    private final int profanityLevel;
    private final int sexualLevel;

    public AutoModMessageFlags(int i, int i2, int i3, int i4) {
        this.identityLevel = i;
        this.sexualLevel = i2;
        this.aggressiveLevel = i3;
        this.profanityLevel = i4;
    }

    public final int getAggressiveLevel() {
        return this.aggressiveLevel;
    }

    public final int getIdentityLevel() {
        return this.identityLevel;
    }

    public final int getProfanityLevel() {
        return this.profanityLevel;
    }

    public final int getSexualLevel() {
        return this.sexualLevel;
    }

    /* ... */
}
