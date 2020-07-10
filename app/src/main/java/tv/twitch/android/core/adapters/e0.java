package tv.twitch.android.core.adapters;


import java.util.List;

import tv.twitch.android.mod.utils.GifHelper;

/**
 * Source: TwitchAdapter
 */
public class e0 {
    private List<u> e;


    protected final List<u> W() {
        return this.e;
    }

    public void K(Object b0Var) { // TODO: TEST
        if (b0Var instanceof Object) {
            GifHelper.recycleObject(b0Var, false); // TODO: __INJECT_CODE
        }
    }

    public final void U() {
        GifHelper.recycleAdapterItems(this.e); // TODO: __INJECT_CODE
    }

    public void T(List<? extends u> list)  {}
}
