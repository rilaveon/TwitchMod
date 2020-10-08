package tv.twitch.android.feature.followed.model;


import java.util.Collections;
import java.util.List;

import tv.twitch.android.mod.bridges.Hooks;


public class FollowedFirstPageContent {
    /* ... */

    public final List getGames() {
        if (Hooks.isFollowedGamesFetcherJump()) // TODO: __INJECT_CODE
            return Collections.emptyList();

        /* ... */

        return null;
    }

    /* ... */
}
