package tv.twitch.android.shared.player.core;

import tv.twitch.android.mod.utils.Logger;

public class CorePlayer implements TwitchPlayer {


    @Override
    public void setSpeed(float speed) { // TODO: __INJECT_METHOD
        Logger.debug("Try set speed on core player");
    }

    @Override
    public boolean isExoPlayer() { // TODO: __INJECT_METHOD
        return false;
    }
}
