package tv.twitch.android.adapters.a;

import android.content.Context;
import android.text.Spanned;
import android.view.MotionEvent;
import android.widget.TextView;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.interfaces.IChatMessageItem;

/**
 * Source: MessageRecyclerItem
 */
public class b implements IChatMessageItem { // TODO: __IMPLEMENT
    private boolean j;
    private Spanned f;

    public String l; // chat msg


    public b(Context context, String messageId, int i2, String str2, String str3, int i3, Spanned message, Object gVar, float f2, int i4, float f3, boolean z, boolean z2, String str4, Object eventDispatcher) {
        message = Hooks.addTimestampToMessage(message, messageId); // TODO: __HOOK_PARAM
    }

    public void k() { // TODO: another methods
        this.j = true; // TODO: __INJECT_CODE
    }

    @Override
    public Spanned getSpanned() { // TODO: __INJECT_METHOD
        return f;
    }

    public static final class a implements IChatMessageItem { // TODO: __IMPLEMENT
        public final TextView S() {
            return null;
        }

        @Override
        public Spanned getSpanned() { // TODO: __INJECT_METHOD
            return (Spanned) S().getText();
        }
    }

}