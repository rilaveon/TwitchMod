package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.View;

import com.google.android.exoplayer2.PlaybackParameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import tv.twitch.a.k.g.e1.b;
import tv.twitch.a.k.g.g;
import tv.twitch.a.k.l.j.k.c;
import tv.twitch.android.api.s1.j1;
import tv.twitch.android.mod.bridges.interfaces.IChatMessageFactory;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.settings.UserMessagesFiltering;
import tv.twitch.android.mod.models.settings.Gifs;
import tv.twitch.android.mod.models.settings.PlayerImpl;
import tv.twitch.android.mod.settings.PreferenceManager;
import tv.twitch.android.mod.utils.ChatFilteringUtil;
import tv.twitch.android.mod.utils.ChatUtil;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.util.EmoteUrlUtil;
import tv.twitch.chat.ChatEmoticonSet;
import tv.twitch.chat.ChatLiveMessage;


@SuppressWarnings({"FinalStaticMethod", "rawtypes"})
public class Hooks {
    private final static String VOD_PLAYER_PRESENTER_CLASS = "tv.twitch.a.k.x.j0.w"; // TODO: __UPDATE
    private final static float DEFAULT_MINIPLAYER_SIZE = 1.0f;


    /**
     * Class: EmoteAdapterSection
     * signature: public void a(RecyclerView.b0 b0Var)
     */
    public final static String hookSetName(String org, String setId) {
        if (!LoaderLS.getPrefManagerInstance().isEmotePickerOn())
            return org;

        EmoteSet set = EmoteSet.findById(setId);
        if (set != null)
            return set.getTitle();

        return org;
    }

    /**
     * Class: ExperimentHelper
     * signature: public boolean I(a aVar)
     */
    public final static boolean hookExperimental(tv.twitch.a.k.m.a experimental, boolean org) {
        if (experimental == null) {
            Logger.error("experimental is null");
            return org;
        }

        switch (experimental) {
            case q:
            case u:
            case v:
            case h0:
                return false;

            case Z:
                return LoaderLS.getPrefManagerInstance().isFloatingChatEnabled();
            case C0:
                return !LoaderLS.getPrefManagerInstance().isOldEmotePicker();
            case j0:
                return !LoaderLS.getPrefManagerInstance().isOldFollowButton();
        }

        return org;
    }

    /**
     * Class: CommunityPointsButtonViewDelegate
     * signature: private final void e(CommunityPointsModel communityPointsModel)
     */
    public final static void setClicker(View.OnClickListener listener) {
        LoaderLS.getHelperInstance().setClicker(listener);
    }

    /**
     * Class: StandaloneMediaClock
     * signatute: private PlaybackParameters f = PlaybackParameters.e;
     */
    public final static PlaybackParameters hookVodPlayerStandaloneMediaClockInit(PlaybackParameters org) {
        PreferenceManager preferenceManager = LoaderLS.getPrefManagerInstance();
        float speed = Float.parseFloat(preferenceManager.getExoplayerSpeed().getValue());
        if (speed == org.a)
            return org;

        if (Helper.isOnStackTrace(VOD_PLAYER_PRESENTER_CLASS))
            return new PlaybackParameters(speed);

        return PlaybackParameters.e;
    }

    /**
     * Class: MessageRecyclerItem
     * signature:
     */
    public final static Spanned addTimestampToMessage(Spanned message) {
        if (!LoaderLS.getPrefManagerInstance().isMessageTimestampOn())
            return message;

        return ChatUtil.addTimestamp(message, new Date());
    }

    /**
     * Class: ChatController
     * signature: ChatEmoticonSet[] b()
     */
    public final static ChatEmoticonSet[] hookChatEmoticonSet(ChatEmoticonSet[] orgSet) {
        if (!LoaderLS.getPrefManagerInstance().isEmotePickerOn())
            return orgSet;

        if (orgSet == null)
            return null;

        Helper helper = LoaderLS.getHelperInstance();
        EmoteManager emoteManager = LoaderLS.getEmoteMangerInstance();

        final int currentChannel = helper.getCurrentChannel();
        Collection<Emote> globalEmotes = emoteManager.getGlobalEmotes();
        Collection<Emote> bttvEmotes = emoteManager.getBttvEmotes(currentChannel);
        Collection<Emote> ffzEmotes = emoteManager.getFfzEmotes(currentChannel);

        ChatEmoticonSet[] newSet = new ChatEmoticonSet[orgSet.length+3];
        System.arraycopy(orgSet, 0, newSet, 0, orgSet.length);

        newSet[newSet.length-1] = ChatFactory.getSet(EmoteSet.BTTV.getId(), bttvEmotes);
        newSet[newSet.length-2] = ChatFactory.getSet(EmoteSet.FFZ.getId(), ffzEmotes);
        newSet[newSet.length-3] = ChatFactory.getSet(EmoteSet.GLOBAL.getId(), globalEmotes);

        return newSet;
    }

