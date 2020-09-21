package tv.twitch.android.shared.chat.communitypoints;


import android.view.ViewGroup;


import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;


public class CommunityPointsButtonViewDelegate {
    private final ViewGroup buttonLayout = null;

    /* ... */

    private final void showClaimAvailable(CommunityPointsModel communityPointsModel) {
        /* ... */

        this.buttonLayout.callOnClick(); // TODO: __INJECT_CODE
    }

    private void handleClaimError() {
        // Toast.makeText(getContext(), R$string.claim_error_message, 1).show(); // TODO: __REMOVE
        /* ... */
    }

    /* ... */
}