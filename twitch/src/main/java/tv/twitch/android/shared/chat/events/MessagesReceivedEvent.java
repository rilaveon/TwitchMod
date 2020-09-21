package tv.twitch.android.shared.chat.events;


import java.util.List;

import tv.twitch.chat.ChatLiveMessage;


public final class MessagesReceivedEvent {
    /* ... */

    public MessagesReceivedEvent(int i, List<? extends ChatLiveMessage> list) {/* ... */}

    public final List<ChatLiveMessage> getMessages() {
        return null;
    }

    public final int getChannelId() {
        return 0;
    }

    /* ... */
}
