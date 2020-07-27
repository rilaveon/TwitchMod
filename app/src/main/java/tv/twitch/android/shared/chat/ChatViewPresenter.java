package tv.twitch.android.shared.chat;


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
        anonConnect();
    }

    private void anonConnect() {
        if (!PreferenceManager.INSTANCE.isBypassChatBan())
            return;

        chatConnectionController.viewerId = 0;
        ChannelInfo channelInfo = this.channel;
        this.channel = null;
        this.setChannel(channelInfo, playbackSessionID, streamType);
    }

    public final void setChannel(ChannelInfo channelInfo2, String str, StreamType streamType2) {

    }
}