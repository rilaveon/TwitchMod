package tv.twitch.android.core.mvp.viewdelegate;


public abstract class RxViewDelegate<S extends ViewDelegateState, E extends ViewDelegateEvent> extends BaseViewDelegate {
    /* ... */

    public void pushEvent(E event) {/* ... */}

    /* ... */
}
