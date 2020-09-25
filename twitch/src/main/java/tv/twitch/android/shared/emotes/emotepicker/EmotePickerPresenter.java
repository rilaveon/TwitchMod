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


public final class EmotePickerPresenter {
    private final EmotePickerAdapterBinder adapterBinder = null;


    /* ... */

    public static abstract class State implements ViewDelegateState {
        /* ... */

        public static final class Loaded extends State {
            /* ... */

            public final EmotePickerSection getSelectedEmotePickerSection() {
                return null;
            }

            /* ... */
        }

        /* ... */
    }

    public static abstract class UpdateEvent {
        /* ... */

        public static final class EmoteSectionSelected extends UpdateEvent  {/* ... */}

        /* ... */
    }

    public final State.Loaded createLoadedState(State.Loaded loaded, UpdateEvent updateEvent) {
        /* ... */

        if (updateEvent instanceof UpdateEvent.EmoteSectionSelected) {
            /* ... */

            int i = 0;
            int i2 = 4;

            if (i2 == 4) {
                i = calcBttvPos();
            }
            i++;


                /* ... */
        }

        /* ... */

        return null;
    }

    private int calcBttvPos() {
        if (this.adapterBinder == null)
            return 0;

        TwitchSectionAdapter adapter = this.adapterBinder.getAdapter();
        if (adapter == null)
            return 0;

        List<RecyclerAdapterSection> sections = adapter.getSections();
        if (sections == null || sections.size() == 0)
            return 0;

        int sizeWithHeader = 0;
        for (RecyclerAdapterSection t : sections) {
            EmotePickerAdapterSection section = (EmotePickerAdapterSection) t;

            if (section.getEmotePickerSection() == EmotePickerSection.BTTV)
                return sizeWithHeader;

            sizeWithHeader += section.sizeWithHeader();
        }

        return 0;
    }

    public static abstract class ClickedEmote {
        /* ... */

        public static final class Unlocked extends ClickedEmote {
            /* ... */

            public Unlocked(EmotePickerEmoteModel emotePickerEmoteModel, EmoteMessageInput emoteMessageInput, EmotePickerTrackingMetadata emotePickerTrackingMetadata, List<Unlocked> list) {/* ... */}

            /* ... */
        }

        /* ... */
    }

    /* ... */
}