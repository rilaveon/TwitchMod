package tv.twitch.android.shared.share.panel;


import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import tv.twitch.android.app.core.ViewExtensionsKt;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.bridges.interfaces.ISharedPanelWidget;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.models.videos.VodModel;
import tv.twitch.android.shared.player.presenters.PlayerPresenter;
import tv.twitch.android.shared.ui.elements.bottomsheet.InteractiveRowView;


public class SharePanelWidget extends FrameLayout implements ISharedPanelWidget { // TODO: __IMPLEMENT
    private ClipModel mClipModel;
    private VodModel mVodModel;
    private PlayerPresenter mPlayerPresenter;

    private SharePanelWidgetListener mSharePanelWidgetListener;

    /* ... */

    private InteractiveRowView mDownloadButton; // TODO: __INJECT_FIELD

    /* ... */

    public interface SharePanelWidgetListener {
        /* ... */

        void onShareClicked();
    }


    public SharePanelWidget(@NonNull Context context) {
        super(context);
    }

    private void setupDownloadButton() { // TODO: __INJECT_METHOD
        int downloadButtonId = ResourcesManager.getId("download_model");
        if (downloadButtonId != 0) {
            this.mDownloadButton = findViewById(downloadButtonId);
            if (this.mDownloadButton != null) {
                ViewExtensionsKt.visibilityForBoolean(this.mDownloadButton, mClipModel != null);
                Hooks.setupClipDownloader(mDownloadButton, this);
            }
        }
    }

    private void init() {
        setupDownloadButton(); // TODO: __INJECT_CODE
    }

    private void updateModButtonsVisibility() { // TODO: __INJECT_METHOD
        if (this.mDownloadButton != null) {
            ViewExtensionsKt.visibilityForBoolean(this.mDownloadButton, mClipModel != null);
        }
    }

    private void updateViewState() {
        updateModButtonsVisibility();  // TODO: __INJECT_CODE
    }

    @Override
    public ClipModel getClipModel() { // TODO: __INJECT_METHOD
        return mClipModel;
    }

    @Override
    public void hidePanel() { // TODO: __INJECT_METHOD
        SharePanelWidgetListener sharePanelWidgetListener = this.mSharePanelWidgetListener;
        if (sharePanelWidgetListener != null) {
            sharePanelWidgetListener.onShareClicked();
        }

    }

    @Override
    public FrameLayout getLayout() { // TODO: __INJECT_METHOD
        return this;
    }
}