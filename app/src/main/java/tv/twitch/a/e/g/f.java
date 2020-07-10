package tv.twitch.a.e.g;

import tv.twitch.android.mod.bridges.Hooks;

/**
 * Source: FollowedGamesFetcher
 */

public class f extends tv.twitch.a.b.i.e {
    public final boolean z() { // TODO: __INJECT_METHOD
        return Hooks.hookFollowerFetcher(super.z());
    }
}
