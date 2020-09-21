package tv.twitch.android.mod.net;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tv.twitch.android.mod.models.api.RobottyResponse;


public interface RobottyApi {
    @GET("/api/v2/recent-messages/{username}?hide_moderation_messages=false&hide_moderated_messages=true")
    Call<RobottyResponse> getPastMessages(@Path("username") String userName, @Query("limit") int limit);
}
