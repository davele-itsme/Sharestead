package dk.via.sharestead.webservices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GamesAPI {
    //asynchronous calls
    @GET("games")
    Call<List<GamesResponse>> getBestRatedGames(@Query("name") String name);

    @GET("games?ordering=-rating")
    Call<GamesResponse> getBestRatedGames();

    @GET("games?tags=vr&ordering=-rating")
    Call<GamesResponse> getVRGames();

    Call<PlatformResponse> getPlatformId();

}
