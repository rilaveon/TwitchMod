package tv.twitch.android.sdk;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.chat.ChatEmoticonSet;


public class ChatController {
    public ChatEmoticonSet[] mEmoticonSets = null;

    public ChatEmoticonSet[] getEmoticonSets() { // TODO: __REPLACE_METHOD
        return hookSets();
    }

    // TODO: __REPLACE_DIRECT_CALL
    public ChatEmoticonSet[] hookSets() { // TODO: __INJECT_METHOD
        return Hooks.hookChatEmoticonSet(this.mEmoticonSets);
    }
}