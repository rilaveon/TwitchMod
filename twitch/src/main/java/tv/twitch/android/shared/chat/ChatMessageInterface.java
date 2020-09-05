package tv.twitch.android.shared.chat;


import java.util.List;

import tv.twitch.android.models.chat.MessageToken;


public interface ChatMessageInterface {
    /* ... */

    List<MessageToken> getTokens();

    int getUserId();

    boolean isDeleted();

    boolean isSystemMessage();

    /* ... */
}