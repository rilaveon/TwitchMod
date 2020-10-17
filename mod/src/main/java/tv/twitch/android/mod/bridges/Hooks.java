package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.google.android.exoplayer2.PlaybackParameters;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import tv.twitch.android.core.user.TwitchAccountManager;
import tv.twitch.android.mod.badges.BadgeManager;
import tv.twitch.android.mod.bridges.interfaces.IChatConnectionController;
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
import tv.twitch.chat.ChatMentionToken;
import tv.twitch.chat.ChatMessageInfo;
import tv.twitch.chat.ChatMessageToken;


@SuppressWarnings({"FinalStaticMethod"})
public class Hooks {
    private final static String VOD_PLAYER_PRESENTER_CLASS = "tv.twitch.android.shared.player.presenters.VodPlayerPresenter";
    private final static String PLAYER_CORE = "playercore";
    private final static String PLAYER_EXO2 = "exoplayer_2";


    public final static String hookSetName(String org, String setId) {
        EmoteSet set = EmoteSet.findById(setId);
        if (set != null)
            return set.getTitle();

        return org;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public final static String hookExperimentalGroup(Experiment experiment, String org) {
        if (experiment == null) {
            Logger.error("experimental is null");
            return org;
        }

        switch (experiment) {
            case VIDEOPLAYER_SELECTION:
                switch (PreferenceManager.INSTANCE.getPlayerImplementation()) {
                    default:
                    case PlayerImpl.AUTO:
                        return org;
                    case PlayerImpl.CORE:
                        return PLAYER_CORE;
                    case PlayerImpl.EXO:
                        return PLAYER_EXO2;
                }
        }

        return org;
    }

    @SuppressWarnings("SimplifiableConditionalExpression")
    public final static boolean hookExperimental(Experiment experiment, boolean org) {
        if (experiment == null) {
            Logger.error("experiment is null");
            return org;
        }

        switch (experiment) {
            case UPDATE_PROMPT_ROLLOUT:
                return false;

            case MGST_DISABLE_PRE_ROLLS:
                return PreferenceManager.INSTANCE.isPlayerAdblockOn() ? true : org;

            case CREATOR_SETTINGS_MENU:
                return true;

            case CHAT_INPUT_FOLLOWER:
            case CHAT_INPUT_VERIFIED:
            case CHAT_INPUT_SUBSCRIBER:
                return PreferenceManager.INSTANCE.hideChatRestriction() ? false : org;

            case VAES_OM:
            case GRANDDADS:
            case SURESTREAM_OM:
            case SURESTREAM_ADS_PBYP:
            case ADS_PBYP:
            case MULTIPLAYER_ADS:
                return PreferenceManager.INSTANCE.isPlayerAdblockOn() ? false : org;

            case FLOATING_CHAT:
                return PreferenceManager.INSTANCE.showFloatingChat();

            case NEW_EMOTE_PICKER:
                return PreferenceManager.INSTANCE.forceOldEmotePickerView() ? false : org;
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

    public final static boolean isHighlightedMessage(ChatMessageInfo messageInfo, TwitchAccountManager accountManager) {
        if (!PreferenceManager.INSTANCE.showMentionHighlights())
            return false;

        if (accountManager == null) {
            Logger.error("accountManager is null");
            return false;
        }

        if (messageInfo == null) {
            Logger.error("messageInfo is null");
            return false;
        }

        ChatMessageToken[] tokens = messageInfo.tokens;
        if (tokens == null || tokens.length == 0) {
            return false;
        }

        String userName = accountManager.getUsername();
        if (TextUtils.isEmpty(userName)) {
            Logger.error("empty userName");
            return false;
        }

        for (ChatMessageToken messageToken : tokens) {
            if (messageToken instanceof ChatMentionToken) {
                ChatMentionToken mentionToken = (ChatMentionToken) messageToken;
                String mentionUser = mentionToken.userName;
                if (TextUtils.isEmpty(mentionUser))
                    continue;

                if (mentionUser != null && mentionUser.equalsIgnoreCase(userName)) {
                    return true;
                }
            }
        }

        return false;
    }

    public final static Spanned addTimestampToMessage(Spanned message, int userId) {
        if (message == null)
            return null;

        if (!PreferenceManager.INSTANCE.isChatTimestampsEnabled())
            return message;

        if (userId <= 0)
            return message;

        return ChatUtil.addTimestamp(message, new Date());
    }

    public final static ChatEmoticonSet[] hookChatEmoticonSet(ChatEmoticonSet[] orgSet) {
        if (orgSet == null)
            return null;

        if (!PreferenceManager.INSTANCE.showBttvEmotesInChat())
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
        if (!PreferenceManager.INSTANCE.showBttvEmotesInChat())
            return;

        EmoteManager.INSTANCE.fetchGlobalEmotes();
        EmoteManager.INSTANCE.requestRoomEmotes(channelInfo.getId());
    }

    public final static void requestEmotes(final PlayableModelParser playableModelParser, Playable playable) {
        if (!PreferenceManager.INSTANCE.showBttvEmotesInChat())
            return;

        EmoteManager.INSTANCE.fetchGlobalEmotes();
        EmoteManager.INSTANCE.requestRoomEmotes(Helper.getChannelId(playableModelParser, playable));
    }

    public final static boolean shouldShowStatButton() {
        return PreferenceManager.INSTANCE.shouldShowPlayerStatButton();
    }

    public final static boolean shouldShowRefreshButton() {
        return PreferenceManager.INSTANCE.shouldShowPlayerRefreshButton();
    }

    public final static boolean shouldShowLockButton() {
        return PreferenceManager.INSTANCE.shouldShowLockButton();
    }

    public final static boolean isSwipperEnabled() {
        return PreferenceManager.INSTANCE.isVolumeSwiperEnabled() || PreferenceManager.INSTANCE.isBrightnessSwiperEnabled();
    }

    public final static boolean shouldLockSwiper() {
        return PreferenceManager.INSTANCE.shouldLockSwiper();
    }

    public final static void setLockSwiper(boolean z) {
        PreferenceManager.INSTANCE.setLockSwiper(z);
    }

    public final static boolean isFollowedGamesFetcherJump() {
        return PreferenceManager.INSTANCE.hideFollowGameSection();
    }

    public final static boolean isRecommendedStreamsFetcher() {
        return PreferenceManager.INSTANCE.hideFollowRecommendationSection();
    }

    public final static boolean isResumeWatchingFetcher() {
        return PreferenceManager.INSTANCE.hideFollowResumeSection();
    }

    public final static boolean hookFollowedGamesFetcher(boolean org) {
        if (!PreferenceManager.INSTANCE.hideFollowGameSection())
            return org;

        return false;
    }

    public final static boolean hookRecommendedStreamsFetcher(boolean org) {
        if (!PreferenceManager.INSTANCE.hideFollowRecommendationSection())
            return org;

        return false;
    }

    public final static boolean hookResumeWatchingFetcher(boolean org) {
        if (!PreferenceManager.INSTANCE.hideFollowResumeSection())
            return org;

        return false;
    }

    public final static int hookMiniPlayerWidth(int size) {
        PreferenceManager preferenceManager = PreferenceManager.INSTANCE;
        float k = Float.parseFloat(preferenceManager.getMiniPlayerScale());

        return (int) (k * size);
    }

    public final static boolean isJumpDisRecentSearch() {
        return PreferenceManager.INSTANCE.hideRecentSearchResult();
    }

    public final static boolean isPlayerMetadataJump() {
        return PreferenceManager.INSTANCE.isCompactPlayerFollowViewEnabled();
    }

    public final static int hookPlayerMetadataViewId(int org) {
        if (!PreferenceManager.INSTANCE.isCompactPlayerFollowViewEnabled())
            return org;

        int id = ResourcesManager.INSTANCE.getLayoutId("player_metadata_view_extended_mod");
        if (id == 0) {
            Logger.error("layout not found");
            return org;
        }

        return id;
    }

    public final static boolean isDevModeOn() {
        return PreferenceManager.INSTANCE.isDevModeOn();
    }

    public final static boolean isAdblockOn() {
        return PreferenceManager.INSTANCE.isPlayerAdblockOn();
    }

    public final static boolean isInterceptorOn() {
        return PreferenceManager.INSTANCE.isInterceptorEnabled();
    }

    public final static ChatLiveMessage[] hookReceivedMessages(IChatConnectionController connectionController, ChatLiveMessage[] chatLiveMessageArr) {
        if (chatLiveMessageArr == null || chatLiveMessageArr.length == 0)
            return chatLiveMessageArr;

        ChatMesssageFilteringUtil filteringUtil = ChatMesssageFilteringUtil.INSTANCE;
        if (!filteringUtil.isEnabled())
            return chatLiveMessageArr;

        chatLiveMessageArr = filteringUtil.filterByKeywords(chatLiveMessageArr);

        if (connectionController == null) {
            Logger.error("connectionController is null");
            return chatLiveMessageArr;
        }

        int viewerId = connectionController.getViewerId();
        if (viewerId > 0) {
            chatLiveMessageArr = filteringUtil.filterByLevel(chatLiveMessageArr, viewerId, PreferenceManager.INSTANCE.getFilterMessageLevel());
        }

        return chatLiveMessageArr;
    }

    public final static boolean isJumpDisableAutoplay() {
        return PreferenceManager.INSTANCE.disableTheatreAutoplay();
    }

    public final static void setCurrentChannel(ChannelSetEvent event) {
        if (event == null) {
            Logger.error("event is null");
            return;
        }

        ChannelInfo channelInfo = event.getChannelInfo();
        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }

        Helper.INSTANCE.setCurrentChannel(channelInfo.getId());
    }

    public final static boolean isHideDiscoverTab() {
        return PreferenceManager.INSTANCE.hideDiscoverTab();
    }

    public final static boolean isHideEsportsTab() {
        return PreferenceManager.INSTANCE.hideEsportsTab();
    }

    public final static boolean isGifsEnabled() {
        return PreferenceManager.INSTANCE.getGifsStrategy() == Gifs.ANIMATED;
    }

    public final static int hookUsernameSpanColor(int usernameColor) {
        return ChatUtil.fixUsernameColor(usernameColor, PreferenceManager.INSTANCE.isDarkThemeEnabled());
    }

    public static int getFloatingChatQueueSize() {
        return PreferenceManager.INSTANCE.getFloatingChatQueueSize();
    }

    public static long getFloatingChatRefresh() {
        return PreferenceManager.INSTANCE.getFloatingChatRefreshRate();
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

        if (!PreferenceManager.INSTANCE.showMessageHistory())
            return;

        ChatUtil.tryAddRecentMessages(source, channelInfo, PreferenceManager.INSTANCE.getMessageHistoryLimit());
    }

    public static Spanned hookMarkAsDeleted(tv.twitch.android.shared.chat.util.ChatUtil.Companion companion, Spanned msg, Context context, PublishSubject<ChatMessageClickedEvents> publishSubject, boolean hasModAccess) {
        if (TextUtils.isEmpty(msg)) {
            Logger.error("empty msg");
            return msg;
        }

        switch (PreferenceManager.INSTANCE.getMsgDelete()) {
            default:
            case MsgDelete.DEFAULT:
                return companion.createDeletedSpanFromChatMessageSpan(msg, context, publishSubject, hasModAccess);
            case MsgDelete.MOD:
                return companion.createDeletedSpanFromChatMessageSpan(msg, context, publishSubject, true);
            case MsgDelete.STRIKETHROUGH:
                return ChatUtil.tryAddStrikethrough(msg);
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

        if (PreferenceManager.INSTANCE.showCustomBadges()) {
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

        if (TextUtils.isEmpty(orgMessage))
            return orgMessage;

        if (chatMessageInterface.isDeleted())
            return orgMessage;

        try {
            SpannedString hooked = orgMessage;

            if (manager.showBttvEmotesInChat())
                hooked = ChatUtil.tryAddEmotes(factory, hooked, channelId, manager.getGifsStrategy() == Gifs.DISABLED, manager.getEmoteSize());

            return hooked;
        } catch (Throwable th) {
            th.printStackTrace();
        }

        return orgMessage;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isJumpSystemIgnore() {
        return PreferenceManager.INSTANCE.hideSystemMessagesInChat();
    }

    public static List<EmoteUiSet> hookEmotePickerSet(List<EmoteUiSet> emoteUiSets, Integer channelId) {
        if (emoteUiSets == null)
            return null;

        if (!PreferenceManager.INSTANCE.showBttvEmotesInChat())
            return emoteUiSets;

        Collection<Emote> bttvGlobalEmotes = EmoteManager.INSTANCE.getGlobalEmotes();
        if (!bttvGlobalEmotes.isEmpty()) {
            Integer resId = ResourcesManager.INSTANCE.getStringId(EmoteSet.GLOBAL.getTitleResId());
            emoteUiSets.add(ChatFactory.getEmoteUiSet(bttvGlobalEmotes, resId));
        }

        if (channelId != null && channelId > 0) {
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
        @ChatWidthScale int scale = PreferenceManager.INSTANCE.getLandscapeChatScale();
        if (scale == ChatWidthScale.DEFAULT)
            return org;

        return scale;
    }

    public static boolean isBypassChatBanJump() {
        return PreferenceManager.INSTANCE.showChatForBannedUser();
    }

    public final static void helper() {
        Object o = hookVodPlayerStandaloneMediaClockInit(); // TODO: __HOOK
    }

    public static boolean isSupportWideEmotes() {
        return PreferenceManager.INSTANCE.fixWideEmotes();
    }

    public static boolean isDisableGoogleBillingJump() {
        return PreferenceManager.INSTANCE.isGoogleBillingDisabled();
    }

    public static void playVan(View view) {
        if (view == null) {
            Logger.error("view is null");
            return;
        }

        view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    AssetFileDescriptor fd = LoaderLS.getInstance().getAssets().openFd("mod/van.wav");
                    Helper.playSoundFromFd(fd);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    @SuppressWarnings({"SwitchStatementWithTooFewBranches", "SimplifiableConditionalExpression"})
    public static boolean hookExperimentalMultiVariant(Experiment experiment, String str, boolean org) {
        if (experiment == null) {
            Logger.error("experimental is null");
            return org;
        }

        if (str == null) {
            Logger.error("str is null");
            return org;
        }

        if (str.length() == 0) {
            Logger.warning("empty str");
            return org;
        }

        switch (experiment) {
            case CLIPFINITY:
                switch (str) {
                    case "control":
                        return PreferenceManager.INSTANCE.disableClipfinity() ? true : org;
                    case "active_with_entry_points":
                        return PreferenceManager.INSTANCE.disableClipfinity() ? false : org;
                    default:
                        return org;
            }
        }

        return org;
    }

    public static void tryFixWideEmotesInView(WeakReference<View> viewWeakReference) {
        if (!PreferenceManager.INSTANCE.fixWideEmotes())
            return;

        if (viewWeakReference == null)
            return;

        View view = viewWeakReference.get();
        if (view == null) {
            Logger.debug("view is null");
            return;
        }

        view.invalidate();
        view.requestLayout();
        viewWeakReference.clear();
    }
}
