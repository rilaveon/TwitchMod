package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;

import com.google.android.exoplayer2.PlaybackParameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import tv.twitch.android.mod.badges.BadgeManager;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.settings.ChatWidthPercent;
import tv.twitch.android.shared.chat.events.ChannelSetEvent;
import tv.twitch.android.shared.chat.ChatMessageInterface;
import tv.twitch.android.shared.emotes.emotepicker.models.EmoteUiModel;
import tv.twitch.android.shared.emotes.emotepicker.models.EmoteUiSet;
import tv.twitch.android.shared.experiments.Experiment;
import tv.twitch.android.api.parsers.PlayableModelParser;
import tv.twitch.android.mod.bridges.interfaces.IChatMessageFactory;
import tv.twitch.android.mod.bridges.models.EmoteSet;
import tv.twitch.android.mod.bridges.models.EmoteUiModelWithUrl;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.settings.UserMessagesFiltering;
import tv.twitch.android.mod.models.settings.Gifs;
import tv.twitch.android.mod.models.settings.PlayerImpl;
import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.mod.utils.ChatMesssageFilteringUtil;
import tv.twitch.android.mod.utils.ChatUtil;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.util.EmoteUrlUtil;
import tv.twitch.chat.ChatEmoticonSet;
import tv.twitch.chat.ChatLiveMessage;


@SuppressWarnings({"FinalStaticMethod"})
public class Hooks {
    private final static String VOD_PLAYER_PRESENTER_CLASS = "tv.twitch.android.shared.player.presenters.VodPlayerPresenter";


    public final static String hookSetName(String org, String setId) {
        if (!PreferenceManager.INSTANCE.isEmotePickerOn())
            return org;

        EmoteSet set = EmoteSet.findById(setId);
        if (set != null)
            return set.getTitle();

        return org;
    }

    public final static boolean hookExperimental(Experiment experimental, boolean org) {
        if (experimental == null) {
            Logger.error("experimental is null");
            return org;
        }

        switch (experimental) {
            case NIELSEN:
            case VAES_OM:
            case SURESTREAM_OM:
            case Nielsen_S2S:
            case PROMOTED_STREAMS:
            case ADS_PBYP:
                if (PreferenceManager.INSTANCE.isAdblockEnabled())
                    return false;
                else
                    return org;

            case UPDATE_PROMPT_ROLLOUT:
                return false;

            case CREATOR_SETTINGS_MENU:
                return false;

            case FLOATING_CHAT:
                return PreferenceManager.INSTANCE.isFloatingChatEnabled();
            case NEW_EMOTE_PICKER:
                return !PreferenceManager.INSTANCE.isOldEmotePicker();
            case GLOBAL_FOLLOW_BUTTON_REVAMP:
                return !PreferenceManager.INSTANCE.isOldFollowButton();
        }

        return org;
    }

    public final static PlaybackParameters hookVodPlayerStandaloneMediaClockInit() {
        if (Helper.isOnStackTrace(VOD_PLAYER_PRESENTER_CLASS)) {
            PreferenceManager preferenceManager = PreferenceManager.INSTANCE;
            float speed = Float.parseFloat(preferenceManager.getExoplayerSpeed().getValue());
            return new PlaybackParameters(speed);
        }

        return PlaybackParameters.DEFAULT;
    }


    public final static Spanned addTimestampToMessage(Spanned message) {
        if (!PreferenceManager.INSTANCE.isMessageTimestampOn())
            return message;

        return ChatUtil.addTimestamp(message, new Date());
    }

    public final static ChatEmoticonSet[] hookChatEmoticonSet(ChatEmoticonSet[] orgSet) {
        if (orgSet == null)
            return null;

        if (!PreferenceManager.INSTANCE.isEmotePickerOn())
            return orgSet;

        final int currentChannel = Helper.INSTANCE.getCurrentChannel();
        Collection<Emote> globalEmotes = EmoteManager.INSTANCE.getGlobalEmotes();
        Collection<Emote> bttvEmotes = EmoteManager.INSTANCE.getBttvEmotes(currentChannel);
        Collection<Emote> ffzEmotes = EmoteManager.INSTANCE.getFfzEmotes(currentChannel);

        ChatEmoticonSet[] newSet = new ChatEmoticonSet[orgSet.length+3];
        System.arraycopy(orgSet, 0, newSet, 0, orgSet.length);

        newSet[newSet.length-1] = ChatFactory.getSet(EmoteSet.BTTV.getId(), bttvEmotes);
        newSet[newSet.length-2] = ChatFactory.getSet(EmoteSet.FFZ.getId(), ffzEmotes);
        newSet[newSet.length-3] = ChatFactory.getSet(EmoteSet.GLOBAL.getId(), globalEmotes);

        return newSet;
    }

