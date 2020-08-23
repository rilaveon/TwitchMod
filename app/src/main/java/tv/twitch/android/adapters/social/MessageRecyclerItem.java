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
import tv.twitch.android.shared.chat.adapter.SystemMessageType;
import tv.twitch.android.shared.chat.adapter.item.ChatMessageClickedEvents;
import tv.twitch.android.shared.chat.util.ChatItemClickEvent;
import tv.twitch.android.shared.chat.util.ChatUtil;


public class MessageRecyclerItem implements IChatMessageItem { // TODO: __IMPLEMENT
    private boolean hasModAccess;
    private Spanned message;
    private PublishSubject<ChatMessageClickedEvents> messageClickSubject;
    private Context context;


    public String rawMessage;


    public MessageRecyclerItem(Context context2, String str, int i, String str2, String str3, int i2, Spanned message, SystemMessageType systemMessageType, float f, int i3, float f2, boolean z, boolean z2, String str4, EventDispatcher<ChatItemClickEvent> eventDispatcher) {
        message = Hooks.addTimestampToMessage(message); // TODO: __HOOK_PARAM
    }

    public void markAsDeleted() {
        ChatUtil.Companion companion = ChatUtil.Companion;
        /* ... */

        this.message = Hooks.hookMarkAsDeleted(companion, this.message, this.context, this.messageClickSubject, this.hasModAccess); // TODO: __INJECT_CODE
    }

    @Override
    public Spanned getSpanned() { // TODO: __INJECT_METHOD
        return message;
    }

    public static final class ChatMessageViewHolder extends RecyclerView.ViewHolder implements IChatMessageItem { // TODO: __IMPLEMENT
        public ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public final TextView getMessageTextView() {
            return null;
        }

        @Override
        public Spanned getSpanned() { // TODO: __INJECT_METHOD
            if (getMessageTextView() != null)
                return (Spanned) getMessageTextView().getText();

            return null;
        }
    }

}