package tv.twitch.android.feature.theatre.common;


import tv.twitch.android.mod.bridges.Hooks;

public class FloatingChatPresenter$2 {
    // return this.this$0.chatConnectionController.observeMessagesReceived().throttleLatest((long) Hooks.getFloatingChatRefresh(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).map(new Function<T, R>(this) {
    long delay = Hooks.getFloatingChatRefresh(); // TODO: __HOOK_ARG
}
