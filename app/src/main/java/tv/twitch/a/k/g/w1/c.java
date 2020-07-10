package tv.twitch.a.k.g.w1;

import android.content.Context;

import tv.twitch.android.mod.bridges.ChatEmoticonUrl;
import tv.twitch.chat.ChatEmoticon;

/**
 * Source: ChatEmoticonUtils
 */
public final class c {
    public static final String c(Context context, ChatEmoticon chatEmoticon) { // TODO: __INJECT_METHOD
        if (chatEmoticon instanceof ChatEmoticonUrl) {
            final String url = ((ChatEmoticonUrl) chatEmoticon).getUrl();
            if (url != null)
                return url;
        }

        return org(context, chatEmoticon);
    }

    public static final String org(Context context, ChatEmoticon chatEmoticon) { // TODO: __RENAME__c
        return null;
    }
}
