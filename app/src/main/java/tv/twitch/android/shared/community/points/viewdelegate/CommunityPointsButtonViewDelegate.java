package tv.twitch.android.shared.community.points.viewdelegate;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;


public class CommunityPointsButtonViewDelegate {
    private final ViewGroup buttonLayout = null;
    private final ImageView overlayIcon = null;

    /* ... */

    public CommunityPointsButtonViewDelegate(Context context, View view) {/* ... */}

    private final void showClaimAvailable(CommunityPointsModel communityPointsModel) {
        /* ... */

        setupClicker(); // TODO: __INJECT_CODE
    }

    private void setupClicker() { // TODO: __INJECT_METHOD
        if (buttonLayout == null) {
            Logger.debug("buttonLayout is null");
            return;
        }

        buttonLayout.performClick();
    }

    private void handleClaimError() {
        // Toast.makeText(getContext(), R$string.claim_error_message, 1).show(); // TODO: __REMOVE
        /* ... */
    }

    /* ... */
}