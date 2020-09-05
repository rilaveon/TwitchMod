package tv.twitch.android.shared.chat;


import java.util.List;

import tv.twitch.android.core.user.TwitchAccountManager;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.interfaces.ILiveChatSource;
import tv.twitch.android.shared.chat.events.ChatNoticeEvents;
import tv.twitch.chat.ChatLiveMessage;


public class LiveChatSource implements ILiveChatSource { // TODO: __IMPLEMENT
    public final TwitchAccountManager accountManager = null;

    /* ... */

    public final void addMessages(int i, List<? extends ChatLiveMessage> liveMessages /* ... */) {
        liveMessages = Hooks.hookLiveMessages(liveMessages, accountManager); // TODO: __HOOK_PARAM

        /* ... */
    }

    public final void onUserNoticeReceived(ChatNoticeEvents noticeEvents) {
        if (noticeEvents instanceof ChatNoticeEvents.FirstTimeChatterNoticeEvent && !Hooks.isJumpSystemIgnore()) { // TODO: __JUMP_HOOK
            /* ... */
        }

        /* ... */

        if (noticeEvents instanceof ChatNoticeEvents.SubscriptionNoticeEvent && !Hooks.isJumpSystemIgnore()) { // TODO: __JUMP_HOOK
            /* ... */
        }

        /* ... */
    }

    public final void addSystemMessage(String str, boolean z, String str2) {
        /* ... */

        return;
    }

    @Override
    public void addMessage(String line) { // TODO: __INJECT_METHOD
        addSystemMessage(line, false, null);
    }
}