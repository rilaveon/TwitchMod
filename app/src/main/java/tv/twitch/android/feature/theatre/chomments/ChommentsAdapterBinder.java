package tv.twitch.android.feature.theatre.chomments;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.chomments.ChommentModel;
import tv.twitch.android.shared.chat.chomments.ChommentRecyclerItem;


public class ChommentsAdapterBinder {
    /* ... */

    private final ChommentRecyclerItem createListItemForChomment(ChommentModel chommentModel) {
        chommentModel = Hooks.maybeFilterThisChomment(chommentModel); // TODO: __INJECT_CODE
        if (chommentModel == null) // TODO: __INJECT_CODE
            return null;

        /* ... */

        return null;
    }

    private final void addItemToAdapter(ChommentRecyclerItem chommentRecyclerItem) {
        if (chommentRecyclerItem == null) // TODO: __INJECT_CODE
            return;

        /* ... */
    }

    /* ... */
}
