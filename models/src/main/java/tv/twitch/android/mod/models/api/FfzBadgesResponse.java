package tv.twitch.android.mod.models.api;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;


public class FfzBadgesResponse {
    @SerializedName("badges")
    private List<FfzBadge> badges;
    @SerializedName("users")
    private HashMap<Integer, List<Integer>> users;

    public HashMap<Integer, List<Integer>> getUsers() {
        return users;
    }

    public List<FfzBadge> getBadges() {
        return badges;
    }
}
