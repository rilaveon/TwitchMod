package tv.twitch.android.mod.models.preferences;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@StringDef({ExoPlayerSpeed.DEFAULT, ExoPlayerSpeed.SPEED125, ExoPlayerSpeed.SPEED150, ExoPlayerSpeed.SPEED175, ExoPlayerSpeed.SPEED200})
public @interface ExoPlayerSpeed {
    String DEFAULT = "1.00";
    String SPEED125 = "1.25";
    String SPEED150 = "1.50";
    String SPEED175 = "1.75";
    String SPEED200 = "2.00";
}
