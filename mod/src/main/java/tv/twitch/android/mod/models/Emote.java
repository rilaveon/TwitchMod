package tv.twitch.android.mod.models;


import androidx.annotation.NonNull;

import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.chat.ChatEmoticon;


public interface Emote {
    @NonNull
    String getCode();

    String getUrl(EmoteSize size);

    boolean isGif();

    @NonNull
    ChatEmoticon getChatEmoticon();
}
