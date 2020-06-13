package dk.via.sharestead.webservices;

import java.util.Map;

import dk.via.sharestead.model.GameDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface GamesApi {
    @GET("games")
    Call<GamesResponse> getGamesByPreference(@QueryMap Map<String, Object> map);

    @GET("platforms/lists/parents")
    Call<PlatformResponse> getPlatformId();

    @GET("games/{id}")
    Call<GameDetails> getGameDetails(@Path("id") int id);

    @GET("games?tags=vr&ordering=-rating")
    Call<GamesResponse> getVRGames();

    @GET("games?tags=vr&ordering=-rating&page=2")
    Call<GamesResponse> getMoreVRGames();
}
