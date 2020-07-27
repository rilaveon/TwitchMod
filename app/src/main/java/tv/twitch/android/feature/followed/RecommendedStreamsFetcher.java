package tv.twitch.android.feature.followed;


import tv.twitch.android.core.fetchers.NoParamDynamicContentFetcher;
import tv.twitch.android.mod.bridges.Hooks;


public class RecommendedStreamsFetcher extends NoParamDynamicContentFetcher {
    public final boolean hasMoreContent() { // TODO: __INJECT_METHOD
        return Hooks.hookRecommendedStreamsFetcher(super.hasMoreContent());
    }
}
