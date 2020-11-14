package tv.twitch.android.mod.models.preferences;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({PlayerSeek.FIVE, PlayerSeek.TEN, PlayerSeek.FIFTEEN, PlayerSeek.THIRTY , PlayerSeek.SIXTY})
public @interface PlayerSeek {
    int FIVE = 5;
    int TEN = 10;
    int FIFTEEN = 15;
    int THIRTY = 30;
    int SIXTY = 60;
}
