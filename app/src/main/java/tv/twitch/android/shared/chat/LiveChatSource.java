package tv.twitch.android.shared.chat;


import java.util.List;

import tv.twitch.android.core.user.TwitchAccountManager;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.shared.chat.events.ChatNoticeEvents;
import tv.twitch.chat.ChatLiveMessage;


public class LiveChatSource {
    public final TwitchAccountManager accountManager = null;


    public final void addMessages(int i, List<? extends ChatLiveMessage> liveMessages /* ... */) {
        liveMessages = Hooks.hookLiveMessages(liveMessages, accountManager.getUsername()); // TODO: __HOOK_PARAM
    }

    public static void addSystemMessage$default(LiveChatSource liveChatSource, String str, boolean z, String str2, int i, Object obj) {
        /* ... */
    }


    public final void onUserNoticeReceived(ChatNoticeEvents noticeEvents) {
        if (noticeEvents instanceof ChatNoticeEvents.FirstTimeChatterNoticeEvent && !Hooks.isJumpSystemIgnore()) { // TODO: __JUMP_HOOK
            /* ... */
        }
    }
}