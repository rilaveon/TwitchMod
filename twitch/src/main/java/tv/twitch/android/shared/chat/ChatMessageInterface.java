package tv.twitch.android.shared.chat;


import java.util.List;

import tv.twitch.android.models.chat.MessageToken;

public interface ChatMessageInterface {
    boolean isDeleted();


    List<MessageToken> getTokens();

    boolean isSystemMessage();

    int getUserId();

    String getUserName();
}