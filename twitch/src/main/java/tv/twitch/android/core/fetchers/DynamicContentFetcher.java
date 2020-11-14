package tv.twitch.android.core.fetchers;


public abstract class DynamicContentFetcher {
    private boolean hasMoreContent;

    /* ... */

    public boolean hasMoreContent() { // TODO: __REMOVE_FINAL
        return this.hasMoreContent;
    }

    /* ... */
}
