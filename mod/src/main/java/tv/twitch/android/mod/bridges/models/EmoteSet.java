package tv.twitch.android.mod.bridges.models;


import tv.twitch.android.mod.bridges.ResourcesManager;


public enum EmoteSet {
    GLOBAL("-110", "mod_emote_picker_bttv_global"),
    FFZ( "-109", "mod_emote_picker_ffz_channel"),
    BTTV( "-108", "mod_emote_picker_bttv_channel");

    public final String mTitleRes;
    public final String mSetId;

    public static EmoteSet findById(String id) {
        for (EmoteSet set : values()) {
            if (set.getId().equals(id))
                return set;
        }

        return null;
    }

    EmoteSet(String id, String titleRes) {
        this.mSetId = id;
        this.mTitleRes = titleRes;
    }

    public String getId() {
        return mSetId;
    }

    public String getTitleRes() {
        return mTitleRes;
    }

    public String getTitle() {
        return ResourcesManager.INSTANCE.getString(getTitleRes());
    }
}
