package tv.twitch.android.adapters.social;


import android.content.Context;
import android.graphics.Color;
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
import tv.twitch.android.mod.utils.ViewUtil;
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

    private boolean isHighlighted; // TODO: __INJECT_FIELD

    /* ... */

    public void bindToViewHolder(RecyclerView.ViewHolder viewHolder) {
        /* ... */

        ChatMessageViewHolder chatMessageViewHolder = (ChatMessageViewHolder) viewHolder;
        if (chatMessageViewHolder != null) {
            /* ... */

            maybeHighlightMessage(chatMessageViewHolder.getMessageTextView()); // TODO: __INJECT_CODE
        }

    }

    public void setHighlighted(boolean z) { // TODO: __INJECT_METHOD
        isHighlighted = z;
    }

    private void maybeHighlightMessage(TextView view) { // TODO: __INJECT_METHOD
        if (view == null) {
            Logger.error("view is null");
            return;
        }

        ViewUtil.setBackground(view, isHighlighted ? Color.argb(100, 255, 0, 0) : null);
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
        public ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);
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