package tv.twitch.android.mod.models.preferences;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({FloatingChatRefreshDelay.DEFAULT, FloatingChatRefreshDelay.MUL1, FloatingChatRefreshDelay.MUL2, FloatingChatRefreshDelay.MUL3, FloatingChatRefreshDelay.MUL4})
public @interface FloatingChatRefreshDelay {
    int DEFAULT = 800;
    int MUL1 = 700;
    int MUL2 = 600;
    int MUL3 = 500;
    int MUL4 = 400;
}