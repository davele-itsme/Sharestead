package dk.via.sharestead.view.authentication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import dk.via.sharestead.R;
import dk.via.sharestead.view.authentication.AuthenticationActivity;
import dk.via.sharestead.view.dialog.ProgressDialog;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailField, passwordField, repeatPasswordField;
    private FirebaseAuth mAuth;
    private final String TAG = "ProgressBar";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        repeatPasswordField = findViewById(R.id.repeatPasswordField);
        progressDialog = new ProgressDialog();
    }

    public void onRegisterBtnClicked(View view) {
        //Getting strings from fields
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String repeatPassword = repeatPasswordField.getText().toString().trim();

        //Display progress bar
        progressDialog.show(getSupportFragmentManager(), TAG);

        //Set text error to TextInputLayout as it does not disappear automatically
        TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
        TextInputLayout repeatPasswordLayout = findViewById(R.id.passwordLayoutRepeat);
        repeatPasswordLayout.setError(null);
        passwordLayout.setError(null);

        //If statements for empty field cases
        if (TextUtils.isEmpty(email)) {
            emailField.setError((getResources().getString(R.string.empty_email)));
            progressDialog.dismiss();
            return;
        } else if (TextUtils.isEmpty(password)) {
            passwordLayout.setError((getResources().getString(R.string.empty_password)));
            progressDialog.dismiss();
            return;
        } else if (!password.equals(repeatPassword)) {
            repeatPasswordLayout.setError((getResources().getString(R.string.password_not_match)));
            progressDialog.dismiss();
            return;
        }

        //Register using FirebaseAuth instance
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {

                        //Geting data from FireBaseUser
                        FirebaseUser user = mAuth.getCurrentUser();
                        String validEmail = user.getEmail();
                        String validUId = user.getUid();

                        //Store registered user info to database using HashMap
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("email", validEmail);
                        hashMap.put("uid", validUId);
                        hashMap.put("name", "");
                        hashMap.put("image", "");

                        //Storing it to firebase database instance
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("Users");
                        reference.child(validUId).setValue(hashMap);


                        // Sign in success, getting back to the activity and close this one
                        startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
                        Toast.makeText(this, getResources().getString(R.string.register_account_successful), Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        if (task.getException() != null) {
                            showAlertDialog(task.getException().getMessage());
                        }
                    }
                });
    }

    public void onLoginClicked(View view) {
        startActivity(new Intent(this, AuthenticationActivity.class));
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.login_dialog_title))
                .setMessage(message)
                .setPositiveButton(
                        getResources().getString(R.string.okay),
                        (dialog, id) -> dialog.cancel())
                .show();
    }
}
