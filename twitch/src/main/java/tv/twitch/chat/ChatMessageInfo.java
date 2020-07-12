package tv.twitch.chat;


import java.util.HashMap;

public class ChatMessageInfo {
    public ChatUserMode userMode = new ChatUserMode();
    public ChatMessageFlags flags = new ChatMessageFlags();
    public HashMap<String, String> messageTags = new HashMap<>();
    public String messageType;
    public String userName;
}
