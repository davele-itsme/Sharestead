package dk.via.sharestead.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.via.sharestead.model.Game;
import dk.via.sharestead.repository.HomeRepository;


public class HomeViewModel extends AndroidViewModel {
    private LiveData<List<Game>> games;
    private LiveData<List<Game>> moreGames;
    private HomeRepository homeRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = HomeRepository.getInstance(application);
        games = homeRepository.getGames();
        moreGames = homeRepository.getMoreGames();
    }

    public void setPlatformGames(String platformPreference) {
        if (platformPreference.equals("VIRTUAL REALITY")) {
            homeRepository.requestVRGames();
        } else {
            //Set string so that it matches string in webservices and can find the right platform
            String newPlatform = "";
            switch (platformPreference) {
                case "COMPUTER":
                    newPlatform = "PC";
                    break;
                case "CONSOLE":
                    newPlatform = "PlayStation";
                    break;
                case "MOBILE":
                    newPlatform = "iOS";
                    break;
            }

            homeRepository.requestPlatformId(newPlatform);
        }

    }

    public LiveData<List<Game>> getGames() {
        return games;
    }

    public LiveData<List<Game>> getMoreGames() {
        return moreGames;
    }
}
