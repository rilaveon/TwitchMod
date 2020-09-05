package tv.twitch.android.settings.base;


import androidx.fragment.app.Fragment;

public abstract class BaseSettingsFragment /* ... */  extends Fragment {
    /* ... */

    protected abstract BaseSettingsPresenter createPresenter();

    /* ... */
}
