package tv.twitch.android.mod.utils;


import tv.twitch.android.mod.models.preferences.UserMessagesFiltering;
import tv.twitch.chat.ChatLiveMessage;
import tv.twitch.chat.ChatMessageInfo;


public class ChatMesssageFilteringUtil {
    public enum MessageLevel {
        DEFAULT,
        SUBS,
        MODS,
        STAFF,
        BROADCASTER,
        USER,
        SYSTEM,
        UNKNOWN;

        public boolean isHighOrSame(MessageLevel level) {
            if (level == null)
                return false;

            return this.ordinal() >= level.ordinal();
        }
    }

    public static boolean compare(String str, String str2) {
        if (str == null)
            return false;

        if (str2 == null)
            return false;

        return str.equalsIgnoreCase(str2);
    }

    public static boolean filter(ChatLiveMessage liveMessage, String accountName, @UserMessagesFiltering int filtering) {
        if (liveMessage == null || liveMessage.messageInfo == null)
            return true;

        MessageLevel level = ChatMesssageFilteringUtil.getMessageLevel(liveMessage.messageInfo, accountName);

        switch (filtering) {
            case UserMessagesFiltering.DISABLED:
                return true;
            case UserMessagesFiltering.BROADCASTER:
                return level.isHighOrSame(MessageLevel.BROADCASTER);
            case UserMessagesFiltering.MODS:
                return level.isHighOrSame(MessageLevel.MODS);
            case UserMessagesFiltering.SUBS:
                return level.isHighOrSame(MessageLevel.SUBS);
        }

        return true;
    }

    public static MessageLevel getMessageLevel(ChatMessageInfo messageInfo, String account) {
        if (messageInfo == null)
            return MessageLevel.UNKNOWN;

        if (compare(account, messageInfo.userName))
            return MessageLevel.USER;

        if (messageInfo.userMode.broadcaster)
            return MessageLevel.BROADCASTER;

        if (messageInfo.userMode.staff)
            return MessageLevel.STAFF;

        if (messageInfo.userMode.moderator || messageInfo.userMode.globalModerator || messageInfo.userMode.administrator)
            return MessageLevel.MODS;

        if (messageInfo.userMode.vip || messageInfo.userMode.subscriber)
            return MessageLevel.SUBS;

        return MessageLevel.DEFAULT;
    }
}
