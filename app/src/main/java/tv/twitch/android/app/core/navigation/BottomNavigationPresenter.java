package tv.twitch.android.app.core.navigation;


import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.bridges.Hooks;


public class BottomNavigationPresenter {
    private final List<BottomNavigationItem> mBottomNavigationItems;

    private enum BottomNavigationItem {
        DISCOVER,
        ESPORTS

        /* ... */
    }

    public BottomNavigationPresenter(/* ... */) {
        ArrayList<BottomNavigationItem> arrayList = new ArrayList<>();

        /* ... */

        this.mBottomNavigationItems = filterList(arrayList);  // TODO: __INJECT_CODE
    }

    private static List<BottomNavigationItem> filterList(ArrayList<BottomNavigationItem> items) { // TODO: __INJECT_METHOD
        if (items == null || items.isEmpty()) {
            return null;
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
