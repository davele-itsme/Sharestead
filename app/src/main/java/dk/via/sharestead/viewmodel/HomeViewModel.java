package dk.via.sharestead.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dk.via.sharestead.model.Game;
import dk.via.sharestead.repository.HomeRepository;
import dk.via.sharestead.webservices.GamesResponse;

public class HomeViewModel extends AndroidViewModel {
    private HomeRepository homeRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = HomeRepository.getInstance(application);
    }

    public LiveData<List<Game>> getGames() {
        return homeRepository.getGames();
    }

}
