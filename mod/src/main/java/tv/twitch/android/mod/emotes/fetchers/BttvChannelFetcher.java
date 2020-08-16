package tv.twitch.android.mod.emotes.fetchers;


import android.text.TextUtils;

import java.util.List;

import retrofit2.Call;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.emotes.BaseEmoteSet;
import tv.twitch.android.mod.models.BttvEmoteModel;
import tv.twitch.android.mod.models.api.BttvChannelResponse;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;


public class BttvChannelFetcher extends ApiCallback<BttvChannelResponse> {
    private final int mChannelId;
    private final Callback mCallback;

    public interface Callback {
        void onBttvEmotesParsed(BaseEmoteSet set);
    }

    public BttvChannelFetcher(int channelId, Callback callback) {
        mChannelId = channelId;
        mCallback = callback;
    }

    @Override
    public void onRequestSuccess(BttvChannelResponse bttvResponse) {
        BaseEmoteSet bttvSet = new BaseEmoteSet();

        List<BttvEmoteResponse> channelEmotes = bttvResponse.getChannelEmotes();
        if (channelEmotes != null) {
            for (BttvEmoteResponse emoticon : channelEmotes) {
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
                bttvSet.addEmote(new BttvEmoteModel(emoticon.getCode(), emoticon.getId(), emoticon.getImageType()));
            }
        }

        List<BttvEmoteResponse> sharedEmotes = bttvResponse.getSharedEmotes();
        if (sharedEmotes != null) {
            for (BttvEmoteResponse emoticon : sharedEmotes) {
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
                bttvSet.addEmote(new BttvEmoteModel(emoticon.getCode(), emoticon.getId(), emoticon.getImageType()));
            }
        }
        mCallback.onBttvEmotesParsed(bttvSet);
        Logger.debug("done!");
    }

    @Override
    public void onRequestFail(Call<BttvChannelResponse> call, FailReason failReason) {
        Logger.debug("channelId=" + mChannelId + ", reason="+failReason.toString());
    }

    @Override
    public void fetch() {
        Logger.info("Fetching bttv emotes for channel: " + mChannelId);
        getBttvApi().getBttvEmotes(mChannelId).enqueue(this);
    }
}
