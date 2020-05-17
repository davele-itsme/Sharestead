package dk.via.sharestead.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.via.sharestead.model.Game;
import dk.via.sharestead.model.GameDetails;
import dk.via.sharestead.webservices.GamesAPI;
import dk.via.sharestead.webservices.GamesDetailsResponse;
import dk.via.sharestead.webservices.GamesResponse;
import dk.via.sharestead.webservices.PlatformResponse;
import dk.via.sharestead.webservices.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameRepository {
    private MutableLiveData<List<Game>> games;
    private MutableLiveData<List<Game>> moreGames;
    private MutableLiveData<GameDetails> gameDetails;
    private static GameRepository instance;
    private Application application;
    private GamesAPI gamesAPI;

    public GameRepository(Application application) {
        games = new MutableLiveData<>();
        moreGames = new MutableLiveData<>();
        gameDetails = new MutableLiveData<>();
        this.application = application;
        gamesAPI = ServiceGenerator.getGamesAPI();
    }

    public static GameRepository getInstance(Application application) {
        if (instance == null) {
            instance = new GameRepository(application);
        }
        return instance;
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
        Call<GamesDetailsResponse> call = gamesAPI.getGameDetails(id);
        call.enqueue(new Callback<GamesDetailsResponse>() {
            @Override
            public void onResponse(Call<GamesDetailsResponse> call, Response<GamesDetailsResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                        gameDetails.setValue(response.body().getGameDetails());
                } else {
                    Toast.makeText(application, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GamesDetailsResponse> call, Throwable t) {
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
                    moreGames.setValue(response.body().getGames());
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

   
