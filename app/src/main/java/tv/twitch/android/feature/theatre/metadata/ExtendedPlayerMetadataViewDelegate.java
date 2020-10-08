package tv.twitch.android.feature.theatre.metadata;


import android.content.Context;
import android.view.ViewGroup;

import tv.twitch.android.mod.bridges.Hooks;


public final class ExtendedPlayerMetadataViewDelegate {
    /* ... */

    public static final class Companion {
        /* ... */

        public final ExtendedPlayerMetadataViewDelegate create(Context context, ViewGroup viewGroup, ViewGroup viewGroup2, ViewGroup viewGroup3) {
            // View inflate = LayoutInflater.from(context).inflate(R$layout.player_metadata_view_extended, viewGroup, false);

            int id = Hooks.hookPlayerMetadataViewId(0); // TODO: __HOOK_ARG

            return null;
        }

        /* ... */
    }

    private final void showSubscribeButton() {
        // this.followOrSubButtonContainer.addView(this.subButtonViewDelegate.getContentView()); // TODO: __REMOVE_CODE
    }

}
