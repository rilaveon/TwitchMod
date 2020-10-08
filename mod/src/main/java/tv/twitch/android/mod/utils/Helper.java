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

import java.util.Random;

import tv.twitch.android.api.parsers.PlayableModelParser;
import tv.twitch.android.core.user.TwitchAccountManager;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.settings.SettingsActivity;


public class Helper {
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
                Intent settingsIntent = new Intent(LoaderLS.getInstance(), SettingsActivity.class);
                settingsIntent.putExtra("OPEN_MOD_SETTINGS", true);
                PendingIntent pendingIntent = PendingIntent.getActivity(LoaderLS.getInstance(), 0, settingsIntent, PendingIntent.FLAG_CANCEL_CURRENT);
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
        PermissionsUtil.checkWritePermission(context, new PermissionsUtil.ResultCallback() {
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
                Helper.showToast(String.format(ResourcesManager.INSTANCE.getString("mod_downloading"), filename));
            }

            @Override
            public void onPermissionDenied() {
                Helper.showToast("onPermissionDenied");
            }

            @Override
            public void onError() {
                Helper.showToast("onError");
            }
        });
    }

    public static int getRandomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
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
        Toast.makeText(LoaderLS.getInstance().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public static boolean isOnStackTrace(String clazz) {
        if (TextUtils.isEmpty(clazz)) {
            Logger.error("Empty clazz");
            return false;
        }

        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement == null)
                continue;

            if (TextUtils.isEmpty(stackTraceElement.getClassName()))
                continue;

            if (!stackTraceElement.getClassName().equalsIgnoreCase(clazz))
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

    public static void showPartnerBanner(Context context) {}

    public static void maybeShowBanner(final Context context, final TwitchAccountManager accountManager) {
        if (!PreferenceManager.INSTANCE.isShowBanner())
            return;

        StringBuilder text = new StringBuilder(ResourcesManager.INSTANCE.getString("mod_banner_text_default"));

        AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Mod Info").setMessage(text).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferenceManager.INSTANCE.disableBanner();
                dialog.cancel();
                if (accountManager.isPartner())
                    showPartnerBanner(context);
            }
        }).create();
        dialog.show();
    }
}
