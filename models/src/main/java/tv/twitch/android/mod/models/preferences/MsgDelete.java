package tv.twitch.android.mod.models.preferences;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({MsgDelete.DEFAULT, MsgDelete.MOD, MsgDelete.STRIKETHROUGH})
public @interface MsgDelete {
    int DEFAULT = 0;
    int MOD = 1;
    int STRIKETHROUGH = 2;
}
