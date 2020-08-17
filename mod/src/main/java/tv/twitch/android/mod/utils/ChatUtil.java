package tv.twitch.android.mod.utils;


import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.LruCache;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tv.twitch.android.core.user.TwitchAccountManager;
import tv.twitch.android.mod.bridges.interfaces.IChatMessageFactory;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.android.models.chat.MessageToken;
import tv.twitch.android.shared.chat.ChatMessageInterface;


public class ChatUtil {
    private static final String TIMESTAMP_DATE_FORMAT = "HH:mm ";
    private static final float TIMESTAMP_SPAN_PROPORTIONAL = 0.75f;
    
    private static final int MAX_THEME_CACHE_SIZE = 500;
    private static final float MIN_DARK_CHECK = .3f;
    private static final float MIN_DARK_CHECK_FIXED = .4f;
    private static final float MAX_WHITE_CHECK = .9f;
    private static final float MAX_WHITE_CHECK_FIXED = .8f;


    private static final LruCache<Integer, Integer> mDarkThemeCache = new LruCache<Integer, Integer>(MAX_THEME_CACHE_SIZE) {
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

    private static final LruCache<Integer, Integer> mWhiteThemeCache = new LruCache<Integer, Integer>(MAX_THEME_CACHE_SIZE) {
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

    public static SpannedString tryAddBadges(SpannedString messageBadgeSpan, IChatMessageFactory factory, Collection<Badge> badges) {
        if (badges == null || badges.isEmpty())
            return messageBadgeSpan;

        SpannableStringBuilder ssb = messageBadgeSpan == null ? new SpannableStringBuilder() : new SpannableStringBuilder(messageBadgeSpan);

        for (Badge badge : badges) {
            if (badge == null)
                continue;

            CharSequence newBadgeSpan = factory.getSpannedBadge(badge.getUrl(), badge.getName());
            if (TextUtils.isEmpty(newBadgeSpan))
                continue;

            if (!TextUtils.isEmpty(badge.getReplaces())) {
                final String replaces = badge.getReplaces() + " ";
                int pos = TextUtils.indexOf(ssb, replaces);
                if (pos != -1) {
                    ssb.replace(pos, replaces.length(), newBadgeSpan);
                }
            } else {
                ssb.append(newBadgeSpan);
            }
        }

        return new SpannedString(ssb);
    }

    public static SpannedString tryAddRedMention(SpannedString msg, TwitchAccountManager accountManager, ChatMessageInterface chatMessageInterface) {
        if (accountManager == null) {
            Logger.error("accountManager is null");
            return msg;
        }

        if (chatMessageInterface == null) {
            Logger.error("chatMessageInterface is null");
            return msg;
        }

        if (TextUtils.isEmpty(msg)) {
            Logger.warning("empty msg");
            return msg;
        }

        List<MessageToken> tokens = chatMessageInterface.getTokens();
        if (tokens == null || tokens.isEmpty()) {
            return msg;
        }

        String userName = accountManager.getUsername();
        if (userName == null || TextUtils.isEmpty(userName)) {
            Logger.error("empty userName");
            return msg;
        }

        for (MessageToken messageToken : tokens) {
            if (messageToken instanceof MessageToken.MentionToken) {
                MessageToken.MentionToken mentionToken = (MessageToken.MentionToken) messageToken;
                String mentionUser = mentionToken.getUserName();
                if (mentionUser == null || TextUtils.isEmpty(mentionUser))
                    continue;

                if (mentionUser.equalsIgnoreCase(userName)) {
                    SpannableString mention = new SpannableString(msg);
                    mention.setSpan(new BackgroundColorSpan(Color.argb(100, 255, 0, 0)), 0, msg.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    return SpannedString.valueOf(mention);
                }
            }
        }

        return msg;
    }

    public static SpannedString tryAddEmotes(IChatMessageFactory factory, SpannedString messageSpan, int channelID, boolean isGifsDisabled, EmoteSize emoteSize) {
        if (TextUtils.isEmpty(messageSpan)) {
            Logger.warning("empty messageSpan");
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

                    final String code = TextUtils.substring(messageSpan, startPos, currentPos);
                    final Emote emote = EmoteManager.INSTANCE.getEmote(code, channelID);
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

    public static Spanned addTimestamp(Spanned message, Date date) {
        SpannableString timeSpan = SpannableString.valueOf(new SimpleDateFormat(TIMESTAMP_DATE_FORMAT, Locale.UK).format(date));
        timeSpan.setSpan(new RelativeSizeSpan(TIMESTAMP_SPAN_PROPORTIONAL), 0, timeSpan.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return SpannableString.valueOf(new SpannableStringBuilder(timeSpan).append(new SpannableStringBuilder(message)));
    }

    public static Integer fixUsernameColor(int color, boolean isDarkTheme) {
        return isDarkTheme ? mDarkThemeCache.get(color) : mWhiteThemeCache.get(color);
    }
}
