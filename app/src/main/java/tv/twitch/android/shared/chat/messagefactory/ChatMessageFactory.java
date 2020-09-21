package tv.twitch.android.shared.chat.messagefactory;


import android.text.SpannedString;

import tv.twitch.android.core.mvp.viewdelegate.EventDispatcher;
import tv.twitch.android.core.user.TwitchAccountManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.models.webview.WebViewSource;
import tv.twitch.android.shared.chat.ChatMessageInterface;
import tv.twitch.android.shared.chat.chatsource.IClickableUsernameSpanListener;
import tv.twitch.android.shared.chat.tracking.ChatFiltersSettings;
import tv.twitch.android.shared.chat.util.ChatItemClickEvent;
import tv.twitch.android.shared.ui.elements.span.TwitchUrlSpanClickListener;
import tv.twitch.android.shared.ui.elements.span.MediaSpan$Type;
import tv.twitch.android.shared.chat.UrlImageClickableProvider;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.interfaces.IChatMessageFactory;
import tv.twitch.chat.ChatMessageInfo;


public class ChatMessageFactory implements IChatMessageFactory { // TODO: __IMPLEMENT
    private final TwitchAccountManager accountManager = null;

    /* ... */

    private final CharSequence imageSpannable(String str, MediaSpan$Type mediaSpan$Type, String str2, UrlImageClickableProvider urlImageClickableProvider, boolean z) {
        return null;
    }

    private final CharSequence usernameSpannable(ChatMessageInterface chatMessageInterface, int color, IClickableUsernameSpanListener iClickableUsernameSpanListener, boolean z, String str, String str2) {
        color = Hooks.hookUsernameSpanColor(color); // TODO: __HOOK_PARAM

        /* ... */
        return null;
    }

    public final Object createChatMessageItem(ChatMessageInfo chatMessageInfo, boolean z, boolean z2, boolean z3, int i, int i2, IClickableUsernameSpanListener iClickableUsernameSpanListener, TwitchUrlSpanClickListener twitchUrlSpanClickListener, WebViewSource webViewSource, String str, boolean z4, ChatFiltersSettings chatFiltersSettings, EventDispatcher<ChatItemClickEvent> eventDispatcher) {
        /* ... */

        if (Hooks.isMentionedMessage(chatMessageInfo, accountManager)) {
            // createMentioned
        }


        return null;
    }

    private final ChatMessageSpanGroup createChatMessageSpanGroup(ChatMessageInterface chatMessageInterface, boolean z, boolean z2, boolean z3, int userId, int channelId, IClickableUsernameSpanListener iClickableUsernameSpanListener, TwitchUrlSpanClickListener twitchUrlSpanClickListener, WebViewSource webViewSource, String str, boolean z4, ChatFiltersSettings chatFiltersSettings, Integer num, EventDispatcher<ChatItemClickEvent> eventDispatcher) {
        try {
            /* ... */

            SpannedString parseChatMessageTokens$default = new SpannedString("KEKW");
            SpannedString generateBadges = new SpannedString("LULW");
            parseChatMessageTokens$default = Hooks.hookChatMessage(this, chatMessageInterface, parseChatMessageTokens$default, channelId, accountManager); // TODO: __HOOK
            generateBadges = Hooks.hookBadges(this, chatMessageInterface, generateBadges); // TODO: __HOOK

            /* ... */

            return null;
        } catch (Throwable th) {
            th.printStackTrace();

            return ChatMessageSpanGroup.Companion.getBLANK();
        }
    }

    /* ... */

    @Override
    public CharSequence getSpannedEmote(String url, String emoteText) { // TODO: __INJECT_METHOD
        return imageSpannable(url, MediaSpan$Type.Emote, emoteText, null, false);
    }

    @Override
    public CharSequence getSpannedBadge(String url, String badgeName) { // TODO: __INJECT_METHOD
        return imageSpannable(url, MediaSpan$Type.Badge, badgeName, null, true);
    }
}