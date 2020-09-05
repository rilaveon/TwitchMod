package tv.twitch.android.shared.chat.observables;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.channel.ChannelInfo;


public class ChatConnectionController {
    public int viewerId; // TODO: __CHANGE_FIELD_ACCESS

    /* ... */

    public final boolean hasBeenConnected(int i) {
        return false;
    }

    private final void connect(ChannelInfo channelInfo) {
        Hooks.requestEmotes(channelInfo); // TODO: __INJECT_CODE

        /* ... */
    }

    /* ... */
}
