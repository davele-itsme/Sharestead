package dk.via.sharestead.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import dk.via.sharestead.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository profileRepository;

    public ProfileViewModel(Application application)
    {
        super(application);
        profileRepository = ProfileRepository.getInstance(application);
    }


    public void setText() {
        profileRepository.setText();
    }

    public LiveData<String[]> getTextLiveData() {
        return profileRepository.getTextLiveData();
    }

    public void uploadImageWithUri(Uri uri) {
        profileRepository.uploadImageWithUri(uri);
    }

    public LiveData<String> getUpdateImage() {
        return profileRepository.getUpdateImage();
    }

    public void changeName(String name) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name.trim());
        profileRepository.changeName(hashMap);
    }

    public LiveData<Boolean> getUpdateName() {
        return profileRepository.getUpdateName();
    }
}
