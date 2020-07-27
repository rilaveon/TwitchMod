package tv.twitch.android.app.core.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tv.twitch.android.mod.bridges.Hooks;


public class BottomNavigationPresenter {
    private final List<BottomNavigationItem> mBottomNavigationItems;

    private enum BottomNavigationItem {
        FOLLOWING,
        DISCOVER,
        BROWSE,
        ESPORTS,
        BROADCAST;
    }

    public BottomNavigationPresenter() {
        ArrayList<BottomNavigationItem> arrayList = new ArrayList<>(Arrays.asList(BottomNavigationItem.FOLLOWING, BottomNavigationItem.DISCOVER, BottomNavigationItem.BROWSE));

        /* ... */

        this.mBottomNavigationItems = filterList(arrayList);  // TODO: __INJECT_CODE
    }

    private static List<BottomNavigationItem> filterList(ArrayList<BottomNavigationItem> items) { // TODO: __INJECT_METHOD
        if (items == null) {
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
