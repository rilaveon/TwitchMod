package tv.twitch.android.mod.models.preferences;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@StringDef({MiniPlayerSize.DEFAULT, MiniPlayerSize.SIZE1, MiniPlayerSize.SIZE2, MiniPlayerSize.SIZE3, MiniPlayerSize.SIZE4})
public @interface MiniPlayerSize {
    String DEFAULT = "1.00";
    String SIZE1 = "1.25";
    String SIZE2 = "1.50";
    String SIZE3 = "1.75";
    String SIZE4 = "2.00";
}
