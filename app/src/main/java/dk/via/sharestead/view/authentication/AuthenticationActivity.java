package dk.via.sharestead.view.authentication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


import dk.via.sharestead.R;
import dk.via.sharestead.view.PreferenceActivity;
import dk.via.sharestead.view.dialog.ProgressDialog;

public class AuthenticationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;
    private final String TAG = "ProgressBar";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        progressDialog = new ProgressDialog();

    }

    public void onLoginBtnClicked(View view) {
        //Show progress bar
        progressDialog.show(getSupportFragmentManager(), TAG);
        //Getting strings from fields
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        //To clear error if user clicks on button again
        TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
        passwordLayout.setError(null);

        //If statements for empty field cases
        if (TextUtils.isEmpty(email)) {
            emailField.setError(getResources().getString(R.string.empty_email));
            progressDialog.dismiss();
            return;
        } else if (TextUtils.isEmpty(password)) {
            passwordLayout.setError(getResources().getString(R.string.empty_password));
            progressDialog.dismiss();
            return;
        }

        //Sign in using FirebaseAuth instance
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in successful
                        startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
                    } else {
                        // If sign in fails, display a message to the user.
                        if (task.getException() != null) {
                            showAlertDialog(task.getException().getMessage());
                        }
                    }
                });
    }

    public void onRegisterClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    //Method for recovering using email
    public void onForgetPasswordClicked(View view) {
        //Setting view
        LinearLayout linearLayout = new LinearLayout(this);
        EditText emailField = new EditText(this);
        emailField.setHint(getResources().getString(R.string.hint_email));
        emailField.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        emailField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailField);
        linearLayout.setPadding(50, 10, 50, 10);

        //Creating Dialog with above mentioned view
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.recover_dialog_title))
                .setView(linearLayout)
                .setPositiveButton(getResources().getString(R.string.recover_positive_button), (dialog, which) -> {
                    if (!emailField.getText().toString().isEmpty()) {
                        String email = emailField.getText().toString().trim();
                        sendRecovery(email);
                    } else {
                        emailField.setError(getResources().getString(R.string.empty_email));
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.login_dialog_title))
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.okay), (dialog, id) ->
                        dialog.cancel())
                .show();
    }

    private void sendRecovery(String email) {
        progressDialog.show(getSupportFragmentManager(), TAG);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(this, getResources().getString(R.string.recovery_email_successful), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.recovery_email_unsuccessful), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}
