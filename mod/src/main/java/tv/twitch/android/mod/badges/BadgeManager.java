package tv.twitch.android.mod.badges;


import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import tv.twitch.android.mod.badges.fetchers.FfzBadgesFetecher;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.BadgeSet;
import tv.twitch.android.mod.utils.Logger;


public class BadgeManager implements FfzBadgesFetecher.Callback {
    public static final BadgeManager INSTANCE = new BadgeManager();

    private final FfzBadgesFetecher mFfzFetcher;
    private BadgeSet mFfzBadgeSet;


    private BadgeManager() {
        mFfzFetcher = new FfzBadgesFetecher(this);
    }

    public void fetchBadges() {
        Logger.debug("Fetching badges...");
        mFfzFetcher.fetch();
    }

    @NonNull
    public List<Badge> getBadgesForUser(Integer userId) {
        if (userId == 0) {
            Logger.debug("userId is 0");
            return Collections.emptyList();
        }

        if (mFfzBadgeSet == null)
            return Collections.emptyList();

        return mFfzBadgeSet.getBadges(userId);
    }

    @Override
    public void onResult(FfzBadgeSet set) {
        mFfzBadgeSet = set;
    }
}
