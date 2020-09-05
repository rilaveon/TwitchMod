package tv.twitch.android.mod.models.preferences;


import androidx.annotation.IntDef;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({FloatingChatSize.DEFAULT, FloatingChatSize.FOUR, FloatingChatSize.FIVE, FloatingChatSize.SIX, FloatingChatSize.SEVEN})
public @interface FloatingChatSize {
    int DEFAULT = 3;
    int FOUR = 4;
    int FIVE = 5;
    int SIX = 6;
    int SEVEN = 7;
}