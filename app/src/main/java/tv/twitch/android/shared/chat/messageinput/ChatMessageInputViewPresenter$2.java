package tv.twitch.android.shared.chat.messageinput;


import tv.twitch.android.shared.chat.events.ChannelSetEvent;
import tv.twitch.android.mod.bridges.Hooks;


public class ChatMessageInputViewPresenter$2 {
    /* ... */

    public final void accept(ChannelSetEvent channelSetEvent) {
        Hooks.setCurrentChannel(channelSetEvent); // TODO: __INJECT_CODE

        /* ... */
    }

    /* ... */
}
