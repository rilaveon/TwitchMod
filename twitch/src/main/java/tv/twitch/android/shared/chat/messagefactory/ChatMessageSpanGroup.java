package tv.twitch.android.shared.chat.messagefactory;


public class ChatMessageSpanGroup {
    public static final Companion Companion = new Companion();

    /* ... */

    public static final class Companion {
        /* ... */

        public final ChatMessageSpanGroup getBLANK() {
            return null;
        }

        /* ... */
    }

    /* ... */

    public ChatMessageSpanGroup(CharSequence badgeSpan, CharSequence senderSpan, CharSequence messageSpan) {/* ... */}

    /* ... */
}
