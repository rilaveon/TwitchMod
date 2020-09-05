package tv.twitch.android.mod.bridges;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import tv.twitch.android.shared.emotes.emotepicker.EmotePickerPresenter;
import tv.twitch.android.shared.emotes.emotepicker.models.EmoteHeaderUiModel;
import tv.twitch.android.shared.emotes.emotepicker.models.EmotePickerSection;
import tv.twitch.android.shared.emotes.emotepicker.models.EmoteUiSet;
import tv.twitch.android.shared.emotes.emotepicker.models.EmoteUiModel;
import tv.twitch.android.shared.emotes.models.EmoteMessageInput;
import tv.twitch.android.mod.bridges.models.ChatEmoticonUrl;
import tv.twitch.android.mod.bridges.models.EmoteUiModelWithUrl;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.preferences.EmoteSize;
import tv.twitch.android.shared.emotes.models.EmotePickerEmoteModel;
import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonSet;


public class ChatFactory {
    private static final int EMOTE_ID_MASK = 0xFFFF0000;

    private static int sLastEmoteId = Integer.MAX_VALUE;

    @NonNull
    private static synchronized String generateEmoteId() {
        if (sLastEmoteId == Integer.MAX_VALUE)
            sLastEmoteId = sLastEmoteId & EMOTE_ID_MASK;

        return String.valueOf(sLastEmoteId++);
    }

    @NonNull
    public static ChatEmoticon getEmoticon(Emote emote) {
        ChatEmoticon chatEmoticon = new ChatEmoticonUrl(emote.getUrl(EmoteSize.LARGE));
        chatEmoticon.match = emote.getCode();
        chatEmoticon.isRegex = false;
        chatEmoticon.emoticonId = generateEmoteId();

        return chatEmoticon;
    }

    @NonNull
    public static ChatEmoticonSet getSet(String setId, Collection<Emote> emotes) {
        ChatEmoticonSet set = new ChatEmoticonSet();
        set.emoticonSetId = setId;
        set.emoticons = convert(emotes);

        return set;
    }

    @NonNull
    private static ChatEmoticon[] convert(Collection<Emote> emoteList) {
        if (emoteList == null || emoteList.size() == 0)
            return new ChatEmoticon[0];

        ChatEmoticon[] chatEmoticons = new ChatEmoticon[emoteList.size()];

        int i = 0;
        for (Emote emote : emoteList) {
            chatEmoticons[i++] = ChatFactory.getEmoticon(emote);
        }

        return chatEmoticons;
    }

    @NonNull
    private static EmoteUiModel getEmote(Emote emote) {
        String fakeEmoteId = generateEmoteId();
        EmoteMessageInput emoteMessageInput = new EmoteMessageInput(emote.getCode(), fakeEmoteId, false);
        EmotePickerEmoteModel emotePicker = new EmotePickerEmoteModel.Generic(fakeEmoteId, emote.getCode());
        EmotePickerPresenter.ClickedEmote clickedEmote = new EmotePickerPresenter.ClickedEmote.Unlocked(emotePicker, emoteMessageInput, null, Collections.<EmotePickerPresenter.ClickedEmote.Unlocked>emptyList());
        return new EmoteUiModelWithUrl(fakeEmoteId, false, false, clickedEmote, emote.getUrl(EmoteSize.LARGE));
    }

    @NonNull
    public static EmoteUiSet getEmoteUiSet(Collection<Emote> emoteList, Integer emoteSetId) {
        EmoteHeaderUiModel header = new EmoteHeaderUiModel.EmoteHeaderStringResUiModel(emoteSetId, true, EmotePickerSection.BTTV, false);
        List<EmoteUiModel> emotes = new ArrayList<>();
        for (Emote emote : emoteList) {
            emotes.add(getEmote(emote));
        }

        return new EmoteUiSet(header, emotes);
    }
}
