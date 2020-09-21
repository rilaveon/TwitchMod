package tv.twitch.android.shared.player.overlay;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.utils.Logger;


public class BottomPlayerControlOverlayViewDelegate {
    private BottomPlayerControlListener mBottomPlayerControlListener = new EmptyBottomPlayerControlListener();

    /* ... */

    private ImageView refreshButton; // TODO: __INJECT_FIELD

    /* ... */

    public interface BottomPlayerControlListener {
        /* ... */

        void onRefreshClicked(); // TODO: __INJECT_METHOD
    }

    public BottomPlayerControlOverlayViewDelegate(Context context, View view) {
        /* ... */
        new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        };

        setupRefreshButton(view); // TODO: __INJECT_CODE
    }

    private void setupRefreshButton(View view) { // TODO: __INJECT_METHOD
        if (view == null) {
            Logger.error("view is null");
            return;
        }
        int refreshButtonId = ResourcesManager.INSTANCE.getId("refresh_button");
        if (refreshButtonId == 0) {
            Logger.error("refreshButtonId == 0");
            return;
        }

        this.refreshButton = view.findViewById(refreshButtonId);
        if (this.refreshButton == null) {
            Logger.error("refresh button is null");
            return;
        }

        this.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomPlayerControlListener != null) {
                    Logger.debug("clicked!");
                    mBottomPlayerControlListener.onRefreshClicked();
                }
            }
        });

        if (!Hooks.shouldShowRefreshButton()) {
            this.refreshButton.setVisibility(View.GONE);
        }
    }
}
