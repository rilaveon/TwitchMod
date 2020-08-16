package tv.twitch.android.mod.bridges;


import android.content.Context;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import tv.twitch.android.app.consumer.TwitchApplication;
import tv.twitch.android.mod.BuildConfig;
import tv.twitch.android.mod.badges.BadgeManager;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.mod.utils.Logger;


public class LoaderLS extends TwitchApplication {
    private static final String APK_BUILD_INFO_TEMPLATE = "BUILD";

    public static String APK_BUILD_INFO = "TEST BUILD";

    private static LoaderLS sInstance = null;


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
    }

    private void fetchBttv() {
        if (PreferenceManager.INSTANCE.isBttvOn())
            EmoteManager.INSTANCE.fetchGlobalEmotes();

        if (PreferenceManager.INSTANCE.isThirdPartyBadgesOn())
            BadgeManager.INSTANCE.fetchBadges();
    }

    private void setBuildInfo() {
        try {
            InputStream inputStream = this.getApplicationContext().getAssets().open("build.properties");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            int buildNum = -1;
            while((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("build_num=")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        buildNum = Integer.parseInt(parts[1]);
                        break;
                    }
                }
            }

            if (buildNum != -1) {
                APK_BUILD_INFO = APK_BUILD_INFO_TEMPLATE + " " + buildNum;
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
        Logger.debug("[" + BuildConfig.LIBRARY_PACKAGE_NAME + ":" + BuildConfig.VERSION_NAME + "] Init LoaderLS...");
    }
}
