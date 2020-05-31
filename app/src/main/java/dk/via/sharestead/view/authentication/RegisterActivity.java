package dk.via.sharestead.view.authentication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import dk.via.sharestead.R;
import dk.via.sharestead.view.dialog.ProgressDialog;
import dk.via.sharestead.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailField, passwordField, repeatPasswordField;
    private FirebaseAuth mAuth;
    private final String TAG = "ProgressBar";
    private ProgressDialog progressDialog;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        setEditTexts();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog();
    }

    private void setEditTexts() {
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        repeatPasswordField = findViewById(R.id.repeatPasswordField);
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

        int validation = registerViewModel.checkEmptyFields(email, password, repeatPassword);

        registerViewModel.getRegisterSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String success) {
                if (success.equals("valid")) {
                    // Sign in success, getting back to the activity and close this one
                    startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_account_successful), Toast.LENGTH_LONG).show();
                    finish();
                }  else if (!success.equals("")){
                    showAlertDialog(success);
                }
                progressDialog.dismiss();
                registerViewModel.getRegisterSuccess().removeObserver(this);
            }
        });

        //Switch case for validation received from viw model
        switch (validation) {
            case 1:
                emailField.setError((getResources().getString(R.string.empty_email)));
                progressDialog.dismiss();
                break;
            case 2:
                passwordLayout.setError((getResources().getString(R.string.empty_password)));
                progressDialog.dismiss();
                break;
            case 3:
                repeatPasswordLayout.setError((getResources().getString(R.string.password_not_match)));
                progressDialog.dismiss();
                break;
            default:
                //If none from above is true, create user with email and password
                registerViewModel.createUser(email, password);
        }
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
