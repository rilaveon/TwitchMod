package tv.twitch.android.mod.badges.fetchers;


import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import tv.twitch.android.mod.badges.FfzBadgeSet;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.models.api.FfzBadge;
import tv.twitch.android.mod.models.api.FfzBadgesResponse;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getFfzApi;


public class FfzBadgesFetecher extends ApiCallback<FfzBadgesResponse> {
    private static final Map<Integer, String> sLocalBadges = new HashMap<>();

    static {
        sLocalBadges.put(1, "file:///android_asset/badges/ffz1.png");
        sLocalBadges.put(2, "file:///android_asset/badges/ffz2.png");
        sLocalBadges.put(3, "file:///android_asset/badges/ffz3.png");
    }

    private final Callback mCallback;


    public interface Callback {
        void onResult(FfzBadgeSet set);
    }

    public FfzBadgesFetecher(Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onRequestSuccess(FfzBadgesResponse response) {
        if (response == null) {
            Logger.error("response is null");
            return;
        }

        List<FfzBadge> badges = response.getBadges();
        if (badges == null) {
            Logger.error("badges is null");
            return;
        }

        HashMap<Integer, List<Integer>> users = response.getUsers();
        if (users == null) {
            Logger.error("users is null");
            return;
        }

        FfzBadgeSet set = new FfzBadgeSet();
        for (FfzBadge badge : badges) {
            if (badge == null)
                continue;

            if (badge.getName() == null) {
                continue;
            }

            if (badge.getUrls() == null) {
                continue;
            }

            String uri = sLocalBadges.get(badge.getId());
            if (uri == null) {
                uri = getUrl(badge.getUrls());
                if (uri == null) {
                    continue;
                }
            }

            List<Integer> usersIds = users.get(badge.getId());
            if (usersIds == null || usersIds.isEmpty()) {
                continue;
            }

            for (Integer id : usersIds) {
                if (id == 0)
                    continue;

                set.addBadge(new Badge(badge.getName(), uri, badge.getReplaces()), id);
            }
        }

        mCallback.onResult(set);
        Logger.debug("done!");
    }

    @Override
    public void onRequestFail(Call<FfzBadgesResponse> call, FailReason failReason) {
        Logger.debug("reason="+failReason.toString());
    }

    @Override
    public void fetch() {
        Logger.info("Fetching ffz badges...");
        getFfzApi().getBadges().enqueue(this);
    }

    private static String getUrl(HashMap<Integer, String> urls) {
        String url;
        if (urls.containsKey(4)) {
            url = urls.get(4);
        } else if (urls.containsKey(2)) {
            url = urls.get(2);
        } else {
            url = urls.get(1);
        }

        if (TextUtils.isEmpty(url)) {
            return null;
        }

        if (url.startsWith("//"))
            url = "https:" + url;

        return url;
    }
}
