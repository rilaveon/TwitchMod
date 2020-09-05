package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;

import com.google.android.exoplayer2.PlaybackParameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import tv.twitch.android.core.user.TwitchAccountManager;
import tv.twitch.android.mod.badges.BadgeManager;
import tv.twitch.android.mod.bridges.interfaces.ILiveChatSource;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.preferences.ChatWidthScale;
import tv.twitch.android.mod.models.preferences.MsgDelete;
import tv.twitch.android.shared.chat.adapter.item.ChatMessageClickedEvents;
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
import tv.twitch.android.mod.models.preferences.UserMessagesFiltering;
import tv.twitch.android.mod.models.preferences.Gifs;
import tv.twitch.android.mod.models.preferences.PlayerImpl;
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
        EmoteSet set = EmoteSet.findById(setId);
        if (set != null)
            return set.getTitle();

        return org;
    }

    public final static boolean isForceOldClipsViewJump() {
        return PreferenceManager.INSTANCE.isForceOldClips();
    }

    public final static boolean hookExperimental(Experiment experimental, boolean org) {
        if (experimental == null) {
            Logger.error("experimental is null");
            return org;
        }

        switch (experimental) {
            case VAES_OM:
            case SURESTREAM_OM:
            case ADS_PBYP:
            case MULTIPLAYER_ADS:
                if (PreferenceManager.INSTANCE.isAdblockEnabled())
                    return false;
                else
                    return org;

            case FLOATING_CHAT:
                return PreferenceManager.INSTANCE.isFloatingChatEnabled();
            case NEW_EMOTE_PICKER:
                return !PreferenceManager.INSTANCE.isForceOldEmotePicker();
        }

        return org;
    }

    public final static PlaybackParameters hookVodPlayerStandaloneMediaClockInit() {
        if (Helper.isOnStackTrace(VOD_PLAYER_PRESENTER_CLASS)) {
            PreferenceManager preferenceManager = PreferenceManager.INSTANCE;
            float speed = Float.parseFloat(preferenceManager.getExoplayerSpeed());
            return new PlaybackParameters(speed);
        }

        return PlaybackParameters.DEFAULT;
    }

    public final static Spanned addTimestampToMessage(Spanned message, int userId) {
        if (!PreferenceManager.INSTANCE.isMessageTimestampOn())
            return message;

        if (userId <= 0)
            return message;

        return ChatUtil.addTimestamp(message, new Date());
    }

    public final static ChatEmoticonSet[] hookChatEmoticonSet(ChatEmoticonSet[] orgSet) {
        if (orgSet == null)
            return null;

        if (!PreferenceManager.INSTANCE.isBttvOn())
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
        EmoteManager.INSTANCE.requestRoomEmotes(channelInfo.getId());
    }

    public final static void requestEmotes(final PlayableModelParser playableModelParser, Playable playable) {
        if (!PreferenceManager.INSTANCE.isBttvOn())
            return;

        EmoteManager.INSTANCE.fetchGlobalEmotes();
        EmoteManager.INSTANCE.requestRoomEmotes(Helper.getChannelId(playableModelParser, playable));
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

    public final static int hookMiniplayerSize(int size) {
        PreferenceManager preferenceManager = PreferenceManager.INSTANCE;
        float k = Float.parseFloat(preferenceManager.getMiniPlayerSize());

        return (int) (k * size);
    }

    public final static String hookPlayerProvider(String name) {
        if (TextUtils.isEmpty(name)) {
            Logger.warning("empty name");
            return name;
        }

        switch (PreferenceManager.INSTANCE.getPlayerImplementation()) {
            default:
            case PlayerImpl.AUTO:
                return name;
            case PlayerImpl.CORE:
                return "playercore";
            case PlayerImpl.EXO:
                return "exoplayer_2";
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

    public final static List<? extends ChatLiveMessage> hookLiveMessages(List<? extends ChatLiveMessage> list, TwitchAccountManager accountManager) {
        if (accountManager == null) {
            Logger.error("accountManager is null");
            return list;
        }

        if (list == null || list.isEmpty())
            return list;

        @UserMessagesFiltering int filtering = PreferenceManager.INSTANCE.getChatFiltering();
        if (filtering == UserMessagesFiltering.DISABLED)
            return list;

        ArrayList<ChatLiveMessage> filtered = new ArrayList<>();
        for (ChatLiveMessage liveMessage : list) {
            if (liveMessage == null || liveMessage.messageInfo == null) {
                filtered.add(liveMessage);
                continue;
            }

            if (ChatMesssageFilteringUtil.filter(liveMessage, accountManager.getUsername(), filtering))
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

    public final static boolean isHideGsJump() {
        return PreferenceManager.INSTANCE.isHideGs();
    }

    /**
     * For hooks ref
     */
    public final static void helper() {
        Object o = hookVodPlayerStandaloneMediaClockInit(); // TODO: __HOOK
    }

    public final static int hookUsernameSpanColor(int usernameColor) {
        return ChatUtil.fixUsernameColor(usernameColor, PreferenceManager.INSTANCE.isDarkThemeEnabled());
    }

    public static int getFloatingChatQueueSize() {
        return PreferenceManager.INSTANCE.getFloatingChatQueueSize();
    }

    public static int getFloatingChatRefresh() {
        return PreferenceManager.INSTANCE.getFloatingChatRefresh();
    }

    public static void injectRecentMessages(final ILiveChatSource source, final ChannelInfo channelInfo) {
        if (source == null) {
            Logger.error("source is null");
            return;
        }

        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }

        if (!PreferenceManager.INSTANCE.isMessageHistoryEnabled())
            return;

        ChatUtil.tryAddRecentMessages(source, channelInfo);
    }

    public static Spanned hookMarkAsDeleted(tv.twitch.android.shared.chat.util.ChatUtil.Companion companion, Spanned msg, Context context, PublishSubject<ChatMessageClickedEvents> publishSubject, boolean hasModAccess) {
        if (TextUtils.isEmpty(msg)) {
            Logger.error("empty msg");
            return msg;
        }

        switch (PreferenceManager.INSTANCE.getMsgDelete()) {
            case MsgDelete.DEFAULT:
                return companion.createDeletedSpanFromChatMessageSpan(msg, context, publishSubject, hasModAccess);
            case MsgDelete.MOD:
                return companion.createDeletedSpanFromChatMessageSpan(msg, context, publishSubject, true);
            case MsgDelete.STRIKETHROUGH:
                return ChatUtil.tryAddStrikethrough(msg);
            default:
                return msg;
        }
    }

    public static SpannedString hookBadges(IChatMessageFactory factory, ChatMessageInterface chatMessageInterface, SpannedString badges) {
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

        if (PreferenceManager.INSTANCE.isThirdPartyBadgesOn()) {
            try {
                Collection<Badge> newBadges = BadgeManager.INSTANCE.findBadges(chatMessageInterface.getUserId());
                if (!newBadges.isEmpty()) {
                    badges = ChatUtil.tryAddBadges(badges, factory, newBadges);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        try {
            Collection<Badge> newBadges = BadgeManager.INSTANCE.getCustomBadges(chatMessageInterface.getUserId());
            if (!newBadges.isEmpty()) {
                badges = ChatUtil.tryAddBadges(badges, factory, newBadges);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }

        return badges;
    }

    public static SpannedString hookChatMessage(IChatMessageFactory factory, ChatMessageInterface chatMessageInterface, SpannedString orgMessage, int channelId, TwitchAccountManager accountManager) {
        PreferenceManager manager = PreferenceManager.INSTANCE;
        if (!manager.isBttvOn())
            return orgMessage;

        if (TextUtils.isEmpty(orgMessage))
            return orgMessage;

        if (chatMessageInterface.isDeleted())
            return orgMessage;

        try {
            SpannedString hooked = ChatUtil.tryAddEmotes(factory, orgMessage, channelId, manager.getGifsStrategy() == Gifs.DISABLED, manager.getEmoteSize());
            if (manager.isRedMentionOn()) {
                hooked = ChatUtil.tryAddRedMention(hooked, accountManager, chatMessageInterface);
            }

            return hooked;
        } catch (Throwable th) {
            th.printStackTrace();
        }

        return orgMessage;
    }

    public static boolean isJumpSystemIgnore() {
        return PreferenceManager.INSTANCE.isIgnoreSystemMessages();
    }

    public static List<EmoteUiSet> hookEmotePickerSet(List<EmoteUiSet> emoteUiSets, Integer channelId) {
        if (emoteUiSets == null)
            return null;

        if (!PreferenceManager.INSTANCE.isBttvOn())
            return emoteUiSets;

        Collection<Emote> bttvGlobalEmotes = EmoteManager.INSTANCE.getGlobalEmotes();
        if (!bttvGlobalEmotes.isEmpty()) {
            Integer resId = ResourcesManager.INSTANCE.getStringId(EmoteSet.GLOBAL.getTitleResId());
            emoteUiSets.add(ChatFactory.getEmoteUiSet(bttvGlobalEmotes, resId));
        }

        if (channelId != null && channelId != -1) {
            Collection<Emote> bttvChannelEmotes = EmoteManager.INSTANCE.getBttvEmotes(channelId);
            if (!bttvChannelEmotes.isEmpty()) {
                Integer resId = ResourcesManager.INSTANCE.getStringId(EmoteSet.BTTV.getTitleResId());
                emoteUiSets.add(ChatFactory.getEmoteUiSet(bttvChannelEmotes, resId));
            }

            Collection<Emote> ffzChannelEmotes = EmoteManager.INSTANCE.getFfzEmotes(channelId);
            if (!ffzChannelEmotes.isEmpty()) {
                Integer resId = ResourcesManager.INSTANCE.getStringId(EmoteSet.FFZ.getTitleResId());
                emoteUiSets.add(ChatFactory.getEmoteUiSet(ffzChannelEmotes, resId));
            }
        }

        return emoteUiSets;
    }

    public static String hookEmoteAdapterItem(Context context, EmoteUiModel emoteUiModel) {
        if (emoteUiModel instanceof EmoteUiModelWithUrl) {
            return ((EmoteUiModelWithUrl) emoteUiModel).getUrl();
        }

        return EmoteUrlUtil.getEmoteUrl(context, emoteUiModel.getId());
    }

    public static int hookChatWidth(int org) {
        @ChatWidthScale int scale = PreferenceManager.INSTANCE.getChatWidthScale();
        if (scale == ChatWidthScale.DEFAULT)
            return org;

        return scale;
    }

    public static boolean isBypassChatBanJump() {
        return PreferenceManager.INSTANCE.isBypassChatBan();
    }
}
