package tv.twitch.android.shared.emotes.emotepicker;

import java.util.List;

import tv.twitch.android.core.adapters.RecyclerAdapterSection;
import tv.twitch.android.core.adapters.TwitchSectionAdapter;
import tv.twitch.android.core.mvp.viewdelegate.ViewDelegateState;
import tv.twitch.android.shared.emotes.emotepicker.adapter.EmotePickerAdapterBinder;
import tv.twitch.android.shared.emotes.emotepicker.adapter.EmotePickerAdapterSection;
import tv.twitch.android.shared.emotes.emotepicker.models.EmotePickerSection;
import tv.twitch.android.shared.emotes.models.EmoteMessageInput;
import tv.twitch.android.shared.emotes.models.EmotePickerEmoteModel;


public class EmotePickerPresenter {
    public EmotePickerAdapterBinder adapterBinder;

    public static abstract class State implements ViewDelegateState {
        public static final class Loaded extends State {
            /* ... */

            public final EmotePickerSection getSelectedEmotePickerSection() {
                return null;
            }

            /* ... */
        }
    }

    public static abstract class UpdateEvent {
        public static final class EmoteSectionSelected extends UpdateEvent  {/* ... */}
    }

    public final State.Loaded createLoadedState(State.Loaded loaded, UpdateEvent updateEvent) {
        /* ... */
        if (updateEvent instanceof UpdateEvent.EmoteSectionSelected) {
            /* ... */
            int i;
            int i2 = 4;
            if (i2 == 4) {
                i = calcBttvPosition();// TODO: __INJECT_CODE
                i += 1;
            }

            /* ... */
        }
        /* ... */

        return null;
    }

    private int calcBttvPosition() { // TODO: __INJECT_CODE
        if (adapterBinder == null)
            return 0;

        TwitchSectionAdapter twitchSectionAdapter = adapterBinder.getAdapter();
        if (twitchSectionAdapter == null)
            return 0;

        List<RecyclerAdapterSection> sections = twitchSectionAdapter.getSections();
        if (sections == null)
            return 0;

        int index = 0;
        for (RecyclerAdapterSection item : sections) {
            if (item instanceof EmotePickerAdapterSection) {
                EmotePickerSection type = ((EmotePickerAdapterSection) item).getEmotePickerSection();
                if (type == EmotePickerSection.BTTV)
                    return index;

                index += item.sizeWithHeader();
            }
        }

        return 0;
    }


    public static abstract class ClickedEmote {
        public static final class Unlocked extends ClickedEmote {
            public Unlocked(EmotePickerEmoteModel emotePickerEmoteModel, EmoteMessageInput emoteMessageInput, EmotePickerTrackingMetadata emotePickerTrackingMetadata, List<Unlocked> list) {}
        }
    }
}