    public final static void requestEmotes(ChannelInfo channelInfo) {
        if (!PreferenceManager.INSTANCE.isBttvOn())
            return;

        EmoteManager.INSTANCE.fetchGlobalEmotes();
        EmoteManager.INSTANCE.requestEmotes(channelInfo.getId());
    }

    public final static void requestEmotes(final PlayableModelParser playableModelParser, final Playable playable) {
        if (!PreferenceManager.INSTANCE.isBttvOn())
            return;

        EmoteManager.INSTANCE.fetchGlobalEmotes();
        EmoteManager.INSTANCE.requestEmotes(Helper.getChannelId(playableModelParser, playable));
    }

    public final static boolean hookFollowedGamesFetcher(boolean org) {
        if (!PreferenceManager.INSTANCE.isDisableFollowedGames())
            return org;

        return false;
    }

    public final static boolean hookRecommendedStreamsFetcher(boolean org) {
        if (!PreferenceManager.INSTANCE.isDisableRecommendations())
            return org;

        return false;
    }

    public final static boolean hookResumeWatchingFetcher(boolean org) {
        if (!PreferenceManager.INSTANCE.isDisableRecentWatching())
            return org;

        return false;
    }

    public final static boolean hookVideoDebugPanel(boolean org) {
        if (!PreferenceManager.INSTANCE.isShowVideoDebugPanel())
            return org;

        return true;
    }

    public final static int hookMiniplayerSize(int size) {
        PreferenceManager preferenceManager = PreferenceManager.INSTANCE;
        float k = Float.parseFloat(preferenceManager.getMiniPlayerSize().getValue());

        return (int) (k * size);
    }

    public final static String hookPlayerProvider(String name) {
        if (TextUtils.isEmpty(name)) {
            Logger.warning("empty name");
            return name;
        }

        PlayerImpl playerImpl = PreferenceManager.INSTANCE.getPlayerImplementation();
        switch (playerImpl) {
            default:
            case AUTO:
                return name;
            case CORE:
            case EXO:
                return playerImpl.getValue();
        }
    }

    public final static boolean isJumpDisRecentSearch() {
        return PreferenceManager.INSTANCE.isDisableRecentSearch();
    }

    public final static boolean isDevModeOn() {
        return PreferenceManager.INSTANCE.isDevModeOn();
    }

    public final static boolean isAdblockOn() {
        return PreferenceManager.INSTANCE.isAdblockEnabled();
    }

    public final static boolean isInterceptorOn() {
        return PreferenceManager.INSTANCE.isInterceptorOn();
    }

    public final static List<? extends ChatLiveMessage> hookLiveMessages(List<? extends ChatLiveMessage> list, String accountName) {
        if (list == null || list.isEmpty()) {
            Logger.warning("empty list");
            return list;
        }

        UserMessagesFiltering filtering = PreferenceManager.INSTANCE.getChatFiltering();
        if (filtering == UserMessagesFiltering.DISABLED)
            return list;

        ArrayList<ChatLiveMessage> filtered = new ArrayList<>();
        for (ChatLiveMessage liveMessage : list) {
            if (liveMessage == null || liveMessage.messageInfo == null) {
                filtered.add(liveMessage);
                continue;
            }

            if (ChatMesssageFilteringUtil.filter(liveMessage, accountName, filtering))
                filtered.add(liveMessage);
        }

        return filtered;
    }

    public final static boolean isJumpDisableAutoplay() {
        return PreferenceManager.INSTANCE.isDisableAutoplay();
    }

    public final static void setCurrentChannel(ChannelSetEvent event) {
        if (event == null) {
            Logger.error("event is null");
            return;
        }

        Helper.INSTANCE.setCurrentChannel(event.getChannelInfo().getId());
    }

    public final static boolean isHideDiscoverTab() {
        return PreferenceManager.INSTANCE.isHideDiscoverTab();
    }

    public final static boolean isHideEsportsTab() {
        return PreferenceManager.INSTANCE.isHideEsportsTab();
    }

    public final static boolean isGifsEnabled() {
        return PreferenceManager.INSTANCE.getGifsStrategy() == Gifs.ANIMATED;
    }

    /**
     * Some hooks
     */
    public final static void helper() {
        Object o = hookVodPlayerStandaloneMediaClockInit(); // TODO: __HOOK
        boolean ad = PreferenceManager.INSTANCE.isAdblockEnabled();
        if (!PreferenceManager.INSTANCE.isHideGs())
            return;
    }

    public final static int hookUsernameSpanColor(int usernameColor) {
        return ChatUtil.fixUsernameColor(usernameColor, PreferenceManager.INSTANCE.isDarkThemeEnabled());
    }

