package tv.twitch.a.k.g.p0;

import java.util.List;

import tv.twitch.android.core.adapters.e0;
import tv.twitch.android.core.adapters.u;
import tv.twitch.android.mod.utils.GifHelper;

/**
 * Source: ChannelChatAdapter
 */
public class a extends e0 {
    public void g() {
        GifHelper.recycleAdapterItems(W()); // TODO: __INJECT_CODE
    }

    public void T(List<? extends u> list) {
        int size = 0;
        if (size > 0) {
            GifHelper.recycleAdapterItems(W(), size); // TODO: __INJECT_CODE
            for (int i2 = 0; i2 < size; i2++) {
                W().remove(0);
            }
            // d(0, size);
        }
    }
}
