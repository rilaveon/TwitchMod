package tv.twitch.android.mod.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;

import java.util.List;

import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.bridges.interfaces.ISharedPanelWidget;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.models.clips.ClipQualityOption;


public class ClipDownloader implements View.OnClickListener {
    private final ISharedPanelWidget mWidget;
    private final ClipsAdapter mAdapter;

    public ClipDownloader(ISharedPanelWidget sharedPanelWidget) {
        mWidget = sharedPanelWidget;
        mAdapter = new ClipsAdapter(mWidget.getLayout().getContext());
    }

    private void fillAdapter(List<ClipQualityOption> options) {
        if (options == null) {
            Logger.error("options is null");
            return;
        }

        mAdapter.clear();
        for (ClipQualityOption option : options) {
            ClipsAdapter.ClipItem item = new ClipsAdapter.ClipItem(option.getSource(), option.getQuality(), String.valueOf(option.getFrameRate()));
            mAdapter.add(item);
        }
    }

    public static final class ClipsAdapter extends ArrayAdapter<ClipsAdapter.ClipItem> {
        public static final class ClipItem {
            private final String mUrl;
            private final String mQuality;
            private final String mFrameRate;

            public ClipItem(String url, String qual, String rate) {
                mUrl = url;
                mQuality = qual;
                mFrameRate = rate;
            }

            @Override
            public String toString() {
                return mQuality + "p" + mFrameRate;
            }

            public String getUrl() {
                return mUrl;
            }
        }

        public ClipsAdapter(@NonNull Context context) {
            super(context, ResourcesManager.INSTANCE.getLayoutId("twitch_spinner_dropdown_item"));
        }
    }

    @Override
    public void onClick(View v) {
        ClipModel clipModel = mWidget.getClipModel();
        if (clipModel == null) {
            Logger.error("clipModel is null");
            return;
        }

        fillAdapter(mWidget.getClipModel().getQualityOptions());
        AlertDialog.Builder builder = new AlertDialog.Builder(mWidget.getLayout().getContext());
        builder.setTitle("Download");

        builder.setAdapter(mAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipsAdapter.ClipItem option = mAdapter.getItem(which);
                if (option == null) {
                    Logger.debug("option is null. Pos=" + which);
                    return;
                }

                ClipModel clipModel = mWidget.getClipModel();

                String filename = clipModel.getTitle() + " - " + clipModel.getBroadcasterName() + " - " + clipModel.getClipSlugId();
                Helper.downloadMP4File(mWidget.getLayout().getContext(), option.getUrl(), filename);
            }
        });
        builder.create().show();

        mWidget.hidePanel();
    }
}
