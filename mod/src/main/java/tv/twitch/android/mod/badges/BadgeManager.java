package tv.twitch.android.mod.badges;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import tv.twitch.android.mod.badges.fetchers.FfzBadgesFetcher;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.BadgeSet;
import tv.twitch.android.mod.utils.Logger;


public class BadgeManager implements FfzBadgesFetcher.Callback {
    public static final BadgeManager INSTANCE = new BadgeManager();

    private final static HashMap<Integer, Collection<Badge>> mCustomBadges = new HashMap<>();


    private final FfzBadgesFetcher mFfzFetcher;
    private BadgeSet mFfzBadgeSet;

    static {
        Collection<Badge> badges = new ArrayList<>();
        badges.add(new Badge("mod-dev", "file:///android_asset/mod/badges/custom/fire.png", null));
        mCustomBadges.put(157861306, badges);
    }


    private BadgeManager() {
        mFfzFetcher = new FfzBadgesFetcher(this);
    }

    public void fetchBadges() {
        Logger.debug("Fetching badges...");
        mFfzFetcher.fetch();
    }

    public Collection<Badge> getCustomBadges(Integer userId) {
        if (userId <= 0) {
            Logger.debug("userId  <= 0");
            return Collections.emptyList();
        }

        Collection<Badge> badges = mCustomBadges.get(userId);
        if (badges == null)
            return Collections.emptyList();

        return badges;
    }

    @NonNull
    public Collection<Badge> findBadges(Integer userId) {
        if (userId <= 0) {
            Logger.debug("userId  <= 0");
            return Collections.emptyList();
        }

        if (mFfzBadgeSet == null)
            return Collections.emptyList();

        return mFfzBadgeSet.getBadges(userId);
    }

    @Override
    public void onFfzBadgesParsed(FfzBadgeSet set) {
        if (set == null) {
            Logger.error("set is null");
            return;
        }

        mFfzBadgeSet = set;
    }
}
