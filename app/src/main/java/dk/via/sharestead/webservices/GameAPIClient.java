package dk.via.sharestead.webservices;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.via.sharestead.model.Game;
import dk.via.sharestead.model.GameDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameAPIClient {
    private MutableLiveData<List<Game>> games;
    private MutableLiveData<List<Game>> moreGames;
    private MutableLiveData<GameDetails> gameDetails;
    private GamesApi gamesAPI;
    private Application application;

    public GameAPIClient(Application application) {
        games = new MutableLiveData<>();
        moreGames = new MutableLiveData<>();
        gameDetails = new MutableLiveData<>();
        gamesAPI = ServiceGenerator.getGamesAPI();
        this.application = application;
    }

    public LiveData<List<Game>> getGames() {
        return games;
    }

    public LiveData<List<Game>> getMoreGames() {
        return moreGames;
    }

    public LiveData<GameDetails> getGameDetails() {
        return gameDetails;
    }


    public void requestVRGames() {
        Call<GamesResponse> call = gamesAPI.getVRGames();
        call.enqueue(new Callback<GamesResponse>() {
            @Override
            public void onResponse(Call<GamesResponse> call, Response<GamesResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    games.setValue(response.body().getResults());
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

    public void requestPlatformId(String platform)
    {
        Call<PlatformResponse> call = gamesAPI.getPlatformId();
        call.enqueue(new Callback<PlatformResponse>() {
            @Override
            public void onResponse(Call<PlatformResponse> call, Response<PlatformResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    requestGamesByPreference(response.body().getPlatformId(platform));
                    requestMoreGamesByPreference(response.body().getPlatformId(platform));
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

    public void requestGameDetails(int id) {
        Call<GameDetails> call = gamesAPI.getGameDetails(id);
        call.enqueue(new Callback<GameDetails>() {
            @Override
            public void onResponse(Call<GameDetails> call, Response<GameDetails> response) {
                if (response.code() == 200 && response.body() != null) {
                    gameDetails.setValue(response.body());
                } else {
                    Toast.makeText(application, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GameDetails> call, Throwable t) {
                Toast.makeText(application, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestGamesByPreference(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("parent_platforms", id);
        map.put("ordering", "-added");
        map.put("dates", "2016-09-01,2020-05-01");
        Call<GamesResponse> call = gamesAPI.getGamesByPreference(map);
        call.enqueue(new Callback<GamesResponse>() {
            @Override
            public void onResponse(Call<GamesResponse> call, Response<GamesResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    games.setValue(response.body().getResults());
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

    private void requestMoreGamesByPreference(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("parent_platforms", id);
        map.put("page", 2);
        map.put("dates", "2013-09-01,2020-05-01");
        Call<GamesResponse> call = gamesAPI.getGamesByPreference(map);
        call.enqueue(new Callback<GamesResponse>() {
            @Override
            public void onResponse(Call<GamesResponse> call, Response<GamesResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    moreGames.setValue(response.body().getResults());
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
}
