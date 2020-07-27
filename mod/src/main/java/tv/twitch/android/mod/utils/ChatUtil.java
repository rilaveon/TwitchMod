package tv.twitch.android.mod.utils;


import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.LruCache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tv.twitch.android.mod.bridges.interfaces.IChatMessageFactory;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.settings.EmoteSize;


public class ChatUtil {
    private static final String TIMESTAMP_DATE_FORMAT = "HH:mm ";

    private static final float MIN_DARK_CHECK = .3f;
    private static final float MIN_DARK_CHECK_FIXED = .4f;
    private static final float MAX_WHITE_CHECK = .9f;
    private static final float MAX_WHITE_CHECK_FIXED = .8f;


    private static final LruCache<Integer, Integer> mDarkThemeCache = new LruCache<Integer, Integer>(500) {
        @Override
        protected Integer create(Integer color) {
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            if (hsv[2] >= MIN_DARK_CHECK) {
                return color;
            }

            hsv[2] = MIN_DARK_CHECK_FIXED;
            return Color.HSVToColor(hsv);
        }
    };

    private static final LruCache<Integer, Integer> mWhiteThemeCache = new LruCache<Integer, Integer>(500) {
        @Override
        protected Integer create(Integer color) {
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            if (hsv[2] <= MAX_WHITE_CHECK) {
                return color;
            }

            hsv[2] = MAX_WHITE_CHECK_FIXED;
            return Color.HSVToColor(hsv);
        }
    };

    public static Spanned addTimestamp(Spanned message, Date date) {
        final String dateFormat = new SimpleDateFormat(TIMESTAMP_DATE_FORMAT, Locale.UK).format(date);
        SpannableString dateString = SpannableString.valueOf(dateFormat);
        dateString.setSpan(new RelativeSizeSpan(0.75f), 0, dateString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder formatted = new SpannableStringBuilder(dateString);
        formatted.append(new SpannableStringBuilder(message));
        return SpannableString.valueOf(formatted);
    }

    public static Integer fixUsernameColor(int color, boolean isDarkTheme) {
        return isDarkTheme ? mDarkThemeCache.get(color) : mWhiteThemeCache.get(color);
    }

    public static SpannedString injectEmotesSpan(IChatMessageFactory factory, SpannedString messageSpan, int channelID, boolean isGifsDisabled, EmoteSize emoteSize) {
        if (TextUtils.isEmpty(messageSpan)) {
            Logger.warning("Empty messageSpan");
            return messageSpan;
        }

        if (factory == null) {
            Logger.error("factory is null");
            return messageSpan;
        }

        boolean newWord = false;
        int startPos = 0;
        int messageLength = messageSpan.length();

        SpannableStringBuilder ssb = null;

        for (int currentPos = 0; currentPos <= messageLength; currentPos++) {
            if (currentPos != messageLength && messageSpan.charAt(currentPos) != ' ') {
                if (!newWord) {
                    newWord = true;
                    startPos = currentPos;
                }
            } else {
                if (newWord) {
                    newWord = false;

                    String code = String.valueOf(messageSpan.subSequence(startPos, currentPos));
                    Emote emote = EmoteManager.INSTANCE.getEmote(code, channelID);
                    if (emote != null) {
                        if (emote.isGif() && isGifsDisabled)
                            continue;

                        SpannableString emoteSpan = (SpannableString) factory.getSpannedEmote(emote.getUrl(emoteSize), emote.getCode());
                        if (emoteSpan != null) {
                            if (ssb == null)
                                ssb = new SpannableStringBuilder(messageSpan);

                            ssb.replace(startPos, currentPos, emoteSpan);
                        }
                    }
                }
            }
        }

        if (ssb != null)
            return SpannedString.valueOf(ssb);

        return messageSpan;
    }
}
