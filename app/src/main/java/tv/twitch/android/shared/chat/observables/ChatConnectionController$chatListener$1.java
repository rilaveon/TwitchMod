package tv.twitch.android.shared.chat.observables;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.chat.ChatLiveMessage;


public class ChatConnectionController$chatListener$1 {
    final ChatConnectionController this$0 = null;

    /* ... */

    public void onChannelMessageReceived(int i, ChatLiveMessage[] chatLiveMessageArr) {
        chatLiveMessageArr = Hooks.hookReceivedMessages(this$0, chatLiveMessageArr);  // TODO: __HOOK_PARAM

        /* ... */
    }

    /* ... */
}
