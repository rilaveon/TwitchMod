package com.google.android.exoplayer2;

/**
 * https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/PlaybackParameters.html
 */
public class PlaybackParameters {
    public static final PlaybackParameters DEFAULT = new PlaybackParameters(1.0f);
    public final float speed;

    public PlaybackParameters(float f) {
        speed = f;
    }
}
