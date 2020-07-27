package tv.twitch.android.mod.models;


import androidx.annotation.NonNull;

import java.util.List;

public interface BadgeSet {
    @NonNull
    List<Badge> getBadges(Integer id);

    void addBadge(Badge badge, Integer id);

    boolean isEmpty();
}
