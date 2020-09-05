package tv.twitch.android.mod.models.preferences;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({PlayerImpl.AUTO, PlayerImpl.CORE, PlayerImpl.EXO})
public @interface PlayerImpl {
    int AUTO = 0;
    int CORE = 1;
    int EXO = 2;
}
