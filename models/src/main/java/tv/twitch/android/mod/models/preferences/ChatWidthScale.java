package tv.twitch.android.mod.models.preferences;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({ChatWidthScale.DEFAULT, ChatWidthScale.SCALE10, ChatWidthScale.SCALE15, ChatWidthScale.SCALE20,
         ChatWidthScale.SCALE25, ChatWidthScale.SCALE30, ChatWidthScale.SCALE35, ChatWidthScale.SCALE40})
public @interface ChatWidthScale {
    int DEFAULT = -1;
    int SCALE10 = 10;
    int SCALE15 = 15;
    int SCALE20 = 20;
    int SCALE25 = 25;
    int SCALE30 = 30;
    int SCALE35 = 35;
    int SCALE40 = 40;
}
