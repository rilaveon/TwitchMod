package tv.twitch.android.shared.emotes.emotepicker;


import android.view.View;
import android.widget.ImageView;

import tv.twitch.android.core.mvp.viewdelegate.RxViewDelegate;
import tv.twitch.android.core.mvp.viewdelegate.ViewDelegateEvent;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.shared.emotes.emotepicker.models.EmotePickerSection;


public class EmotePickerViewDelegate extends RxViewDelegate<EmotePickerPresenter.State, EmotePickerViewDelegate.Event> {
    private final ImageView bttvEmotesButton = findView(ResourcesManager.INSTANCE.getId("bttv_emote_button")); // TODO: __INJECT_FIELD

    /* ... */

    public static abstract class Event implements ViewDelegateEvent {
        /* ... */

        public static final class EmoteSectionSelectedEvent extends Event  {
            /* ... */

            public EmoteSectionSelectedEvent(EmotePickerSection emotePickerSection) {/* ... */}

            /* ... */
        }

        /* ... */
    }


    EmotePickerViewDelegate() {
        this.bttvEmotesButton.setOnClickListener(new View.OnClickListener() { // TODO: __INJECT_CODE
            public final void onClick(View view) {
                pushEvent(new Event.EmoteSectionSelectedEvent(EmotePickerSection.BTTV));
            }
        });

    }

    public void render(EmotePickerPresenter.State state) {
        /* ... */

        if (state instanceof EmotePickerPresenter.State.Loaded) {
            EmotePickerPresenter.State.Loaded loaded = (EmotePickerPresenter.State.Loaded) state;
            /* ... */

            setBttvSelected((EmotePickerPresenter.State.Loaded) state); // TODO: __INJECT_CODE

            /* ... */
        }

        /* ... */
    }

    private final void setBttvSelected(EmotePickerPresenter.State.Loaded state) { // TODO: __INJECT_METHOD
        this.bttvEmotesButton.setSelected(state.getSelectedEmotePickerSection() == EmotePickerSection.BTTV);
    }

    /* ... */
}
