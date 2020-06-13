package dk.via.sharestead.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AuthRepository {
    private FirebaseAuth mAuth;

    private MutableLiveData<String> registerSuccess;
    private MutableLiveData<String> loginSuccess;
    private MutableLiveData<String> resetSuccess;

    private static AuthRepository instance;
    private Application application;

    public AuthRepository(Application application) {
        this.application = application;
        mAuth = FirebaseAuth.getInstance();
        registerSuccess = new MutableLiveData<>();
        loginSuccess = new MutableLiveData<>();
        resetSuccess = new MutableLiveData<>();
    }

    public static AuthRepository getInstance(Application application) {
        if (instance == null) {
            instance = new AuthRepository(application);
        }
        return instance;
    }

    public void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registerSuccess.setValue("valid");
                        registerSuccess = new MutableLiveData<>();

                        FirebaseUser user = mAuth.getCurrentUser();
                        String validEmail = user.getEmail();
                        String validUId = user.getUid();

                        //Store registered user info to database using HashMap
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("email", validEmail);
                        hashMap.put("uid", validUId);
                        hashMap.put("name", "");
                        hashMap.put("image", "");

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("Users");
                        reference.child(validUId).setValue(hashMap);
                    } else {
                        if (task.getException() != null) {
                            registerSuccess.setValue(task.getException().getMessage());
                            registerSuccess = new MutableLiveData<>();
                        }
                    }
                });
    }

    public LiveData<String> getRegisterSuccess() {
        return registerSuccess;
    }

    public void signIn(String email, String password) {
        //Sign in using FirebaseAuth instance
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginSuccess.setValue("valid");
                        loginSuccess = new MutableLiveData<>();
                    } else {
                        if (task.getException() != null) {
                            loginSuccess.setValue(task.getException().getMessage());
                            loginSuccess = new MutableLiveData<>();
                        }
                    }
                });
    }

    public LiveData<String> getLoginSuccess() {
        return loginSuccess;
    }

    public void sendReset(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                resetSuccess.setValue("valid");
                resetSuccess = new MutableLiveData<>();
            } else {
                if (task.getException() != null) {
                    resetSuccess.setValue(task.getException().getMessage());
                    resetSuccess = new MutableLiveData<>();
                }
            }
        });
    }

    public LiveData<String> getResetSuccess() {
        return resetSuccess;
    }
}
