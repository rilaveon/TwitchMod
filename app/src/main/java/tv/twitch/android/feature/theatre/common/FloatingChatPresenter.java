package tv.twitch.android.feature.theatre.common;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.util.LimitedQueue;

public class FloatingChatPresenter {
    public final LimitedQueue<Object> messageQueue = new LimitedQueue<>(Hooks.getFloatingChatQueueSize()); // TODO: __HOOK

}
