package tv.twitch.android.mod.chat.fetchers;

import android.text.TextUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import tv.twitch.android.mod.models.api.RobottyResponse;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.models.channel.ChannelInfo;

import static tv.twitch.android.mod.net.ServiceFactory.getRobottyApi;

public final class RobottyFetcher implements Callback<RobottyResponse> {
    private final static int MAX_RETRY_ATTEMPTS = 2;

    private final ChannelInfo mChannel;
    private final int mLimit;
    private final Callback mCallback;

    private int mRetryCount = 0;

    public interface Callback {
        void onMessagesParsed(ChannelInfo channel, List<String> ircMessages);

        void onError(String text);
    }


    public RobottyFetcher(ChannelInfo channel, int limit, Callback callback) {
        mChannel = channel;
        mLimit = limit;
        mCallback = callback;
    }

    @EverythingIsNonNull
    public void onFailure(Call<RobottyResponse> call, Throwable callThrowable) {
        Logger.debug("retryCount=" + mRetryCount);

        if (mRetryCount++ < MAX_RETRY_ATTEMPTS) {
            Logger.debug("Next try...");
            try {
                retry(call);
                return;
            } catch (Throwable cloneThrowable) {
                cloneThrowable.printStackTrace();
            }
        }

        mRetryCount = 0;
        callThrowable.printStackTrace();
        mCallback.onError("Server response error");
    }

    protected final void retry(Call<RobottyResponse> call) {
        call.clone().enqueue(this);
    }

    @EverythingIsNonNull
    public void onResponse(Call<RobottyResponse> call, Response<RobottyResponse> response) {
        if (response.code() == 403) {
            Logger.warning("403 for channel " + mChannel.getName());
            mCallback.onError("Not available for this channel");
            return;
        }

        if (response.code() == 400) {
            Logger.error("400 for channel " + mChannel.getName());
            mCallback.onError("Bad request");
            return;
        }

        RobottyResponse robottyResponse = response.body();
        if (robottyResponse == null) {
            Logger.error("null body");
            mCallback.onError("Unexpected server response");
            return;
        }

        String errorCode = robottyResponse.getErrorCode();
        if (!TextUtils.isEmpty(errorCode)) {
            if (errorCode.equals("channel_not_joined")) {
                if (mRetryCount++ < MAX_RETRY_ATTEMPTS) {
                    retry(call);
                    return;
                }
            }

            Logger.debug("errorCode=" + errorCode + ", error=" + robottyResponse.getError());
            mCallback.onError("Server response error");
            return;
        }

        mCallback.onMessagesParsed(mChannel, robottyResponse.getMessages());
    }

    public void fetch() {
        Logger.info("Fetching robotty messages...");
        getRobottyApi().getPastMessages(mChannel.getName(), mLimit).enqueue(this);
    }
}
