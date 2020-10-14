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
    private ImageView lockButton; // TODO: __INJECT_FIELD

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
        setupLockButton(view); // TODO: __INJECT_CODE
    }

    private void updateLockButtonState() { // TODO: __INJECT_METHOD
        if (this.lockButton == null)
            return;

        int lockDrawableId = ResourcesManager.INSTANCE.getDrawableId("ic_lock");
        if (lockDrawableId == 0) {
            Logger.error("ic_lock not found");
            return;
        }

        int unlockDrawableId = ResourcesManager.INSTANCE.getDrawableId("ic_unlock");
        if (unlockDrawableId == 0) {
            Logger.error("ic_unlock not found");
            return;
        }

        if (Hooks.shouldLockSwiper()) {
            this.lockButton.setImageResource(unlockDrawableId);
        } else {
            this.lockButton.setImageResource(lockDrawableId);
        }
    }

    private void setupLockButton(View view) { // TODO: __INJECT_METHOD
        if (view == null) {
            Logger.error("view is null");
            return;
        }

        int lockButton = ResourcesManager.INSTANCE.getId("lock_button");
        if (lockButton == 0) {
            Logger.error("lockButton == 0");
            return;
        }
        this.lockButton = view.findViewById(lockButton);
        if (this.lockButton == null) {
            Logger.error("lock button is null");
            return;
        }

        this.lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Hooks.shouldLockSwiper()) {
                    Hooks.setLockSwiper(false);
                    updateLockButtonState();
                } else {
                    Hooks.setLockSwiper(true);
                    updateLockButtonState();
                }
            }
        });
        updateLockButtonState();
    }

    public void setLockButtonVisible(boolean z) { // TODO: __INJECT_METHOD
        if (this.lockButton != null) {
            if (!Hooks.isSwipperEnabled()) {
                this.lockButton.setVisibility(View.GONE);
                return;
            }

            this.lockButton.setVisibility(z ? View.VISIBLE : View.GONE);
        }
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
                    mBottomPlayerControlListener.onRefreshClicked();
                }
            }
        });

        if (!Hooks.shouldShowRefreshButton()) {
            this.refreshButton.setVisibility(View.GONE);
        }
    }
}
