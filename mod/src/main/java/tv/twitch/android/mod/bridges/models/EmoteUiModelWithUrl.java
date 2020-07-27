package tv.twitch.android.mod.bridges.models;


import tv.twitch.android.shared.emotes.emotepicker.EmotePickerPresenter;
import tv.twitch.android.shared.emotes.emotepicker.models.EmoteUiModel;


public final class EmoteUiModelWithUrl extends EmoteUiModel {
    final String mUrl;


    public EmoteUiModelWithUrl(String id, boolean isLockIconVisible, boolean isLongClickIconVisible, EmotePickerPresenter.ClickedEmote clickedEmote, String url) {
        super(id, isLockIconVisible, isLongClickIconVisible, clickedEmote);
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
}
