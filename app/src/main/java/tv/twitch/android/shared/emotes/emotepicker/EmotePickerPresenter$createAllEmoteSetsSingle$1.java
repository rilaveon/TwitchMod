package tv.twitch.android.shared.emotes.emotepicker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.shared.emotes.emotepicker.models.EmoteUiSet;
import tv.twitch.android.shared.emotes.models.EmoteSet;


public class EmotePickerPresenter$createAllEmoteSetsSingle$1 {
    final Integer $channelId = 0;

    /* ... */

    public final Object apply(List<? extends EmoteSet> list) {
        /* ... */

        Collection<EmoteUiSet> collection = Hooks.hookEmotePickerSet(new ArrayList<EmoteUiSet>(), $channelId); // TODO: __INJECT_CODE

        /* ... */

        return null;
    }

    /* ... */
}