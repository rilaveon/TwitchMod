package tv.twitch.android.shared.emotes.emotepicker.adapter;


import tv.twitch.android.core.adapters.RecyclerAdapterSection;
import tv.twitch.android.shared.emotes.emotepicker.models.EmotePickerSection;


public class EmotePickerAdapterSection extends RecyclerAdapterSection {
    public final EmotePickerSection getEmotePickerSection() {
        return null;
    }

    public final boolean isNamedEmoteHeader() {
        return false;
    }
}