    public static SpannedString hookBadges(IChatMessageFactory factory, ChatMessageInterface chatMessageInterface, SpannedString badges) {
        if (!PreferenceManager.INSTANCE.isFfzBadgesOn())
            return badges;

        if (factory == null) {
            Logger.error("factory is null");
            return badges;
        }
        if (chatMessageInterface == null) {
            Logger.error("chatMessageInterface is null");
            return badges;
        }
        if (badges == null) {
            Logger.error("badges is null");
            return badges;
        }

        try {
            List<Badge> newBadges = BadgeManager.INSTANCE.getBadgesForUser(chatMessageInterface.getUserId());
            if (newBadges != null && !newBadges.isEmpty()) {
                SpannableStringBuilder ssb = new SpannableStringBuilder(badges);
                for (Badge badge : newBadges) {
                    if (badge == null)
                        continue;

                    CharSequence spannedBadge = factory.getSpannedBadge(badge.getUrl(), badge.getName());
                    if (TextUtils.isEmpty(spannedBadge))
                        continue;

                    if (!TextUtils.isEmpty(badge.getReplaces())) {
                        final String replaces = badge.getReplaces() + " ";
                        int pos = TextUtils.indexOf(ssb, replaces);
                        if (pos != -1) {
                            ssb.replace(pos, replaces.length(), spannedBadge);
                        }
                    } else {
                        ssb.append(spannedBadge);
                    }
                }

                return new SpannedString(ssb);
            }

        } catch (Throwable th) {
            th.printStackTrace();
        }

        return badges;
    }

    public static SpannedString hookChatMessage(IChatMessageFactory factory, ChatMessageInterface chatMessageInterface, SpannedString orgMessage, int channelId) {
        PreferenceManager manager = PreferenceManager.INSTANCE;
        if (!manager.isBttvOn())
            return orgMessage;

        try {
            if (TextUtils.isEmpty(orgMessage))
                return orgMessage;

            if (chatMessageInterface.isDeleted())
                return orgMessage;

            return ChatUtil.injectEmotesSpan(factory, orgMessage, channelId, manager.getGifsStrategy() == Gifs.DISABLED, manager.getEmoteSize());
        } catch (Throwable th) {
            th.printStackTrace();
        }

        return orgMessage;
    }

    public static boolean isJumpSystemIgnore() {
        return PreferenceManager.INSTANCE.isIgnoreSystemMessages();
    }

    public static List<EmoteUiSet> hookEmotePickerSet(List<EmoteUiSet> list, Integer channelId) {
        if (!PreferenceManager.INSTANCE.isEmotePickerOn())
            return list;

        if (list == null)
            return null;

        Collection<Emote> bttvGlobalEmotes = EmoteManager.INSTANCE.getGlobalEmotes();
        if (bttvGlobalEmotes != null && !bttvGlobalEmotes.isEmpty()) {
            Integer resId = ResourcesManager.INSTANCE.getStringId(EmoteSet.GLOBAL.getTitleRes());
            list.add(ChatFactory.getEmoteSetUi(bttvGlobalEmotes, resId));
        }

        if (channelId != null && channelId != -1) {
            Collection<Emote> bttvChannelEmotes = EmoteManager.INSTANCE.getBttvEmotes(channelId);
            if (bttvChannelEmotes != null && !bttvChannelEmotes.isEmpty()) {
                Integer resId = ResourcesManager.INSTANCE.getStringId(EmoteSet.BTTV.getTitleRes());
                list.add(ChatFactory.getEmoteSetUi(bttvChannelEmotes, resId));
            }

            Collection<Emote> ffzChannelEmotes = EmoteManager.INSTANCE.getFfzEmotes(channelId);
            if (ffzChannelEmotes != null && !ffzChannelEmotes.isEmpty()) {
                Integer resId = ResourcesManager.INSTANCE.getStringId(EmoteSet.FFZ.getTitleRes());
                list.add(ChatFactory.getEmoteSetUi(ffzChannelEmotes, resId));
            }
        }

        return list;
    }

    public static String hookEmoteAdapterItem(Context context, EmoteUiModel emoteUiModel) {
        if (emoteUiModel instanceof EmoteUiModelWithUrl) {
            return ((EmoteUiModelWithUrl) emoteUiModel).getUrl();
        }

        return EmoteUrlUtil.getEmoteUrl(context, emoteUiModel.getId());
    }

    public static int hookChatWidth(int org) {
        ChatWidthPercent percent = PreferenceManager.INSTANCE.getChatWidthPercent();
        if (percent == ChatWidthPercent.DEFAULT)
            return org;

        return Integer.parseInt(percent.getValue());
    }
}
