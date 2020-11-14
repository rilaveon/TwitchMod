package tv.twitch.android.shared.chat;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.models.streams.StreamType;
import tv.twitch.android.shared.chat.events.ChatConnectionEvents;
import tv.twitch.android.shared.chat.observables.ChatConnectionController;


public class ChatViewPresenter {
    public final LiveChatSource liveChatSource = null;

    private ChannelInfo channel;
    private String playbackSessionID;
    private StreamType streamType;

    /* ... */

    private final ChatConnectionController chatConnectionController = null;

    /* ... */

    public final void onUserBanStateUpdated(boolean z) {
        if (z && Hooks.isBypassChatBanJump()) { // TODO: __INJECT_CODE
            z = false;
            anonConnect();
        }

        /* ... */
    }

    @SuppressWarnings("ConstantConditions")
    private void anonConnect() { // TODO: __INJECT_METHOD
        ChannelInfo backupChannelInfo = this.channel;

        if (chatConnectionController != null)
            chatConnectionController.setViewerId(0);
        this.channel = null;
        this.setChannel(backupChannelInfo, playbackSessionID, streamType);
        this.channel = backupChannelInfo;
    }

    public final void setChannel(ChannelInfo channelInfo2, String str, StreamType streamType2) {/* ... */}

    public final void onChannelStateChanged(ChatConnectionEvents chatConnectionEvents) {
        /* ... */

        maybeInjectRecentMessages(chatConnectionEvents); // TODO: __INJECT_CODE
    }

    private void maybeInjectRecentMessages(ChatConnectionEvents chatConnectionEvent) { // TODO: __INJECT_METHOD
        if (chatConnectionEvent instanceof ChatConnectionEvents.ChatConnectedEvent) {
            if (channel != null && channel.getId() == chatConnectionEvent.getChannelId())
                Hooks.injectRecentMessages(liveChatSource, channel);
        }
    }

    /* ... */
}