package tv.twitch.android.shared.chat.observables;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.interfaces.IChatConnectionController;
import tv.twitch.android.models.channel.ChannelInfo;


public class ChatConnectionController implements IChatConnectionController { // TODO: __IMPLEMENTATION
    public int viewerId; // TODO: __CHANGE_FIELD_ACCESS

    /* ... */

    private final void connect(ChannelInfo channelInfo) {
        Hooks.requestEmotes(channelInfo); // TODO: __INJECT_CODE

        /* ... */
    }

    @Override
    public int getViewerId() { // TODO: __INJECT_METHOD
        return viewerId;
    }

    /* ... */
}
