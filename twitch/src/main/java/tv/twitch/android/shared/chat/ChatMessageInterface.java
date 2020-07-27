package tv.twitch.android.shared.chat;


public interface ChatMessageInterface {
    boolean isDeleted();

    boolean isSystemMessage();

    int getUserId();

    String getUserName();
}