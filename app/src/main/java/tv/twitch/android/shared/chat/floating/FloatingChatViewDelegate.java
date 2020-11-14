package tv.twitch.android.shared.chat.floating;


import tv.twitch.android.mod.bridges.Hooks;


public class FloatingChatViewDelegate {
    /* ... */

    public FloatingChatViewDelegate() {
        /* ... */

        // this.messageCarouselViewDelegate = new CompactChatViewDelegate((LinearLayout) findView(R$id.messages_container), Hooks.getFloatingChatQueueSize());
        int count = Hooks.getFloatingChatQueueSize(); // TODO: __HOOK_ARG

        /* ... */
    }

    /* ... */
}
