package tv.twitch.android.mod.badges;


import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;


import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.BadgeSet;


public class FfzBadgeSet implements BadgeSet {
    private final Map<Integer, HashSet<Badge>> mMap = new HashMap<>();


    @NonNull
    @Override
    public Collection<Badge> getBadges(Integer userId) {
        HashSet<Badge> badges = mMap.get(userId);
        if (badges == null)
            return Collections.emptyList();

        return badges;
    }

    @Override
    public void addBadge(Badge badge, Integer userId) {
        Objects.requireNonNull(badge);

        if (userId <= 0)
            throw new IllegalArgumentException("userId <= 0");

        if (!mMap.containsKey(userId)) {
            synchronized (this) {
                if (!mMap.containsKey(userId)) {
                    mMap.put(userId, new HashSet<Badge>());
                }
            }
        }

        HashSet<Badge> set = mMap.get(userId);
        if (set != null) {
            set.add(badge);
        }
    }

    @Override
    public boolean isEmpty() {
        return mMap.isEmpty();
    }
}
