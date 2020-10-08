package tv.twitch.android.app.core;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import tv.twitch.android.core.user.TwitchAccountManager;
import tv.twitch.android.mod.utils.Helper;


public class MainActivity extends AppCompatActivity {
    TwitchAccountManager mAccountManager;

    /* ... */

    private FragmentManager.OnBackStackChangedListener mBackStackListener = new FragmentManager.OnBackStackChangedListener() { // TODO: __REPLACE_FIELD
        @Override
        public void onBackStackChanged() {
            Helper.maybeShowBanner(MainActivity.this, mAccountManager);
        }
    };

    /* ... */
}
