package tv.twitch.android.shared.emotes.emotepicker;


import android.widget.ImageView;

import tv.twitch.android.core.mvp.viewdelegate.BaseViewDelegate;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.shared.emotes.emotepicker.models.EmotePickerSection;


public class EmotePickerViewDelegate extends BaseViewDelegate {
    private final ImageView bttvEmoteButton = findView(ResourcesManager.INSTANCE.getId("bttv_emote_button")); // TODO: __INJECT_FIELD


    public void render(EmotePickerPresenter.State state) {
        /* ... */
        if (state instanceof EmotePickerPresenter.State.Loaded) {
            EmotePickerPresenter.State.Loaded loaded = (EmotePickerPresenter.State.Loaded) state;
            /* ... */

            this.bttvEmoteButton.setSelected(loaded.getSelectedEmotePickerSection() == EmotePickerSection.BTTV); // TODO: __INJECT_CODE

            /* ... */
        }
    }
}
