package dk.via.sharestead.webservices;

import androidx.lifecycle.LiveData;

import java.util.List;

import dk.via.sharestead.model.Game;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GamesAPI {
    //asynchronous calls
    @GET("/games")
    Call<List<GameResponse>> getGames(@Query("name") String name);
}
