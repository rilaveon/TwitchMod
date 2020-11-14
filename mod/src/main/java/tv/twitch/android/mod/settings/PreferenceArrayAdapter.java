package tv.twitch.android.mod.settings;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.utils.Logger;

public class PreferenceArrayAdapter extends ArrayAdapter<PreferenceArrayAdapter.AdapterItem> {
    private final String mPrefKey;

    public static final class AdapterItem {
        private final String mName;
        private final Object mVal;

        public AdapterItem(String name, Object val) {
            mName = name;
            mVal = val;
        }

        public String getName() {
            return mName;
        }

        public Object getVal() {
            return mVal;
        }

        @NonNull
        @Override
        public String toString() {
            return mName;
        }
    }

    public void fill(Object[] vals, String[] names) {
        if (vals == null) {
            Logger.error("vals is null");
            return;
        }

        if (names == null) {
            Logger.error("names is null");
            return;
        }

        if (vals.length != names.length) {
            Logger.error("diff length: vals=" + vals.length + ", names=" + names.length);
            return;
        }

        for (int i = 0; i < vals.length; i++) {
            add(new AdapterItem(names[i], vals[i]));
        }
    }

    private static boolean compare(Object o, Object o1) {
        if (o == null)
            return false;

        if (o1 == null)
            return false;

        if (o instanceof Integer) {
            if (o1 instanceof Integer) {
                Integer val1 = (Integer) o;
                Integer val2 = (Integer) o1;

                return val1.equals(val2);
            }
        }

        if (o instanceof String) {
            if (o1 instanceof String) {
                String val1 = (String) o;
                String val2 = (String) o1;
                return val1.equals(val2);
            }
        }

        return o == o1;
    }


    public int findItemPos(Object val) {
        for (int i = 0; i < this.getCount(); i++) {
            AdapterItem item = this.getItem(i);
            if (item == null)
                continue;

            if (compare(item.getVal(), val))
                return i;
        }

        return 0;
    }


    public PreferenceArrayAdapter(Context context, String prefKey) {
        super(context, ResourcesManager.getLayoutId("twitch_spinner_dropdown_item"));
        mPrefKey = prefKey;
    }

    public String getPrefKey() {
        return mPrefKey;
    }
}
