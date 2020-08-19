package tv.twitch.android.mod.utils;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.widget.Toast;


import com.google.android.gms.common.GooglePlayServicesUtilLight;

import tv.twitch.android.api.parsers.PlayableModelParser;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.settings.SettingsActivity;


public class Helper {
    private static int ConnectionResult_SUCCESS = 0;

    public static final Helper INSTANCE = new Helper();

    private int mCurrentChannel = 0;

    private Helper() {}


    public static void openUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            Logger.error("Empty url");
            return;
        }

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://" + url;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        LoaderLS.getInstance().startActivity(intent);
    }

    public static void showRestartDialog(Context context, String message) {
        new AlertDialog.Builder(context).setMessage(message).setPositiveButton(ResourcesManager.INSTANCE.getString("dialog_yes"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PendingIntent pendingIntent = PendingIntent.getActivity(LoaderLS.getInstance(), 0, new Intent(LoaderLS.getInstance(), SettingsActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
                ((AlarmManager) LoaderLS.getInstance().getSystemService(Context.ALARM_SERVICE)).setExact(AlarmManager.RTC, 1500, pendingIntent);
                Process.killProcess(Process.myPid());

            }
        }).setNegativeButton(ResourcesManager.INSTANCE.getString("dialog_no"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    public static void downloadMP4File(final Context context, final String url, final String filename) {
        PermissionsUtil.checkWritePermission(context, new PermissionsUtil.Result() {
            @Override
            public void onPermissionGranted() {
                Uri uri = Uri.parse(url);
                DownloadManager downloadManager = (DownloadManager) LoaderLS.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                request.setMimeType("video/mp4");
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"/twitch/" + filename + ".mp4");

                downloadManager.enqueue(request);
                Helper.showToast("Downloading " + filename + "...");
            }

            @Override
            public void onPermissionDenied() {
                Helper.showToast("No access");
            }

            @Override
            public void onError() {
                Helper.showToast("error");
            }
        });
    }

    public static int getChannelId(PlayableModelParser playableModelParser, Playable playable) {
        if (playableModelParser == null) {
            Logger.error("playableModelParser is null");
            return 0;
        }
        if (playable == null) {
            Logger.error("playable is null");
            return 0;
        }

        if (playable instanceof ClipModel) {
            return ((ClipModel) playable).getBroadcasterId();
        }

        return playableModelParser.getChannelId(playable);
    }

    public static void showToast(String message) {
        Context context = LoaderLS.getInstance().getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean isOnStackTrace(String clazz) {
        if (TextUtils.isEmpty(clazz)) {
            Logger.error("Empty clazz");
            return false;
        }

        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element == null)
                continue;

            if (TextUtils.isEmpty(element.getClassName()))
                continue;

            if (!element.getClassName().equalsIgnoreCase(clazz))
                continue;

            return true;
        }

        return false;
    }

    public int getCurrentChannel() {
        return this.mCurrentChannel;
    }

    public void setCurrentChannel(int channelID) {
        if (channelID < 0)
            channelID = 0;

        this.mCurrentChannel = channelID;
    }

    public static boolean isGooglePlayServicesAvailable(Context context) {
        try {
            int resCode = GooglePlayServicesUtilLight.isGooglePlayServicesAvailableOrg(context, GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            return resCode == ConnectionResult_SUCCESS;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return false;
    }
}
