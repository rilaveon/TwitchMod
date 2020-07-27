package tv.twitch.android.mod.utils;

import android.view.View;


import tv.twitch.android.mod.bridges.interfaces.ISharedPanelWidget;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.models.clips.ClipQuality;


public class ClipDownloader implements View.OnClickListener {
    private final ISharedPanelWidget mWidget;

    public ClipDownloader(ISharedPanelWidget sharedPanelWidget) {
        mWidget = sharedPanelWidget;
    }

    @Override
    public void onClick(View v) {
        final ClipModel clipModel = mWidget.getClipModel();
        if (clipModel == null) {
            Logger.error("clipModel is null");
            return;
        }

        String filename = clipModel.getTitle() + " - " + clipModel.getBroadcasterName() + " - " + clipModel.getClipSlugId();
        Helper.downloadMP4File(mWidget.getLayout().getContext(), clipModel.getBestUrlForQuality(ClipQuality.QualitySource), filename);

        mWidget.hidePanel();
    }
}
