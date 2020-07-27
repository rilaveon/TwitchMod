package tv.twitch.android.mod.emotes.fetchers;


import android.text.TextUtils;

import java.util.List;

import retrofit2.Call;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.emotes.BaseEmoteSet;
import tv.twitch.android.mod.models.BttvEmoteModel;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;

public class BttvGlobalFetcher extends ApiCallback<List<BttvEmoteResponse>> {
    private final Callback mCallback;

    public interface Callback {
        void globalBttvParsed(BaseEmoteSet set);
    }

    public BttvGlobalFetcher(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onRequestSuccess(List<BttvEmoteResponse> bttvResponse) {
        BaseEmoteSet globalSet = new BaseEmoteSet();

        for (BttvEmoteResponse emoticon : bttvResponse) {
            if (emoticon == null)
                continue;

            if (TextUtils.isEmpty(emoticon.getId())) {
                continue;
            }

            if (TextUtils.isEmpty(emoticon.getCode())) {
                continue;
            }

            if (emoticon.getImageType() == null) {
                continue;
            }

            globalSet.addEmote(new BttvEmoteModel(emoticon.getCode(), emoticon.getId(), emoticon.getImageType()));
        }

        mCallback.globalBttvParsed(globalSet);
        Logger.debug("done!");
    }

    @Override
    public void onRequestFail(Call<List<BttvEmoteResponse>> call, FailReason failReason) {
        Logger.debug("reason="+failReason.toString());
    }

    @Override
    public void fetch() {
        Logger.info("Fetching global emotes...");
        getBttvApi().getGlobalEmotes().enqueue(this);
    }
}
