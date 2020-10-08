package tv.twitch.android.mod.utils;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import tv.twitch.android.mod.models.preferences.UserMessagesFiltering;
import tv.twitch.chat.ChatEmoticonToken;
import tv.twitch.chat.ChatLiveMessage;
import tv.twitch.chat.ChatMentionToken;
import tv.twitch.chat.ChatMessageInfo;
import tv.twitch.chat.ChatMessageToken;
import tv.twitch.chat.ChatTextToken;
import tv.twitch.chat.ChatUrlToken;


public class ChatMesssageFilteringUtil {
    private static final Pattern sSplitWordPattern = Pattern.compile("[\\s\\r\\n,]+");

    private final HashSet<String> mWordBlacklist = new HashSet<>();
    private final HashSet<String> mWordCaseInsensitiveBlacklist = new HashSet<>();

    private final HashSet<String> mUserBlacklist = new HashSet<>();

    public static final ChatMesssageFilteringUtil INSTANCE = new ChatMesssageFilteringUtil();

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

    private static String getTextFromToken(ChatMessageToken token) {
        if (token instanceof ChatUrlToken) {
            if (!((ChatUrlToken) token).hidden)
                return ((ChatUrlToken) token).url;
        } else if (token instanceof ChatTextToken) {
            return ((ChatTextToken) token).text;
        } else if (token instanceof ChatMentionToken) {
            return ((ChatMentionToken) token).text;
        } else if (token instanceof ChatEmoticonToken) {
            return ((ChatEmoticonToken) token).emoticonText;
        }

        return null;
    }

    public boolean isEnabled() {
        return !mWordBlacklist.isEmpty() && !mWordCaseInsensitiveBlacklist.isEmpty() && !mUserBlacklist.isEmpty();
    }

    private boolean isUserInBlacklist(String username) {
        if (TextUtils.isEmpty(username))
            return false;

        return mUserBlacklist.contains(username.toLowerCase());
    }

    public ChatLiveMessage[] filterByLevel(ChatLiveMessage[] messages, int viewerId, @UserMessagesFiltering int filteringPreference) {
        if (messages == null || messages.length == 0)
            return null;

        List<ChatLiveMessage> newMessages = new ArrayList<>();
        for (ChatLiveMessage message : messages) {
            if (filterByLevel(message, viewerId, filteringPreference))
                newMessages.add(message);
        }

        if (newMessages.size() == 0)
            return null;

        return newMessages.toArray(new ChatLiveMessage[0]);
    }

    public ChatLiveMessage[] filterByKeywords(ChatLiveMessage[] messages) {
        if (messages == null || messages.length == 0)
            return null;

        List<ChatLiveMessage> newMessages = new ArrayList<>();
        for (ChatLiveMessage message : messages) {
            if (filterByKeywords(message))
                newMessages.add(message);
        }

        if (newMessages.size() == 0)
            return null;

        return newMessages.toArray(new ChatLiveMessage[0]);
    }

    public boolean filterByKeywords(ChatLiveMessage liveMessage) {
        if (liveMessage == null || liveMessage.messageInfo == null)
            return true;

        String userName = liveMessage.messageInfo.userName;
        if (isUserInBlacklist(userName))
            return false;

        ChatMessageToken[] tokens = liveMessage.messageInfo.tokens;
        if (tokens == null || tokens.length == 0)
            return true;

        for (ChatMessageToken token : tokens) {
            String text = getTextFromToken(token);
            if (text != null && !TextUtils.isEmpty(text)) {
                for (String word : sSplitWordPattern.split(text)) {
                    if (isWordInBlacklist(word)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean isWordInBlacklist(String word) {
        if (TextUtils.isEmpty(word))
            return false;

        if (mWordBlacklist.contains(word))
            return true;

        return mWordCaseInsensitiveBlacklist.contains(word.toLowerCase());
    }

    public synchronized void updateBlacklist(String text) {
        mWordBlacklist.clear();
        mWordCaseInsensitiveBlacklist.clear();
        mUserBlacklist.clear();

        if (TextUtils.isEmpty(text)) {
            return;
        }

        for (String word : sSplitWordPattern.split(text)) {
            if (TextUtils.isEmpty(word))
                continue;

            if (word.length() > 2) {
                char ch = word.charAt(0);

                switch (ch) {
                    case '@':
                        mUserBlacklist.add(word.substring(1).toLowerCase());
                        continue;
                    case '#':
                        mWordCaseInsensitiveBlacklist.add(word.substring(1).toLowerCase());
                        continue;
                }
            }

            mWordBlacklist.add(word);
        }
    }

    public boolean filterByLevel(ChatLiveMessage liveMessage, int userId, @UserMessagesFiltering int filtering) {
        if (liveMessage == null || liveMessage.messageInfo == null)
            return true;

        MessageLevel level = ChatMesssageFilteringUtil.getMessageLevel(liveMessage.messageInfo, userId);
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

    public static MessageLevel getMessageLevel(ChatMessageInfo messageInfo, int userId) {
        if (messageInfo == null)
            return MessageLevel.UNKNOWN;

        if (messageInfo.userId == userId)
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
