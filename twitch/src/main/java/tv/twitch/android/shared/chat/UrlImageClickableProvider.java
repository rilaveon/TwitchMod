package tv.twitch.android.shared.chat;


import android.text.style.ClickableSpan;


public interface UrlImageClickableProvider {
    ClickableSpan getClickableForUrl(String str);
}