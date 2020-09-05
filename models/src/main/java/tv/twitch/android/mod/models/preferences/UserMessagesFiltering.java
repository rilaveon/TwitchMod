package tv.twitch.android.mod.models.preferences;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({UserMessagesFiltering.DISABLED, UserMessagesFiltering.SUBS, UserMessagesFiltering.MODS, UserMessagesFiltering.BROADCASTER})
public @interface UserMessagesFiltering {
    int DISABLED = 0;
    int SUBS = 1;
    int MODS = 2;
    int BROADCASTER = 3;
}
