package tv.twitch.android.mod.bridges.interfaces;


public interface IChatMessageFactory {
    CharSequence getSpannedEmote(String url, String emoteText);

    CharSequence getSpannedBadge(String url, String badgeName);
}
