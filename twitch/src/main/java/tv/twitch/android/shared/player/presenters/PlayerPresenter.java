package tv.twitch.android.shared.player.presenters;


import io.reactivex.Observable;
import tv.twitch.android.shared.player.models.ManifestResponse;


public interface PlayerPresenter {
    /* ... */

    void stop();

    Observable<ManifestResponse> getManifestObservable();

    /* ... */
}
