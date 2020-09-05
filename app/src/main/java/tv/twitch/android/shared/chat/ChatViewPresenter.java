package tv.twitch.android.shared.chat;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.models.streams.StreamType;
import tv.twitch.android.shared.chat.events.ChatConnectionEvents;
import tv.twitch.android.shared.chat.observables.ChatConnectionController;


public class ChatViewPresenter {
    public LiveChatSource liveChatSource;

    public ChannelInfo channel;
    public String playbackSessionID;
    public StreamType streamType;

    /* ... */

    public ChatConnectionController chatConnectionController;

    /* ... */

    public final void onUserBanStateUpdated(boolean z) { // TODO: __REPLACE_METHOD
        if (z && Hooks.isBypassChatBanJump()) {
            z = false;
            anonConnect();
        }

        /* ... */
    }

    private void anonConnect() { // TODO: __INJECT_METHOD
        ChannelInfo backupChannelInfo = this.channel;

        chatConnectionController.viewerId = 0;
        this.channel = null;
        this.setChannel(backupChannelInfo, playbackSessionID, streamType);
        this.channel = backupChannelInfo;
    }

    public final void setChannel(ChannelInfo channelInfo2, String str, StreamType streamType2) {/* ... */}

    public final void onChannelStateChanged(ChatConnectionEvents chatConnectionEvents) {
        /* ... */

        if (chatConnectionEvents instanceof ChatConnectionEvents.ChatConnectedEvent) { // TODO: __INJECT_CODE
            if (channel != null && channel.getId() == chatConnectionEvents.getChannelId())
                Hooks.injectRecentMessages(liveChatSource, channel);
        }
    }

    /* ... */
}