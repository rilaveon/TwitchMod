package tv.twitch.android.mod.chat.fetchers;

import android.text.TextUtils;

import java.util.List;

import retrofit2.Call;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.models.api.RobottyResponse;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.models.channel.ChannelInfo;

import static tv.twitch.android.mod.net.ServiceFactory.getRobottyApi;

public final class RobottyFetcher extends ApiCallback<RobottyResponse> {
    private final ChannelInfo mChannel;
    private final int mLimit;
    private final Callback mCallback;

    public interface Callback {
        void onRobbotyMessagesParsed(ChannelInfo channel, List<String> ircMessages);

        void onError(String text);
    }


    public RobottyFetcher(ChannelInfo channel, int limit, Callback callback) {
        mChannel = channel;
        mLimit = limit;
        mCallback = callback;
    }

    @Override
    public void onRequestSuccess(RobottyResponse robottyResponse) {
        if (!TextUtils.isEmpty(robottyResponse.getError())) {
            Logger.error(robottyResponse.getError());
            mCallback.onError(robottyResponse.getError());
            return;
        }

        mCallback.onRobbotyMessagesParsed(mChannel, robottyResponse.getMessages());
    }

    @Override
    public void onRequestFail(Call<RobottyResponse> call, FailReason failReason) {
        Logger.debug("onRequestFail");
    }

    @Override
    public void fetch() {
        Logger.info("Fetching robotty messages...");
        getRobottyApi().getPastMessages(mChannel.getName(), mLimit).enqueue(this);
    }
}
