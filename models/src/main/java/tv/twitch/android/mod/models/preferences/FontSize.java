package tv.twitch.android.mod.models.preferences;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({FontSize.EIGHT, FontSize.NINE, FontSize.TEN, FontSize.ELEVEN, FontSize.TWELVE, FontSize.DEFAULT, FontSize.FOURTEEN, FontSize.SIXTEEN, FontSize.EIGHTEEN, FontSize.TWENTY})
public @interface FontSize {
    int EIGHT = 8;
    int NINE = 9;
    int TEN = 10;
    int ELEVEN = 11;
    int TWELVE = 12;
    int DEFAULT = 13;
    int FOURTEEN = 14;
    int SIXTEEN = 16;
    int EIGHTEEN = 18;
    int TWENTY = 20;
}
