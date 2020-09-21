package tv.twitch.android.models.clips;


import java.util.Collections;
import java.util.List;

import tv.twitch.android.models.Playable;


public class ClipModel implements Playable {
    /* ... */

    public final List<ClipQualityOption> getQualityOptions() {
        return Collections.emptyList();
    }

    public int getBroadcasterId() {
        return 0;
    }

    public String getBroadcasterName() {
        return null;
    }

    public final String getClipSlugId() {
        return null;
    }

    public final String getTitle() {
        return null;
    }

    /* ... */
}
