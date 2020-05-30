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
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String repeatPassword = repeatPasswordField.getText().toString().trim();
        progressDialog.show(getSupportFragmentManager(), TAG);
        TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
        TextInputLayout repeatPasswordLayout = findViewById(R.id.passwordLayoutRepeat);
        repeatPasswordLayout.setError(null);
        passwordLayout.setError(null);

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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
                        Toast.makeText(this, getResources().getString(R.string.register_account_successful), Toast.LENGTH_SHORT).show();
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
                .setTitle("Error with login")
                .setMessage(message)
                .setPositiveButton(
                        "Okay",
                        (dialog, id) -> dialog.cancel())
                .show();
    }
}
