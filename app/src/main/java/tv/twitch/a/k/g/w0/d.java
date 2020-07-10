package tv.twitch.a.k.g.w0;


import android.text.Spanned;
import android.widget.TextView;

import tv.twitch.android.mod.bridges.interfaces.IChatMessageItem;

/**
 * Source: ChommentRecyclerItem
 */
public class d implements IChatMessageItem { // TODO: __IMPLEMENT
    private Spanned c;

    @Override
    public Spanned getSpanned() { // TODO: __INJECT_METHOD
        return c;
    }

    public static final class a implements IChatMessageItem { // TODO: __IMPLEMENT
        @Override
        public Spanned getSpanned() { // TODO: __INJECT_METHOD
            return (Spanned) R().getText();
        }

        public final TextView R() {
            return null;
        }
    }
}
