package com.google.android.exoplayer2;


import androidx.annotation.Nullable;

public class ExoPlayerImpl {
    private PlaybackParameters playbackParameters;


    public void setPlaybackParameters(@Nullable PlaybackParameters playbackParameters) { // TODO: __INJECT_METHOD
        this.playbackParameters = playbackParameters;
        notifyListeners(new n(playbackParameters));

    }

    private void notifyListeners(BasePlayer.ListenerInvocation listenerInvocation) {/* ... */}
}
