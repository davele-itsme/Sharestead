package dk.via.sharestead.webservices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Generating Web API using Retrofit
public class ServiceGenerator {
    private static ServiceGenerator instance;
    private GamesAPI gamesAPI;
    private static final String BASE_URL = "https://api.rawg.io/api/";

    public ServiceGenerator() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gamesAPI = retrofit.create(GamesAPI.class);
    }

    public static ServiceGenerator getInstance()
    {
        if(instance == null)
        {
            instance = new ServiceGenerator();
        }
        return instance;
    }

    public Call<GamesResponse> getGames(){
        return gamesAPI.getGames();
    }

}