    /**
     * Class: ChatConnectionController
     * signature: private final void a(ChannelInfo channelInfo)
     */
    public final static void requestEmotes(ChannelInfo channelInfo) {
        if (!LoaderLS.getPrefManagerInstance().isBttvOn())
            return;

        LoaderLS.getEmoteMangerInstance().requestEmotes(channelInfo.getId(), false);
    }

    /**
     * Class: ModelTheatreModeTracker
     * signature: public c(f1 f1Var, Playable playable, Object pageViewTracker)
     */
    public final static void requestEmotes(final j1 playableModelParser, final Playable playable) {
        if (!LoaderLS.getPrefManagerInstance().isBttvOn())
            return;

        LoaderLS.getEmoteMangerInstance().requestEmotes(Helper.getChannelId(playableModelParser, playable), true);
    }

    /**
     * Class: FollowedGamesFetcher
     * signature: public final boolean j()
     */
    public final static boolean hookFollowerFetcher(boolean org) {
        if (!LoaderLS.getPrefManagerInstance().isDisableFollowedGames())
            return org;

        return false;
    }

    /**
     * Class: RecommendedStreamsFetcher
     * signature: public final boolean j()
     */
    public final static boolean hookRecommendedFetcher(boolean org) {
        if (!LoaderLS.getPrefManagerInstance().isDisableRecommendations())
            return org;

        return false;
    }

    /**
     * Class: ResumeWatchingVideosFetcher
     * signature: public final boolean j()
     */
    public final static boolean hookResumeWatchingFetcher(boolean org) {
        if (!LoaderLS.getPrefManagerInstance().isDisableRecentWatching())
            return org;

        return false;
    }

    /**
     * Class: VideoDebugConfig
     * signature: public final boolean a()
     */
    public final static boolean hookVideoDebugPanel(boolean org) {
        if (!LoaderLS.getPrefManagerInstance().isShowVideoDebugPanel())
            return org;

        return true;
    }

    /**
     * Class: MiniPlayerSize
     * signature: public final int b()
     */
    public final static int hookMiniplayerSize(int size) {
        PreferenceManager preferenceManager = LoaderLS.getPrefManagerInstance();
        float k = Float.parseFloat(preferenceManager.getMiniPlayerSize().getValue());
        if (k == DEFAULT_MINIPLAYER_SIZE)
            return size;

        return (int) (k * size);
    }

    /**
     * Class: PlayerImplementation
     * signature: public final PlayerImplementation getProviderForName(String str)
     */
    public final static String hookPlayerProvider(String name) {
        if (TextUtils.isEmpty(name)) {
            Logger.warning("empty name");
            return name;
        }

        PlayerImpl playerImpl = LoaderLS.getPrefManagerInstance().getPlayerImplementation();
        switch (playerImpl) {
            default:
            case AUTO:
                return name;
            case CORE:
            case EXO:
                return playerImpl.getValue();
        }
    }

    /**
     * Class: SearchSuggestionAdapterBinder
     * signature: public final void a(Object obj)
     */
    public final static boolean isJumpDisRecentSearch() {
        return LoaderLS.getPrefManagerInstance().isDisableRecentSearch();
    }

    /**
     * Class: *.*
     */
    public final static boolean isDevModeOn() {
        return LoaderLS.getPrefManagerInstance().isDevModeOn();
    }

    /**
     * Class: *.*
     */
    public final static boolean isInterceptorOn() {
        return LoaderLS.getPrefManagerInstance().isInterceptorOn();
    }

    /**
     * Class: tv.twitch.a.k.g.a0
     * signature:  public final void a(int i2, List<? extends ChatLiveMessage> list...)
     */
    public final static List<? extends ChatLiveMessage> hookLiveMessages(List<? extends ChatLiveMessage> list, String accountName) {
        if (list == null || list.isEmpty()) {
            Logger.warning("empty list");
            return list;
        }

        final boolean ignoreSystem = LoaderLS.getPrefManagerInstance().isIgnoreSystemMessages();
        UserMessagesFiltering filtering = LoaderLS.getPrefManagerInstance().getChatFiltering();

        if (filtering == UserMessagesFiltering.DISABLED && !ignoreSystem)
            return list;

        ArrayList<ChatLiveMessage> filtered = new ArrayList<>();
        for (ChatLiveMessage liveMessage : list) {
            if (liveMessage == null || liveMessage.messageInfo == null) {
                filtered.add(liveMessage);
                continue;
            }

            if (ChatFilteringUtil.filter(liveMessage, accountName, filtering))
                filtered.add(liveMessage);
        }

        return filtered;
    }

