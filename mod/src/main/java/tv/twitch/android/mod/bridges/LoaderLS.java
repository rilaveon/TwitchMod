package tv.twitch.android.mod.bridges;


import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import tv.twitch.android.app.consumer.TwitchApplication;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;


public class LoaderLS extends TwitchApplication {
    public static final String VERSION = "TwitchMod v2.3";
    private static final String BUILD_TEMPLATE = "TEST BUILD";

    public static String BUILD = BUILD_TEMPLATE;

    private EmoteManager sEmoteManager;
    private PreferenceManager sPreferenceManager;
    private Helper sHelper;
    private IDPub sIDPub;

    private static volatile LoaderLS sInstance = null;

    public static LoaderLS getInstance() {
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

    private void post() {}

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
                BUILD = BUILD_TEMPLATE + " (" + buildNum + ")";
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
        setBuild();
        Logger.debug("Init LoaderLS. " + VERSION + " " + BUILD);
        sPreferenceManager = new PreferenceManager(getApplicationContext());
        sEmoteManager = new EmoteManager();
        sHelper = new Helper();
        sIDPub = new IDPub();
    }

    public static EmoteManager getEmoteMangerInstance() {
        return getInstance().getEmoteManager();
    }

    public static PreferenceManager getPrefManagerInstance() {
        return getInstance().getPrefManager();
    }

    public static Helper getHelperInstance() {
        return getInstance().getHelper();
    }

    public static IDPub getIDPubInstance() {
        return getInstance().getIDPub();
    }

    private EmoteManager getEmoteManager() {
        if (sEmoteManager == null) {
            synchronized (LoaderLS.class) {
                if (sEmoteManager == null) {
                    sEmoteManager = new EmoteManager();
                }
            }
        }
        return sEmoteManager;
    }

    private PreferenceManager getPrefManager() {
        if (sPreferenceManager == null) {
            synchronized (LoaderLS.class) {
                if (sPreferenceManager == null) {
                    sPreferenceManager = new PreferenceManager(getApplicationContext());
                }
            }
        }
        return sPreferenceManager;
    }

    private Helper getHelper() {
        if (sHelper == null) {
            synchronized (LoaderLS.class) {
                if (sHelper == null) {
                    sHelper = new Helper();
                }
            }
        }
        return sHelper;
    }

    private IDPub getIDPub() {
        if (sIDPub == null) {
            synchronized (LoaderLS.class) {
                if (sIDPub == null) {
                    sIDPub = new IDPub();
                }
            }
        }
        return sIDPub;
    }
}
