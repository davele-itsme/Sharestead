package dk.via.sharestead.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.via.sharestead.model.Game;
import dk.via.sharestead.webservices.GamesAPI;
import dk.via.sharestead.webservices.GamesResponse;
import dk.via.sharestead.webservices.PlatformResponse;
import dk.via.sharestead.webservices.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private MutableLiveData<List<Game>> games;
    private static HomeRepository instance;
    private Application application;
    private GamesAPI gamesAPI;

    public HomeRepository(Application application) {
        games = new MutableLiveData<>();
        this.application = application;
         gamesAPI = ServiceGenerator.getGamesAPI();
    }

    public static HomeRepository getInstance(Application application) {
        if (instance == null) {
            instance = new HomeRepository(application);
        }
        return instance;
    }

    public void requestVRGames() {

        Call<GamesResponse> call = gamesAPI.getVRGames();
        call.enqueue(new Callback<GamesResponse>() {
            @Override
            public void onResponse(Call<GamesResponse> call, Response<GamesResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    games.setValue(response.body().getGames());
                } else {
                    Toast.makeText(application, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GamesResponse> call, Throwable t) {
                Toast.makeText(application, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestPlatformId(String platform) {
        Call<PlatformResponse> call = gamesAPI.getPlatformId();
        call.enqueue(new Callback<PlatformResponse>() {
            @Override
            public void onResponse(Call<PlatformResponse> call, Response<PlatformResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                   requestGamesByPreference(response.body().getPlatformId(platform));
                   Toast.makeText(application, "WORKS: " + response.body().getPlatformId(platform), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(application, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlatformResponse> call, Throwable t) {
                Toast.makeText(application, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestGamesByPreference(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("platforms", id);
        map.put("ordering", "-added");
        map.put("dates", "2016-09-01,2020-09-30");
        Call<GamesResponse> call = gamesAPI.getGamesByPreference(map);
        call.enqueue(new Callback<GamesResponse>() {
            @Override
            public void onResponse(Call<GamesResponse> call, Response<GamesResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    games.setValue(response.body().getGames());
                } else {
                    Toast.makeText(application, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GamesResponse> call, Throwable t) {
                Toast.makeText(application, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<List<Game>> getGames() {
        return games;
    }


}

   