    /**
     * Class: RecommendationAutoPlayPresenter
     * signature: public final void prepareRecommendationForCurrentModel(T t)
     */
    public final static boolean isJumpDisableAutoplay() {
        return LoaderLS.getPrefManagerInstance().isDisableAutoplay();
    }

    /**
     * Class: ChatMessageInputViewPresenter
     * signature: public final void a(b bVar)
     */
    public final static void setCurrentChannel(b event) {
        if (event == null) {
            Logger.error("event is null");
            return;
        }

        LoaderLS.getHelperInstance().setCurrentChannel(event.a().getId());
    }

    /**
     * Class: *.*
     */
    public final static boolean isHideDiscoverTab() {
        return LoaderLS.getPrefManagerInstance().isHideDiscoverTab();
    }

    /**
     * Class: *.*
     */
    public final static boolean isHideEsportsTab() {
        return LoaderLS.getPrefManagerInstance().isHideEsportsTab();
    }

    /**
     * Class: *.*
     */
    public final static boolean isGifsEnabled() {
        return LoaderLS.getPrefManagerInstance().getGifsStrategy() == Gifs.ANIMATED;
    }

    /**
     * Some hooks
     */
    public final static void helper() {
        Object o = hookVodPlayerStandaloneMediaClockInit(new PlaybackParameters(0.0f)); // TODO: __HOOK
    }

    /**
     * Class: ChatMessageFactory
     */
    public final static int hookUsernameSpanColor(int usernameColor) {
        return ChatUtil.fixUsernameColor(usernameColor, LoaderLS.getPrefManagerInstance().isDarkThemeEnabled());
    }

    /**
     * Class: ChatMessageFactory
     */
    public static SpannedString hookChatMessage(IChatMessageFactory factory, g chatMessageInterface, SpannedString orgMessage, int channelId) {
        PreferenceManager manager = LoaderLS.getPrefManagerInstance();
        if (!manager.isBttvOn())
            return orgMessage;

        try {
            if (TextUtils.isEmpty(orgMessage))
                return orgMessage;

            if (chatMessageInterface.a())
                return orgMessage;

            return ChatUtil.injectEmotesSpan(factory, LoaderLS.getEmoteMangerInstance(), orgMessage, channelId, manager.getGifsStrategy() == Gifs.DISABLED, manager.getEmoteSize());
        } catch (Throwable th) {
            th.printStackTrace();
        }

        return orgMessage;
    }

    /**
     * Class: LiveChatSource
     */
    public static boolean isJumpSystemIgnore() {
        return LoaderLS.getPrefManagerInstance().isIgnoreSystemMessages();
    }

    /**
     * Class: EmotePickerPresenter
     */
    public static Collection hookEmotePickerSet(Collection list, Integer channelId) {
        if (!LoaderLS.getPrefManagerInstance().isEmotePickerOn())
            return list;

        if (list == null)
            return null;

        EmoteManager emoteManager = LoaderLS.getEmoteMangerInstance();

        Collection<Emote> bttvGlobalEmotes = emoteManager.getGlobalEmotes();
        if (bttvGlobalEmotes != null && !bttvGlobalEmotes.isEmpty()) {
            Integer resId = LoaderLS.getIDPubInstance().getStringId(EmoteSet.GLOBAL.getTitleRes());
            list.add(ChatFactory.getEmoteSetUi(bttvGlobalEmotes, resId));
        }

        if (channelId != null && channelId != -1) {
            Collection<Emote> bttvChannelEmotes = emoteManager.getBttvEmotes(channelId);
            if (bttvChannelEmotes != null && !bttvChannelEmotes.isEmpty()) {
                Integer resId = LoaderLS.getIDPubInstance().getStringId(EmoteSet.BTTV.getTitleRes());
                list.add(ChatFactory.getEmoteSetUi(bttvChannelEmotes, resId));
            }

            Collection<Emote> ffzChannelEmotes = emoteManager.getFfzEmotes(channelId);
            if (ffzChannelEmotes != null && !ffzChannelEmotes.isEmpty()) {
                Integer resId = LoaderLS.getIDPubInstance().getStringId(EmoteSet.FFZ.getTitleRes());
                list.add(ChatFactory.getEmoteSetUi(ffzChannelEmotes, resId));
            }
        }

        return list;
    }

    public static String hookEmoteAdapterItem(Context context, c emoteUiModel) {
        if (emoteUiModel instanceof EmoteUiModelWithUrl) {
            return ((EmoteUiModelWithUrl) emoteUiModel).getUrl();
        }

        return EmoteUrlUtil.getEmoteUrl(context, emoteUiModel.b());
    }
}
