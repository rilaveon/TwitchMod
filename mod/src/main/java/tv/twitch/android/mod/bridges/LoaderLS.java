package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.app.consumer.TwitchApplication;
import tv.twitch.android.mod.BuildConfig;
import tv.twitch.android.mod.badges.BadgeManager;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.mod.utils.ChatMesssageFilteringUtil;
import tv.twitch.android.mod.utils.Logger;


public class LoaderLS extends TwitchApplication {
    private static final String APK_BUILD_INFO_TEMPLATE = "BUILD ";
    private static final String CUSTOM_BADGES_ASSETS_PATH = "mod/badges/custom";

    private static String sBuildInfo = "TEST BUILD";
    private static int sBuildNumber = -1;

    private static LoaderLS sInstance = null;

    public static String getBuildInfo() {
        StringBuilder builder = new StringBuilder(sBuildInfo);
        if (sBuildNumber != -1) {
            builder.append(" b").append(sBuildNumber);
        }

        return builder.toString();
    }

    public static int getBuildNumber() {
        return sBuildNumber;
    }

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @NonNull
    public static LoaderLS getInstance() {
        if (sInstance == null) {
            throw new ExceptionInInitializerError("LoaderLS instance is null");
        }

        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        initLoader();
        super.onCreate();
        fetchBttv();
        setFilterBlocklist();
        setCustomBadges();
    }

    public String[] getAssetsEntries(String root) {
        if (TextUtils.isEmpty(root))
            root = "";

        try {
            return this.getApplicationContext().getAssets().list(root);
        } catch (IOException ex) {
            Logger.error("root=" + root);
            ex.printStackTrace();
        }

        return null;
    }

    private void setFilterBlocklist() {
        ChatMesssageFilteringUtil.INSTANCE.updateBlocklist(PreferenceManager.INSTANCE.getUserFilterText());
    }

    private void setCustomBadges() {
        try {
            String[] entries = getAssetsEntries(CUSTOM_BADGES_ASSETS_PATH);
            if (entries == null || entries.length == 0)
                return;

            for (String entry : entries) {
                try {
                    int userId = Integer.parseInt(entry);

                    if (userId <= 0) {
                        Logger.warning("Bad ID: " + userId);
                        continue;
                    }

                    String userBadgesPath = CUSTOM_BADGES_ASSETS_PATH + "/" + entry;
                    String[] badgeEntries = getAssetsEntries(userBadgesPath);

                    if (badgeEntries == null || badgeEntries.length == 0)
                        return;

                    List<Badge> badges = new ArrayList<>();
                    for (String filename : badgeEntries) {
                        if (filename.length() < 5 || !filename.contains(".") || !(filename.endsWith(".png") || filename.endsWith(".gif"))) {
                            Logger.debug("filtering: " + filename);
                            continue;
                        }

                        String name = filename.substring(0, filename.length()-4);
                        badges.add(new Badge("custom-" + name, "file:///android_asset/" + userBadgesPath + "/" + filename, null));
                    }

                    if (badges.size() > 0) {
                        BadgeManager.INSTANCE.setUserCustomBadges(userId, badges);
                    }
                } catch (NumberFormatException ex) {
                    Logger.warning("Bad userID: " + entry);
                }
            }

        } catch (Throwable th) {
            th.printStackTrace();
            BadgeManager.INSTANCE.clearCustomBadges();
        }
    }

    private void fetchBttv() {
        if (PreferenceManager.INSTANCE.showBttvEmotesInChat())
            EmoteManager.INSTANCE.fetchGlobalEmotes();

        if (PreferenceManager.INSTANCE.showCustomBadges())
            BadgeManager.INSTANCE.fetchBadges();
    }

    private void setBuildInfo() {
        try {
            InputStream inputStream = this.getApplicationContext().getAssets().open("build.properties");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("build_num=")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        if (TextUtils.isEmpty(parts[1]))
                            continue;

                        try {
                            sBuildNumber = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (line.startsWith("build_info=")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        if (TextUtils.isEmpty(parts[1]))
                            continue;

                        sBuildInfo = parts[1];
                    }
                }
            }

            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void initLoader() {
        sInstance = this;
        PreferenceManager.INSTANCE.initialize(this);
        setBuildInfo();
        Logger.debug("[" + BuildConfig.LIBRARY_PACKAGE_NAME + ":" + BuildConfig.VERSION_NAME + ":" + sBuildNumber + "] Init LoaderLS...");
    }
}
