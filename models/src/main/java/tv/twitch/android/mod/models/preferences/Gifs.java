package tv.twitch.android.mod.models.preferences;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({Gifs.DISABLED, Gifs.STATIC, Gifs.ANIMATED})
public @interface Gifs {
    int DISABLED = -1;
    int STATIC = 0;
    int ANIMATED = 1;
}
