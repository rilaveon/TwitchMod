package tv.twitch.android.mod.bridges.interfaces;


import android.graphics.drawable.Drawable;


public interface IUrlDrawable {
    Drawable getDrawable();

    boolean isBadge();

    void setIsTwitchEmote(boolean z);

    boolean isTwitchEmote();
}
