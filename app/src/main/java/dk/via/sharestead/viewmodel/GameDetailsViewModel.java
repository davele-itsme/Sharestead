package dk.via.sharestead.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dk.via.sharestead.model.GameDetails;
import dk.via.sharestead.repository.GameRepository;

public class GameDetailsViewModel extends AndroidViewModel {
    private GameRepository gameRepository;
    private LiveData<GameDetails> gameDetails;
    public GameDetailsViewModel(@NonNull Application application) {
        super(application);
        gameRepository = GameRepository.getInstance(application);
        gameDetails = gameRepository.getGameDetails();
    }


    public void setGameDetails(int id) {
        gameRepository.requestGameDetails(id);
    }

    public LiveData<GameDetails> getGameDetails()
    {
        return gameDetails;
    }
}
