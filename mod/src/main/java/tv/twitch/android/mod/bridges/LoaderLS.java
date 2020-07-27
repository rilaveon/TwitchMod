package tv.twitch.android.mod.bridges;


import android.content.Context;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import tv.twitch.android.app.consumer.TwitchApplication;
import tv.twitch.android.mod.badges.BadgeManager;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.mod.utils.Logger;


public class LoaderLS extends TwitchApplication {
    public static final String VERSION = "TwitchMod v2.4";
    private static final String BUILD_TEMPLATE = "BUILD";

    public static String BUILD = BUILD_TEMPLATE;

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
        init();
        super.onCreate();
        post();
    }

    private void post() {
        fetchBttvStuff();
    }

    private void fetchBttvStuff() {
        if (PreferenceManager.INSTANCE.isBttvOn())
            EmoteManager.INSTANCE.fetchGlobalEmotes();

        if (PreferenceManager.INSTANCE.isFfzBadgesOn())
            BadgeManager.INSTANCE.fetchBadges();
    }

    private void setBuild() {
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
                BUILD = BUILD_TEMPLATE + " " + buildNum;
            }

            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void init() {
        sInstance = this;
        PreferenceManager.INSTANCE.initialize(this);
        setBuild();
        Logger.debug("Init LoaderLS. " + VERSION + " " + BUILD);
    }
}
