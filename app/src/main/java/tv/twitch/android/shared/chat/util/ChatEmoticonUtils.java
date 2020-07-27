package tv.twitch.android.shared.chat.util;


import android.content.Context;

import tv.twitch.android.mod.bridges.models.ChatEmoticonUrl;
import tv.twitch.chat.ChatEmoticon;


public final class ChatEmoticonUtils {
    public static final String getEmoteURL(Context context, ChatEmoticon chatEmoticon) { // TODO: __INJECT_METHOD
        if (chatEmoticon instanceof ChatEmoticonUrl) {
            final String url = ((ChatEmoticonUrl) chatEmoticon).getUrl();
            if (url != null)
                return url;
        }

        return org(context, chatEmoticon);
    }

    public static final String org(Context context, ChatEmoticon chatEmoticon) { // TODO: __RENAME__getEmoteURL
        return null;
    }
}
