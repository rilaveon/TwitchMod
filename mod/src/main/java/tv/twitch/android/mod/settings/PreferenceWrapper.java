package tv.twitch.android.mod.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.utils.Logger;


public final class PreferenceWrapper implements SharedPreferences.OnSharedPreferenceChangeListener  {
    private final List<PreferenceListener> mListeners = new ArrayList<>();

    private final SharedPreferences mDefaultSharedPreferences;

    public interface PreferenceListener {
        void onPreferenceChanged(SharedPreferences sharedPreferences, String key);
    }

    public PreferenceWrapper(Context context) {
        mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mDefaultSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void updateBoolean(String key, boolean val) {
        if (TextUtils.isEmpty(key)) {
            Logger.error("Empty key");
            return;
        }

        mDefaultSharedPreferences.edit().putBoolean(key, val).apply();
    }

    public void updateInt(String key, int val) {
        if (TextUtils.isEmpty(key)) {
            Logger.error("Empty key");
            return;
        }

        mDefaultSharedPreferences.edit().putInt(key, val).apply();
    }

    public void updateString(String key, String val) {
        if (TextUtils.isEmpty(key)) {
            Logger.error("Empty key");
            return;
        }

        mDefaultSharedPreferences.edit().putString(key, val).apply();
    }

    public boolean getBoolean(String key, boolean def) {
        return mDefaultSharedPreferences.getBoolean(key, def);
    }

    public String getString(String key, String def) {
        return mDefaultSharedPreferences.getString(key, def);
    }

    public int getInt(String key, int def) {
        return mDefaultSharedPreferences.getInt(key, def);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (sharedPreferences == mDefaultSharedPreferences) {
            for (PreferenceListener listener : mListeners) {
                listener.onPreferenceChanged(sharedPreferences, key);
            }
        } else {
            Logger.debug("check sharedPreferences");
        }
    }

    public void registerPreferenceListener(@NonNull PreferenceListener listener) {
        mListeners.add(listener);
    }
}
