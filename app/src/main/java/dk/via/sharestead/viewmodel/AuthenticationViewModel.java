package dk.via.sharestead.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dk.via.sharestead.repository.AuthRepository;

public class AuthenticationViewModel extends AndroidViewModel {
    private AuthRepository authRepository;

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        authRepository = AuthRepository.getInstance(application);
    }

    public int checkEmptyFields(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            return 1;
        } else if (TextUtils.isEmpty(password)) {
            return 2;
        }
        return 0;
    }

    public void signIn(String email, String password) {
        authRepository.signIn(email, password);
    }

    public LiveData<String> getLoginSuccess() {
        return authRepository.getLoginSuccess();
    }

    public boolean checkEmptyField(String email) {
        if(TextUtils.isEmpty(email))
        {
            return true;
        }
        return false;
    }

    public void sendReset(String email) {
        authRepository.sendReset(email);
    }

    public LiveData<String> getResetSuccess() {
        return authRepository.getResetSuccess();
    }
}
