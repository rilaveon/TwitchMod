package tv.twitch.android.shared.chat.util;

import android.content.Context;
import android.text.Spanned;

import io.reactivex.subjects.PublishSubject;
import tv.twitch.android.shared.chat.adapter.item.ChatMessageClickedEvents;


public final class ChatUtil {
    public static final Companion Companion = new Companion();

    /* ... */

    public static final class Companion {
        /* ... */

        public final Spanned createDeletedSpanFromChatMessageSpan(Spanned spanned, Context context, PublishSubject<ChatMessageClickedEvents> publishSubject, boolean z) {
            return null;
        }

        /* ... */
    }

    /* ... */
}
