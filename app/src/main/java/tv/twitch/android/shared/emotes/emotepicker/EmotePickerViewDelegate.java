package tv.twitch.android.shared.emotes.emotepicker;


import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import tv.twitch.android.core.mvp.viewdelegate.RxViewDelegate;
import tv.twitch.android.core.mvp.viewdelegate.ViewDelegateEvent;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.shared.emotes.emotepicker.models.EmotePickerSection;
import tv.twitch.android.shared.player.parsers.extm3u.raw.Media;


public class EmotePickerViewDelegate extends RxViewDelegate<EmotePickerPresenter.State, EmotePickerViewDelegate.Event> {
    private final ImageView bttvEmotesButton = getBttvButtonView(); // TODO: __INJECT_FIELD

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
        setupVan(); // TODO: __INJECT_CODE
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

    private void setupVan() { // TODO: __INJECT_METHOD
        if (this.bttvEmotesButton == null) {
            Logger.error("bttvEmotesButton is null");
            return;
        }

        Hooks.playVan(bttvEmotesButton);
    }

    private ImageView getBttvButtonView() { // TODO: __INJECT_METHOD
        return findView(ResourcesManager.INSTANCE.getId("bttv_emote_button"));
    }

    /* ... */
}
