package tv.twitch.android.shared.chat.observables;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.interfaces.IChatConnectionController;
import tv.twitch.android.models.channel.ChannelInfo;


public class ChatConnectionController implements IChatConnectionController { // TODO: __IMPLEMENTATION
    private int viewerId;

    /* ... */

    private final void connect(ChannelInfo channelInfo) {
        Hooks.requestEmotes(channelInfo); // TODO: __INJECT_CODE

        /* ... */
    }

    @Override
    public int getViewerId() { // TODO: __INJECT_METHOD
        return viewerId;
    }

    @Override
    public void setViewerId(int id) { // TODO: __INJECT_METHOD
        viewerId = id;
    }

    /* ... */
}
