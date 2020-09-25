package tv.twitch.android.mod.utils;


import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.LruCache;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import tv.twitch.android.mod.bridges.interfaces.IChatMessageFactory;
import tv.twitch.android.mod.bridges.interfaces.ILiveChatSource;
import tv.twitch.android.mod.chat.fetchers.RobottyFetcher;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.preferences.EmoteSize;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.shared.chat.util.ClickableUsernameSpan;


public class ChatUtil {
    private static final String TIMESTAMP_DATE_FORMAT = "HH:mm ";

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


    public static void tryAddRecentMessages(final ILiveChatSource source, final ChannelInfo channelInfo, int limit) {
        if (source == null) {
            Logger.error("source is null");
            return;
        }

        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }

        int channelId = channelInfo.getId();

        if (channelId <= 0) {
            Logger.error("Bad ID: " + channelId);
            return;
        }

        source.addMessage("[ROBOTTY] Fetching recent messages... ( https://recent-messages.robotty.de )");
        RobottyFetcher fetcher = new RobottyFetcher(channelInfo, limit, new RobottyFetcher.Callback() {
            @Override
            public void onMessagesParsed(ChannelInfo channel, List<String> ircMessages) {
                if (channel == null) {
                    Logger.error("channel is null");
                    return;
                }

                List<String> messages = new ArrayList<>();
                for (int j = ircMessages.size() - 1; j >= 0; j--) {
                    String chatMessageText = IrcUtil.formatIrcMessage(ircMessages.get(j));
                    if (chatMessageText != null) {
                        messages.add(0, chatMessageText);
                    }
                }

                if (messages.size() == 0) {
                    source.addMessage("[ROBOTTY] No message available");
                    return;
                }

                for (String msg : messages) {
                    source.addMessage(msg);
                }
            }

            @Override
            public void onError(String text) {
                if (!TextUtils.isEmpty(text)) {
                    Logger.error(text);
                    source.addMessage("[ROBOTTY] Error: " + text);
                }
            }
        });
        fetcher.fetch();
    }

    public static Spanned tryAddStrikethrough(Spanned msg) {
        try {
            if (TextUtils.isEmpty(msg))
                return msg;

            StrikethroughSpan[] strikethroughSpanArr = msg.getSpans(0, msg.length(), StrikethroughSpan.class);
            if (strikethroughSpanArr != null && strikethroughSpanArr.length > 0)
                return msg;

            ClickableUsernameSpan[] clickableUsernameSpanArr = msg.getSpans(0, msg.length(), ClickableUsernameSpan.class);
            int usernameSpannedEnd = 0;
            if (clickableUsernameSpanArr.length != 0) {
                usernameSpannedEnd = msg.getSpanEnd(clickableUsernameSpanArr[0]);
                int i = usernameSpannedEnd + 2;
                if (i < msg.length()) {
                    if (msg.subSequence(usernameSpannedEnd, i).toString().equalsIgnoreCase(": ")) {
                        usernameSpannedEnd = i;
                    }
                }
            }

            SpannableStringBuilder msgBuilder = new SpannableStringBuilder(msg, 0, usernameSpannedEnd);

            SpannableStringBuilder msgTextBuilder = new SpannableStringBuilder(msg, usernameSpannedEnd, msg.length());
            msgTextBuilder.setSpan(new StrikethroughSpan(), 0, msgTextBuilder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            msgBuilder.append(msgTextBuilder);

            return SpannedString.valueOf(msgBuilder);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return msg;
    }

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

    public static SpannedString tryAddEmotes(IChatMessageFactory factory, SpannedString messageSpan, int channelID, boolean isGifsDisabled, @EmoteSize int emoteSize) {
        if (factory == null) {
            Logger.error("factory is null");
            return messageSpan;
        }

        if (TextUtils.isEmpty(messageSpan)) {
            Logger.warning("empty messageSpan");
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
                    final Emote emote = EmoteManager.INSTANCE.findEmote(code, channelID);
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
        return SpannableString.valueOf(new SpannableStringBuilder(timeSpan).append(new SpannableStringBuilder(message)));
    }

    public static Integer fixUsernameColor(int color, boolean isDarkTheme) {
        return isDarkTheme ? mDarkThemeCache.get(color) : mWhiteThemeCache.get(color);
    }
}
