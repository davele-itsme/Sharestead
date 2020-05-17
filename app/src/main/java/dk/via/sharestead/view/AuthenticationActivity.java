package dk.via.sharestead.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import dk.via.sharestead.R;

public class AuthenticationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);
        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        progressBar = findViewById(R.id.progressBarRecovery);
        progressBar.setVisibility(View.GONE);
    }

    public void onLoginBtnClicked(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            showAlertDialog("Email can not be empty.");
            return;
        } else if (TextUtils.isEmpty(password)) {
            showAlertDialog("Password can not be empty.");

            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Sign in successful
                        startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
                    } else {
                        // If sign in fails, display a message to the user.
                        if (task.getException().getMessage() != null) {
                            showAlertDialog(task.getException().getMessage());
                        }
                    }
                });
    }

    public void onRegisterClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void onForgetPasswordClicked(View view) {
        LinearLayout linearLayout = new LinearLayout(this);
        EditText emailField = new EditText(this);
        emailField.setHint("Email");
        emailField.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        emailField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailField);
        linearLayout.setPadding(50, 10, 50, 10);

        new AlertDialog.Builder(this)
                .setTitle("Recover password")
                .setView(linearLayout)
                .setPositiveButton("Recover", (dialog, which) -> {
                    if(!emailField.getText().toString().isEmpty())
                    {
                        String email = emailField.getText().toString().trim();
                        sendRecovery(email);
                    }
                    else {
                        Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error with login")
                .setMessage(message)
                .setPositiveButton("Okay", (dialog, id) ->
                        dialog.cancel())
                .show();
    }

    private void sendRecovery(String email) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                Toast.makeText(this, "Recovery email was sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "An error occurred. Please, try again later.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}
