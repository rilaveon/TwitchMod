package tv.twitch.android.mod.emotes.fetchers;


import android.text.TextUtils;

import java.util.List;

import retrofit2.Call;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.emotes.BaseEmoteSet;
import tv.twitch.android.mod.models.FfzEmoteModel;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.models.api.FfzEmoteResponse;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;


public class FfzChannelFetcher extends ApiCallback<List<FfzEmoteResponse>> {
    private final int mId;
    private final Callback mCallback;

    public interface Callback {
        void onFfzEmotesParsed(BaseEmoteSet set);
    }

    public FfzChannelFetcher(int channelId, Callback callback) {
        mId = channelId;
        mCallback = callback;
    }

    @Override
    public void onRequestSuccess(List<FfzEmoteResponse> ffzResponse) {
        BaseEmoteSet ffzSet = new BaseEmoteSet();
        for (FfzEmoteResponse emoteResponse : ffzResponse) {
            if (emoteResponse == null)
                continue;

            if (emoteResponse.getImages() == null)
                continue;

            if (emoteResponse.getImages().isEmpty())
                continue;

            if (TextUtils.isEmpty(emoteResponse.getId()))
                continue;

            FfzEmoteModel emote = new FfzEmoteModel(emoteResponse.getCode(), emoteResponse.getImages());
            ffzSet.addEmote(emote);
        }

        mCallback.onFfzEmotesParsed(ffzSet);
        Logger.debug("done!");
    }

    @Override
    public void onRequestFail(Call<List<FfzEmoteResponse>> call, FailReason failReason) {
        Logger.debug("channelId=" + mId + ", reason="+failReason.toString());
    }

    @Override
    public void fetch() {
        Logger.info("Fetching ffz emotes for channel: " + mId);
        getBttvApi().getFfzEmotes(mId).enqueue(this);
    }
}
