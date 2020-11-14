package tv.twitch.android.app.core.navigation;


import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.bridges.Hooks;


public class BottomNavigationPresenter {
    private final List<BottomNavigationItem> mBottomNavigationItems;

    private enum BottomNavigationItem {
        /* ... */

        DISCOVER,
        ESPORTS

        /* ... */
    }

    public BottomNavigationPresenter(/* ... */) {
        ArrayList<BottomNavigationItem> arrayList = new ArrayList<>();

        /* ... */

        this.mBottomNavigationItems = filterNavItems(arrayList);  // TODO: __INJECT_CODE
    }

    private static List<BottomNavigationItem> filterNavItems(ArrayList<BottomNavigationItem> items) { // TODO: __INJECT_METHOD
        if (items == null || items.isEmpty()) {
            return items;
        }

        if (Hooks.isHideDiscoverTab()) {
            items.remove(BottomNavigationItem.DISCOVER);
        }
        if (Hooks.isHideEsportsTab()) {
            items.remove(BottomNavigationItem.ESPORTS);
        }

        return items;
    }
}
