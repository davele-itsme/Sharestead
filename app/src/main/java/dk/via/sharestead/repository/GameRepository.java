package dk.via.sharestead.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import java.util.List;

import dk.via.sharestead.model.Game;
import dk.via.sharestead.model.GameDetails;
import dk.via.sharestead.webservices.GameAPIClient;

public class GameRepository {
    private static GameRepository instance;
    private Application application;
    private GameAPIClient gameAPIClient;

    public GameRepository(Application application) {
        this.application = application;
        gameAPIClient = new GameAPIClient(application);
    }

    public static GameRepository getInstance(Application application) {
        if (instance == null) {
            instance = new GameRepository(application);
        }
        return instance;
    }

    public LiveData<List<Game>> getGames() {
        return gameAPIClient.getGames();
    }

    public LiveData<List<Game>> getMoreGames() {
        return gameAPIClient.getMoreGames();
    }

    public LiveData<GameDetails> getGameDetails() {
        return gameAPIClient.getGameDetails();
    }

    public void requestVRGames() {
        gameAPIClient.requestVRGames();
        gameAPIClient.requestMoreVRGames();
    }

    public void requestPlatformId(String platform) {
        gameAPIClient.requestPlatformId(platform);
    }

    public void requestGameDetails(int id) {
       gameAPIClient.requestGameDetails(id);
    }
}

   
