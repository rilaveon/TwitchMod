package tv.twitch.android.sdk;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.chat.ChatEmoticonSet;


public class ChatController {
    public ChatEmoticonSet[] mEmoticonSets = null;

    /* ... */

    public ChatEmoticonSet[] getEmoticonSets() { // TODO: __REPLACE_METHOD
        return Hooks.hookChatEmoticonSet(this.mEmoticonSets);
    }

    public static /* synthetic */ ChatEmoticonSet[]access$000(ChatController chatController) { // TODO: __REPLACE_METHOD
        return Hooks.hookChatEmoticonSet(chatController.mEmoticonSets);
    }

    /* ... */
}