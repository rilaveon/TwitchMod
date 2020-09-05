package tv.twitch.android.shared.chat.events;

public class ChatConnectionEvents {
    /* ... */

    public static final class ChatConnectingEvent extends ChatConnectionEvents {/* ... */}

    public static final class ChatConnectedEvent extends ChatConnectionEvents {/* ... */}

    public int getChannelId() {
        return 0;
    }

    /* ... */
}