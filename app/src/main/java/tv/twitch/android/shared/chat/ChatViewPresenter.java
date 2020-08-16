package tv.twitch.android.shared.chat;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.models.streams.StreamType;
import tv.twitch.android.shared.chat.observables.ChatConnectionController;


public class ChatViewPresenter {
    public ChannelInfo channel;
    public String playbackSessionID;
    public StreamType streamType;
    public LiveChatSource liveChatSource;

    public ChatConnectionController chatConnectionController;


    public final void onUserBanStateUpdated(boolean z) { // TODO: __REPLACE_METHOD
        if (Hooks.isBypassChatBanJump()) {
            anonConnect();
        }
    }

    private void anonConnect() { // TODO: __INJECT_METHOD
        ChannelInfo backupChannelInfo = this.channel;

        chatConnectionController.viewerId = 0;
        this.channel = null;
        this.setChannel(backupChannelInfo, playbackSessionID, streamType);
        this.channel = backupChannelInfo;
    }

    public final void setChannel(ChannelInfo channelInfo2, String str, StreamType streamType2) {

    }
}