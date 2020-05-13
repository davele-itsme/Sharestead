package dk.via.sharestead.webservices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GamesAPI {
    //asynchronous calls
    @GET("games?ordering=-rating")
    Call<GamesResponse> getGamesByPreference();

    @GET("games?tags=vr&ordering=-rating")
    Call<GamesResponse> getVRGames();

    @GET("platforms")
    Call<PlatformResponse> getPlatformId();

}
