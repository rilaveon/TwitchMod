package tv.twitch.android.mod.net;


import retrofit2.Call;
import retrofit2.http.GET;
import tv.twitch.android.mod.models.api.FfzBadgesResponse;


public interface FfzApi {
    @GET("/v1/badges/ids")
    Call<FfzBadgesResponse> getBadges();
}
