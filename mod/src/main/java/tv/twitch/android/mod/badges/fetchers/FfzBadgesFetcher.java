package tv.twitch.android.mod.badges.fetchers;


import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import tv.twitch.android.mod.badges.FfzBadgeSet;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.models.api.FfzBadge;
import tv.twitch.android.mod.models.api.FfzBadgesResponse;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getFfzApi;


public class FfzBadgesFetcher extends ApiCallback<FfzBadgesResponse> {
    private final Callback mCallback;

    private static final HashMap<Integer, String> LOCAL_BADGES = new HashMap<>();

    static {
        LOCAL_BADGES.put(1, "file:///android_asset/mod/badges/ffz/1.png");
        LOCAL_BADGES.put(2, "file:///android_asset/mod/badges/ffz/2.png");
        LOCAL_BADGES.put(3, "file:///android_asset/mod/badges/ffz/3.png");
    }

    public interface Callback {
        void onFfzBadgesParsed(FfzBadgeSet set);
    }

    public FfzBadgesFetcher(Callback callback) {
        this.mCallback = callback;
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

            String url;
            if (LOCAL_BADGES.containsKey(badge.getId())) {
                url = LOCAL_BADGES.get(badge.getId());
            } else {
                url = getUrl(badge.getUrls());
            }

            if (url == null)
                continue;

            List<Integer> usersIds = users.get(badge.getId());
            if (usersIds == null || usersIds.isEmpty()) {
                continue;
            }

            for (Integer id : usersIds) {
                if (id == 0)
                    continue;

                set.addBadge(new Badge(badge.getName(), url, badge.getReplaces()), id);
            }
        }

        mCallback.onFfzBadgesParsed(set);
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

        if (TextUtils.isEmpty(url))
            return null;

        if (url != null && url.startsWith("//"))
            url = "https:" + url;

        return url;
    }
}
