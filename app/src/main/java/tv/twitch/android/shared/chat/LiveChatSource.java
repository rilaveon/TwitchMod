package tv.twitch.android.shared.chat;


import tv.twitch.android.core.user.TwitchAccountManager;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.interfaces.ILiveChatSource;
import tv.twitch.android.shared.chat.events.ChatNoticeEvents;
import tv.twitch.android.shared.chat.messagefactory.MessageListAdapterBinder;


public class LiveChatSource implements ILiveChatSource { // TODO: __IMPLEMENT
    private final MessageListAdapterBinder messageListAdapterBinder = null;
    public final TwitchAccountManager accountManager = null;

    /* ... */

    public final void onUserNoticeReceived(ChatNoticeEvents noticeEvents) {
        if (noticeEvents instanceof ChatNoticeEvents.FirstTimeChatterNoticeEvent && !Hooks.isJumpSystemIgnore()) { // TODO: __JUMP_HOOK
            /* ... */
        }

        /* ... */

        if (noticeEvents instanceof ChatNoticeEvents.SubscriptionNoticeEvent && !Hooks.isJumpSystemIgnore()) { // TODO: __JUMP_HOOK
            /* ... */
        }

        /* ... */

        if (noticeEvents instanceof ChatNoticeEvents.RewardGiftNoticeEvent && !Hooks.isJumpSystemIgnore()) { // TODO: __JUMP_HOOK
            /* ... */
        }

        /* ... */
    }

    public final void addSystemMessage(String str, boolean z, String str2) {
        /* ... */

        return;
    }

    @Override
    public void addRecentMessage(String line) { // TODO: __INJECT_METHOD
        addSystemMessage(line, false, null);
    }
}