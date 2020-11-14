package tv.twitch.android.shared.emotes.emotepicker;


import android.view.View;
import android.widget.ImageView;

import tv.twitch.android.core.adapters.TwitchSectionAdapter;
import tv.twitch.android.core.mvp.viewdelegate.RxViewDelegate;
import tv.twitch.android.core.mvp.viewdelegate.ViewDelegateEvent;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.bridges.interfaces.IEmotePickerViewDelegate;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.shared.emotes.emotepicker.models.EmotePickerSection;
import tv.twitch.android.shared.ui.elements.list.ContentListViewDelegate;


public class EmotePickerViewDelegate extends RxViewDelegate<EmotePickerPresenter.State, EmotePickerViewDelegate.Event> implements IEmotePickerViewDelegate { // TODO: __IMPLEMENT
    private final ImageView bttvEmotesButton = getBttvButtonView(); // TODO: __INJECT_FIELD
    private final ContentListViewDelegate listViewDelegate = null;

    @Override
    public void scrollToBttvSection() { // TODO: __INJECT_METHOD
        int pos = Helper.calcBttvPos((TwitchSectionAdapter) listViewDelegate.getAdapter());
        listViewDelegate.xScrollToPosition(pos);
    }

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

        /* ... */

        setupBttvEmoteButton(); // TODO: __INJECT_CODE
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

    private void setupBttvEmoteButton() { // TODO: __INJECT_METHOD
        if (this.bttvEmotesButton == null) {
            Logger.error("bttvEmotesButton is null");
            return;
        }

        Hooks.setupBttvEmotesButtonClickListener(bttvEmotesButton, this);
        this.bttvEmotesButton.setVisibility(Hooks.isBttvEmotesEnabled() ? View.VISIBLE : View.GONE);
    }

    private ImageView getBttvButtonView() { // TODO: __INJECT_METHOD
        return findView(ResourcesManager.getId("bttv_emote_button"));
    }

    /* ... */
}
