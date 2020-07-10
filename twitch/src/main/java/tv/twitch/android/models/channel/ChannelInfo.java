package tv.twitch.android.models.channel;


import android.os.Parcelable;


public interface ChannelInfo extends Parcelable {
    String getDisplayName();

    String getGame();

    int getId();

    String getName();

    boolean isPartner();
}
