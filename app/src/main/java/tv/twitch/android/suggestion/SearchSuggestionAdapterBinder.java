package tv.twitch.android.suggestion;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.search.suggestion.SuggestableContent;


public class SearchSuggestionAdapterBinder {
    /* ... */

    public final void bind(SuggestableContent suggestableContent) {
        /* ... */

        if (suggestableContent instanceof SuggestableContent.SearchSuggestionPastQueries && !Hooks.isJumpDisRecentSearch()) { // TODO :__JUMP_HOOK
            /* ... */
        }

        /* ... */
    }

    /* ... */
}