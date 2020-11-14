package tv.twitch.android.adapters.social;


import android.content.Context;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.subjects.PublishSubject;
import tv.twitch.android.core.mvp.viewdelegate.EventDispatcher;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.interfaces.IChatMessageItem;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.shared.chat.adapter.SystemMessageType;
import tv.twitch.android.shared.chat.adapter.item.ChatAdapterItem;
import tv.twitch.android.shared.chat.adapter.item.ChatMessageClickedEvents;
import tv.twitch.android.shared.chat.util.ChatItemClickEvent;
import tv.twitch.android.shared.chat.util.ChatUtil;


public class MessageRecyclerItem implements ChatAdapterItem {
    private boolean hasModAccess;
    private Spanned message;
    private PublishSubject<ChatMessageClickedEvents> messageClickSubject;
    private final Context context = null;

    public final String rawMessage = null;

    private boolean shouldHighlightBackground; // TODO: __INJECT_FIELD

    /* ... */

    public void bindToViewHolder(RecyclerView.ViewHolder viewHolder) {
        /* ... */

        ChatMessageViewHolder chatMessageViewHolder = (ChatMessageViewHolder) viewHolder;
        if (chatMessageViewHolder != null) {
            /* ... */

            maybeHighlightMessage(chatMessageViewHolder); // TODO: __INJECT_CODE
        }

    }

    public void setShouldHighlightBackground(boolean z) { // TODO: __INJECT_METHOD
        shouldHighlightBackground = z;
    }

    private void maybeHighlightMessage(ChatMessageViewHolder holder) { // TODO: __INJECT_METHOD
        if (holder == null) {
            Logger.error("holder is null");
            return;
        }

        Hooks.highlightView(holder.itemView, shouldHighlightBackground);
    }

    public MessageRecyclerItem(Context context2, String str, int authorUserId, String str2, String str3, int i2, Spanned message, SystemMessageType systemMessageType, float f, int i3, float f2, boolean z, boolean z2, String str4, EventDispatcher<ChatItemClickEvent> eventDispatcher) {
        message = Hooks.addTimestampToMessage(message, authorUserId); // TODO: __HOOK_PARAM

        /* ... */
    }

    public void markAsDeleted() {
        ChatUtil.Companion companion = ChatUtil.Companion;

        /* ... */

        this.message = Hooks.hookMarkAsDeleted(companion, this.message, this.context, this.messageClickSubject, this.hasModAccess); // TODO: __REPLACE_CODE
    }


    public static final class ChatMessageViewHolder extends RecyclerView.ViewHolder implements IChatMessageItem { // TODO: __IMPLEMENT
        private final TextView messageTextView = null;

        /* ... */

        public ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            /* ... */

            Hooks.setChatMessageFontSize(messageTextView); // TODO: __INJECT_CODE
        }

        public final TextView getMessageTextView() {
            return null;
        }

        @Override // TODO: __INJECT_METHOD
        public TextView getTextView() {
            return getMessageTextView();
        }

        /* ... */
    }

    /* ... */
}