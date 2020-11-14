package tv.twitch.android.shared.player.overlay;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.bridges.interfaces.IBottomPlayerControlOverlayViewDelegate;
import tv.twitch.android.mod.utils.Logger;


public class BottomPlayerControlOverlayViewDelegate implements IBottomPlayerControlOverlayViewDelegate { // TODO: __IMPLEMENT
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

        setupRefreshButton(view); // TODO: __INJECT_CODE
        setupLockButton(view); // TODO: __INJECT_CODE
    }

    public void updateLockButtonState() { // TODO: __INJECT_METHOD
        if (this.lockButton == null)
            return;

        int lockDrawableId = ResourcesManager.getDrawableId("ic_lock");
        if (lockDrawableId == 0) {
            Logger.error("ic_lock not found");
            return;
        }

        int unlockDrawableId = ResourcesManager.getDrawableId("ic_unlock");
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

        int lockButton = ResourcesManager.getId("lock_button");
        if (lockButton == 0) {
            Logger.error("lockButton == 0");
            return;
        }
        this.lockButton = view.findViewById(lockButton);
        if (this.lockButton == null) {
            Logger.error("lock button is null");
            return;
        }

        Hooks.setupLockButtonClickListener(this.lockButton, this);
        updateLockButtonState();
    }

    public void setLockButtonVisible(boolean z) { // TODO: __INJECT_METHOD
        if (this.lockButton != null) {
            if (!Hooks.isSwipperEnabled() || !Hooks.shouldShowLockButton()) {
                this.lockButton.setVisibility(View.GONE);
                return;
            }

            this.lockButton.setVisibility(z ? View.VISIBLE : View.GONE);
        }
    }

    public void clickRefresh() { // TODO: __INJECT_METHOD
        if (mBottomPlayerControlListener != null) {
            mBottomPlayerControlListener.onRefreshClicked();
        }
    }


    private void setupRefreshButton(View view) { // TODO: __INJECT_METHOD
        if (view == null) {
            Logger.error("view is null");
            return;
        }
        int refreshButtonId = ResourcesManager.getId("refresh_button");
        if (refreshButtonId == 0) {
            Logger.error("refreshButtonId == 0");
            return;
        }

        this.refreshButton = view.findViewById(refreshButtonId);
        if (this.refreshButton == null) {
            Logger.error("refresh button is null");
            return;
        }

        Hooks.setupRefreshButtonClickListener(this.refreshButton, this);

        if (!Hooks.shouldShowRefreshButton()) {
            this.refreshButton.setVisibility(View.GONE);
        }
    }
}
