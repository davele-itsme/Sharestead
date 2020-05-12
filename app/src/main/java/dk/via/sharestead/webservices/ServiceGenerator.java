package dk.via.sharestead.webservices;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Generating Web API using Retrofit
public class ServiceGenerator {
    private static final String rawgBaseUrl = "https://api.rawg.io/api";
    private static final int LIMIT = 50;

    private MutableLiveData<List<GameResponse>> gameList = new MutableLiveData<>();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(rawgBaseUrl).addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = retrofitBuilder.build();
    private static GamesAPI gamesAPI = retrofit.create(GamesAPI.class);

    public static GamesAPI getGamesAPI() {
        return gamesAPI;
    }

    public void requestGames(String name) {
        GamesAPI gamesAPI = ServiceGenerator.getGamesAPI();
        Call<List<GameResponse>> call = gamesAPI.getGames(name);
        call.enqueue(new Callback<List<GameResponse>>() {
            @Override
            public void onResponse(Call<List<GameResponse>> call, Response<List<GameResponse>> response) {
                if(response.code() == 200)
                {
                    gameList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GameResponse>> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong");
            }
        });
    }
}
