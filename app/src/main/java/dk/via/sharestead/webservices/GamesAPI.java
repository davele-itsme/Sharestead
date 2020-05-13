package dk.via.sharestead.webservices;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GamesAPI {
    //asynchronous calls
    @GET("games")
    Call<GamesResponse> getGamesByPreference(@QueryMap Map<String, Object> map);

    @GET("games?tags=vr&ordering=-rating")
    Call<GamesResponse> getVRGames();

    @GET("platforms/lists/parents")
    Call<PlatformResponse> getPlatformId();

}
