package tv.twitch.android.models.chat;


public class MessageToken {
    /* ... */

    public static final class MentionToken extends MessageToken {
        /* ... */

        public final String getUserName() {
            return null;
        }

        /* ... */
    }

    public static final class EmoticonToken extends MessageToken {
        private final String id;
        private final String text;

        public EmoticonToken(String str, String str2) {
            this.text = str;
            this.id = str2;
        }

        public final String getId() {
            return this.id;
        }

        public final String getText() {
            return this.text;
        }

        /* ... */
    }

    public static final class TextToken extends MessageToken {
        private final AutoModMessageFlags flags;
        private final String text;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public TextToken(String str, AutoModMessageFlags autoModMessageFlags) {
            this.text = str;
            this.flags = autoModMessageFlags;
        }

        public final String getText() {
            return this.text;
        }
    }


    /* ... */
}
