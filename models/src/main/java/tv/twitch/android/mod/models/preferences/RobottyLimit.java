package tv.twitch.android.mod.models.preferences;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({RobottyLimit.LIMIT1, RobottyLimit.LIMIT2, RobottyLimit.LIMIT3})
public @interface RobottyLimit {
    int LIMIT1 = 20;
    int LIMIT2 = 50;
    int LIMIT3 = 100;
}
