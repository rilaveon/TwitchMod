package tv.twitch.android.mod.bridges;

import java.util.Collection;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonSet;

public class ChatFactory {
    private static final int EMOTE_ID_MASK = 0xFFFF0000;

    private static int sLastEmoteId = Integer.MAX_VALUE;


    private static synchronized String generateEmoteId() {
        if (sLastEmoteId == Integer.MAX_VALUE)
            sLastEmoteId = sLastEmoteId & EMOTE_ID_MASK;

        return String.valueOf(sLastEmoteId++);
    }

    public static ChatEmoticon getEmoticon(Emote emote) {
        ChatEmoticon chatEmoticon = new ChatEmoticonUrl(emote.getUrl(EmoteSize.LARGE));
        chatEmoticon.match = emote.getCode();
        chatEmoticon.isRegex = false;
        chatEmoticon.emoticonId = generateEmoteId();

        return chatEmoticon;
    }

    public static ChatEmoticonSet getSet(String setId, Collection<Emote> emotes) {
        ChatEmoticonSet set = new ChatEmoticonSet();
        set.emoticonSetId = setId;
        set.emoticons = convert(emotes);

        return set;
    }

    private static ChatEmoticon[] convert(Collection<Emote> emoteList) {
        if (emoteList == null || emoteList.size() == 0)
            return new ChatEmoticon[0];

        ChatEmoticon[] chatEmoticons = new ChatEmoticon[emoteList.size()];

        int i = 0;
        for (Emote emote : emoteList) {
            chatEmoticons[i++] = emote.getChatEmoticon();
        }

        return chatEmoticons;
    }
}
