package dk.via.sharestead.webservices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GamesAPI {
    //asynchronous calls
    @GET("/games")
    Call<List<GamesResponse>> getGames(@Query("name") String name);

    @GET("games")
    Call<GamesResponse> getGames();
}
