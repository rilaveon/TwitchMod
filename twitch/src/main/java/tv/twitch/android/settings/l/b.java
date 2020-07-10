package tv.twitch.android.settings.l;


import androidx.fragment.app.FragmentActivity;

import java.util.List;

import tv.twitch.android.shared.ui.menus.j;

/**
 * Source: BaseSettingsPresenter
 */
public abstract class b {


    /**
     * Destinations
     */
    protected abstract d V1();

    /**
     * Title
     */
    public abstract String Y1();


    /**
     * Controller
     */
    protected abstract j W1();

    public final FragmentActivity S1() {
        return null;
    }

    public abstract void d2();

    /**
     * Menu items
     */
    public final List<tv.twitch.android.shared.ui.menus.l.b> X1() {
        return null;
    }
}
