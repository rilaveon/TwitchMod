package tv.twitch.android.mod.models.preferences;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({EmoteSize.SMALL, EmoteSize.MEDIUM, EmoteSize.LARGE})
public @interface EmoteSize {
    int SMALL = 0;
    int MEDIUM = 1;
    int LARGE = 2;
}
