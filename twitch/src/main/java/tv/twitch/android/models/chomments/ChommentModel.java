package tv.twitch.android.models.chomments;


public class ChommentModel {
    private final ChommentCommenterModel commenter = null;
    private final ChommentMessageModel message = null;

    /* ... */

    public final ChommentMessageModel getMessage() {
        return this.message;
    }

    public ChommentCommenterModel getCommenter() {
        return commenter;
    }

    /* ... */
}
