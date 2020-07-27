package tv.twitch.android.shared.share.panel;


import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import tv.twitch.android.app.core.ViewExtensionsKt;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.bridges.interfaces.ISharedPanelWidget;
import tv.twitch.android.mod.utils.ClipDownloader;
import tv.twitch.android.models.ShareTextData;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.shared.ui.elements.bottomsheet.InteractiveRowView;


public class SharePanelWidget extends FrameLayout implements ISharedPanelWidget {
    private ClipModel mClipModel;
    private SharePanelWidgetListener mSharePanelWidgetListener;

    private InteractiveRowView mDownloadButton; // TODO: __INJECT_FIELD


    public interface SharePanelWidgetListener {
        void onHostClicked();

        void onShareClicked();

        void onUrlCopiedToClipboard();

        void onWhisperClicked(ShareTextData shareTextData);
    }


    public SharePanelWidget(@NonNull Context context) {
        super(context);
    }

    private void initDownloadModel() { // TODO: __INJECT_METHOD
        this.mDownloadButton = findViewById(ResourcesManager.INSTANCE.getId("download_model"));
        ViewExtensionsKt.visibilityForBoolean(this.mDownloadButton, mClipModel != null);
        mDownloadButton.setOnClickListener(new ClipDownloader(this));
    }

    private void init() {
        initDownloadModel();
    }

    private void updateViewState() {
        if (this.mDownloadButton != null) { // TODO: __INJECT_CODE
            ViewExtensionsKt.visibilityForBoolean(this.mDownloadButton, mClipModel != null);
        }
    }

    @Override
    public ClipModel getClipModel() {
        return mClipModel;
    }

    @Override
    public void hidePanel() {
        SharePanelWidgetListener sharePanelWidgetListener = this.mSharePanelWidgetListener;
        if (sharePanelWidgetListener != null) {
            sharePanelWidgetListener.onShareClicked();
        }

    }

    @Override
    public FrameLayout getLayout() {
        return this;
    }
}