package tv.twitch.android.mod.bridges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import tv.twitch.a.k.l.j.d;
import tv.twitch.a.k.l.j.k.c;
import tv.twitch.a.k.l.k.a;
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

    private static tv.twitch.a.k.l.j.k.c getEmote(Emote emote) {
        tv.twitch.a.k.l.k.a emoteMessageInput = new a(emote.getCode(), "-1", false);
        tv.twitch.a.k.l.k.c emotePicker = new tv.twitch.a.k.l.k.c("-1", emote.getCode(), Collections.<tv.twitch.a.k.l.k.c>emptyList());
        tv.twitch.a.k.l.j.d.d1 clickedEmote = new d.d1.b(emotePicker, emoteMessageInput, Collections.<d.d1.b>emptyList());
        return new EmoteUiModelWithUrl("-1", false, false, clickedEmote, emote.getUrl(EmoteSize.LARGE));
    }

    public static tv.twitch.a.k.l.j.k.d getEmoteSetUi(Collection<Emote> emoteList, Integer emoteSetId) {
        tv.twitch.a.k.l.j.k.a header = new tv.twitch.a.k.l.j.k.a.b(emoteSetId, true, tv.twitch.a.k.l.j.k.b.d, false);
        List<c> emotes = new ArrayList<>();
        for (Emote emote : emoteList) {
            emotes.add(getEmote(emote));
        }

        return new tv.twitch.a.k.l.j.k.d(header, emotes);
    }
}
