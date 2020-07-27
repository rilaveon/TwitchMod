package tv.twitch.android.mod.badges;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.BadgeSet;


public class FfzBadgeSet implements BadgeSet {
    private final Map<Badge, HashSet<Integer>> mMap = new HashMap<>();


    @NonNull
    @Override
    public List<Badge> getBadges(Integer userId) {
        if (mMap.isEmpty())
            return Collections.emptyList();

        List<Badge> badges = null;
        for (Map.Entry<Badge, HashSet<Integer>> entry : mMap.entrySet()) {
            if (entry.getValue() == null || entry.getKey() == null)
                continue;

            if (entry.getValue().contains(userId)) {
                if (badges == null)
                    badges = new ArrayList<>();

                badges.add(entry.getKey());
            }
        }

        if (badges != null) {
            return badges;
        }

        return Collections.emptyList();
    }

    @Override
    public void addBadge(Badge badge, Integer userId) {
        Objects.requireNonNull(badge);

        if (userId == 0)
            throw new IllegalArgumentException("userId == 0");

        if (!mMap.containsKey(badge)) {
            synchronized (this) {
                if (!mMap.containsKey(badge)) {
                    mMap.put(badge, new HashSet<Integer>());
                }
            }
        }

        mMap.get(badge).add(userId);
    }

    @Override
    public boolean isEmpty() {
        return mMap.isEmpty();
    }
}
