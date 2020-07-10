package tv.twitch.a.k.g.m1;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.channel.ChannelInfo;

/**
 * Source: ChatConnectionController
 */
public class a {
    private final void o2(ChannelInfo channelInfo) {
        Hooks.requestEmotes(channelInfo); // TODO: __INJECT_CODE
    }
}
