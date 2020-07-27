package tv.twitch.android.shared.emotes.emotepicker;

import android.util.Log;

import java.util.List;

import tv.twitch.android.shared.emotes.emotepicker.adapter.EmotePickerAdapterBinder;
import tv.twitch.android.shared.emotes.emotepicker.adapter.EmotePickerAdapterSection;
import tv.twitch.android.shared.emotes.emotepicker.models.EmotePickerSection;
import tv.twitch.android.core.adapters.RecyclerAdapterSection;
import tv.twitch.android.shared.emotes.models.EmoteMessageInput;
import tv.twitch.android.shared.emotes.models.EmotePickerEmoteModel;


public class EmotePickerPresenter {
    public EmotePickerAdapterBinder adapterBinder;


    public final State.Loaded createLoadedState(State.Loaded loaded, UpdateEvent updateEvent) {
        int i = findBttvIndex(this.adapterBinder.getAdapter().getSections());

        return null;
    }

    private int findBttvIndex(List<RecyclerAdapterSection> items) {
        if (items == null) {
            Log.e("nop", "items is null");

            return 0;
        }
        if (items.isEmpty()) {
            Log.e("nop", "empty items");

            return 0;
        }

        int index = 0;
        for (RecyclerAdapterSection item : items) {
            if (item instanceof EmotePickerAdapterSection) {
                EmotePickerSection type = ((EmotePickerAdapterSection) item).getEmotePickerSection();
                if (type == EmotePickerSection.BTTV)
                    return index;

                index += item.sizeWithHeader();
            }
        }

        return 0;
    }


    public static abstract class State {
        public static final class Loaded extends State {
            public final EmotePickerSection getSelectedEmotePickerSection() {
                return null;
            }
        }
    }

    public static abstract class UpdateEvent {}

    public static abstract class ClickedEmote {

        public static final class Unlocked extends ClickedEmote {
            public Unlocked(EmotePickerEmoteModel emotePickerEmoteModel, EmoteMessageInput emoteMessageInput, EmotePickerTrackingMetadata emotePickerTrackingMetadata, List<Unlocked> list) {}
        }
    }
}