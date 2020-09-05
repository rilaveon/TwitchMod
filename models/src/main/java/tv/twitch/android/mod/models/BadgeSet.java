package tv.twitch.android.mod.models;


import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;

public interface BadgeSet {
    @NonNull
    Collection<Badge> getBadges(Integer id);

    void addBadge(Badge badge, Integer id);

    boolean isEmpty();
}
