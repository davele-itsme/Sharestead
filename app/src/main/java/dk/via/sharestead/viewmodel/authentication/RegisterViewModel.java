package dk.via.sharestead.viewmodel.authentication;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dk.via.sharestead.repository.AuthRepository;

public class RegisterViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    public RegisterViewModel(@NonNull Application application) {
        super(application);
        authRepository = AuthRepository.getInstance(application);
    }

    public int checkEmptyFields(String email, String password, String repeatPassword) {
        if (TextUtils.isEmpty(email)) {
            return 1;
        } else if (TextUtils.isEmpty(password)) {
            return 2;
        } else if (!password.equals(repeatPassword)) {
            return 3;
        }
        return 0;
    }

    public void createUser(String email, String password) {
        authRepository.createUser(email, password);
    }

    public LiveData<String> getRegisterSuccess() {
        return authRepository.getRegisterSuccess();
    }
}
