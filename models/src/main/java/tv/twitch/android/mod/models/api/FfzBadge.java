package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;


import java.util.HashMap;


public class FfzBadge {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("replaces")
    private String replaces;
    @SerializedName("urls")
    private HashMap<Integer, String> urls;

    public int getId() {
        return id;
    }

    public HashMap<Integer, String> getUrls() {
        return urls;
    }

    public String getName() {
        return name;
    }

    public String getReplaces() {
        return replaces;
    }
}
