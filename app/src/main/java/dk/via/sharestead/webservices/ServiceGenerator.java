package dk.via.sharestead.webservices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Generating Web API using Retrofit
public class ServiceGenerator {
    private static final String BASE_URL = "https://api.rawg.io/api/";
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static GamesApi gamesAPI = retrofit.create(GamesApi.class);

    public static GamesApi getGamesAPI() {
        return gamesAPI;
    }
}
