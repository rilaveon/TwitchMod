package tv.twitch.android.mod.utils;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import tv.twitch.android.mod.models.preferences.UserMessagesFiltering;
import tv.twitch.chat.ChatLiveMessage;
import tv.twitch.chat.ChatMessageInfo;
import tv.twitch.chat.ChatMessageToken;


public class ChatMesssageFilteringUtil {
    private static final Pattern SPLIT_PATTERN = Pattern.compile("[\\s\\r\\n,]+");

    private final HashSet<String> mWordsBlocklist = new HashSet<>();
    private final HashSet<String> mCaseInsensitiveWordsBlocklist = new HashSet<>();

    private final HashSet<String> mUsernameBlocklist = new HashSet<>();

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

    public boolean isDisabled() {
        return mWordsBlocklist.isEmpty() && mCaseInsensitiveWordsBlocklist.isEmpty() && mUsernameBlocklist.isEmpty();
    }

    private boolean isUsernameInBlocklist(String username) {
        if (TextUtils.isEmpty(username))
            return false;

        return mUsernameBlocklist.contains(username.toLowerCase());
    }

    public ChatLiveMessage[] filterByMessageLevel(ChatLiveMessage[] messages, int viewerId, @UserMessagesFiltering int filteringPreference) {
        if (messages == null || messages.length == 0)
            return null;

        List<ChatLiveMessage> newMessages = new ArrayList<>();
        for (ChatLiveMessage message : messages) {
            if (filterByMessageLevel(message, viewerId, filteringPreference))
                newMessages.add(message);
        }

        if (newMessages.size() == 0)
            return new ChatLiveMessage[0];

        return newMessages.toArray(new ChatLiveMessage[0]);
    }

    public ChatLiveMessage[] filterLiveMessages(ChatLiveMessage[] messages) {
        if (messages == null || messages.length == 0)
            return null;

        List<ChatLiveMessage> newMessages = new ArrayList<>();
        for (ChatLiveMessage message : messages) {
            if (filterLiveMessage(message))
                newMessages.add(message);
        }

        if (newMessages.size() == 0)
            return new ChatLiveMessage[0];

        return newMessages.toArray(new ChatLiveMessage[0]);
    }

    public boolean filterByUsername(String username) {
        if (username == null || username.length() == 0)
            return true;

        return !isUsernameInBlocklist(username);
    }

    public boolean filterByKeywords(String[] words) {
        if (words == null || words.length == 0)
            return true;

        for (String word : words) {
            if (isWordInBlocklist(word)) {
                return false;
            }
        }

        return true;
    }

    public boolean filterLiveMessage(ChatLiveMessage liveMessage) {
        if (liveMessage == null || liveMessage.messageInfo == null)
            return true;

        String userName = liveMessage.messageInfo.userName;
        if (isUsernameInBlocklist(userName))
            return false;

        ChatMessageToken[] tokens = liveMessage.messageInfo.tokens;
        if (tokens == null || tokens.length == 0)
            return true;

        for (ChatMessageToken token : tokens) {
            String text = ChatUtil.getTextFromToken(token);
            if (text != null && !TextUtils.isEmpty(text)) {
                for (String word : SPLIT_PATTERN.split(text)) {
                    if (isWordInBlocklist(word)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean isWordInBlocklist(String word) {
        if (TextUtils.isEmpty(word))
            return false;

        if (mWordsBlocklist.contains(word))
            return true;

        return mCaseInsensitiveWordsBlocklist.contains(word.toLowerCase());
    }

    public synchronized void updateBlocklist(String text) {
        mWordsBlocklist.clear();
        mCaseInsensitiveWordsBlocklist.clear();
        mUsernameBlocklist.clear();

        if (TextUtils.isEmpty(text)) {
            return;
        }

        for (String word : SPLIT_PATTERN.split(text)) {
            if (TextUtils.isEmpty(word))
                continue;

            if (word.length() > 2) {
                switch (word.charAt(0)) {
                    case '@':
                        mUsernameBlocklist.add(word.substring(1).toLowerCase());
                        continue;
                    case '#':
                        mCaseInsensitiveWordsBlocklist.add(word.substring(1).toLowerCase());
                        continue;
                }
            }

            mWordsBlocklist.add(word);
        }
    }

    public boolean filterByMessageLevel(ChatLiveMessage liveMessage, int userId, @UserMessagesFiltering int filtering) {
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

    public static MessageLevel getMessageLevel(ChatMessageInfo messageInfo, int loginUserId) {
        if (messageInfo == null)
            return MessageLevel.UNKNOWN;

        if (messageInfo.userId == loginUserId)
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
