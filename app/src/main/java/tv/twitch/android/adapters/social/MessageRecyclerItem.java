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
import tv.twitch.android.shared.chat.adapter.SystemMessageType;
import tv.twitch.android.shared.chat.adapter.item.ChatMessageClickedEvents;
import tv.twitch.android.shared.chat.util.ChatItemClickEvent;
import tv.twitch.android.shared.chat.util.ChatUtil;


public class MessageRecyclerItem implements IChatMessageItem { // TODO: __IMPLEMENT
    private boolean hasModAccess;
    private Spanned message;
    private PublishSubject<ChatMessageClickedEvents> messageClickSubject;
    private final Context context = null;

    public final String rawMessage = null;

    private boolean isMentioned; // TODO: __INJECT_FIELD

    /* ... */


    public static final class Companion {


        public final MessageRecyclerItem createMentioned(Context context, String str, int i, String str2, String str3, int i2, Spanned spanned, boolean z, boolean z2, String str4, EventDispatcher<ChatItemClickEvent> eventDispatcher) { // TODO: __INJECT
            MessageRecyclerItem item = create(context, str, i, str2, str3, i2, spanned, z, z2, str4, eventDispatcher);
            if (item != null)
                item.setMentioned(true);
            return item;
        }

        public final MessageRecyclerItem create(Context context, String str, int i, String str2, String str3, int i2, Spanned spanned, boolean z, boolean z2, String str4, EventDispatcher<ChatItemClickEvent> eventDispatcher) {
            return null;
        }
    }

    public void bindToViewHolder(RecyclerView.ViewHolder viewHolder) {
        /* ... */

        ChatMessageViewHolder chatMessageViewHolder = (ChatMessageViewHolder) viewHolder;
        if (chatMessageViewHolder != null) {
            /* ... */


            setViewBackground(chatMessageViewHolder.getMessageTextView()); // TODO: __INJECT_CODE
        }

    }

    private void setMentioned(boolean z) { // TODO: __INJECT_METHOD
        isMentioned = z;
    }

    private void setViewBackground(TextView view) { // TODO: __INJECT_METHOD
        if (view == null) {
            Logger.error("view is null");
            return;
        }

        if (isMentioned)
            view.setBackgroundColor(Color.argb(100, 255, 0, 0));
        else
            view.setBackground(null);
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

    @Override
    public Spanned getSpanned() { // TODO: __INJECT_METHOD
        return message;
    }

    @Override
    public void clearTextView() {} // TODO: __INJECT_METHOD

    public static final class ChatMessageViewHolder extends RecyclerView.ViewHolder implements IChatMessageItem { // TODO: __IMPLEMENT
        public ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public final TextView getMessageTextView() {
            return null;
        }



        @Override
        public Spanned getSpanned() { // TODO: __INJECT_METHOD
            TextView textView = getMessageTextView();
            if (textView != null)
                return (Spanned) textView.getText();

            return null;
        }

        @Override
        public void clearTextView() { // TODO: __INJECT_METHOD
            TextView textView = getMessageTextView();
            if (textView != null)
                textView.setText(null);
        }

        /* ... */
    }

    /* ... */
}