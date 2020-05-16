package dk.via.sharestead.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;


import dk.via.sharestead.R;

public class AuthenticationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);
        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        button = findViewById(R.id.logInBtn);
    }

    public void onLoginBtnClicked(View view)
    {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            showAlertDialog("Email can not be empty.");
            return;
        }
        else if(TextUtils.isEmpty(password))
        {
            showAlertDialog("Password can not be empty.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in successful
                        startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
                    } else {
                        // If sign in fails, display a message to the user.
                        showAlertDialog(task.getException().getMessage());
                    }
                });
    }

    public void onRegisterClicked(View view)
    {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void showAlertDialog(String message)
    {
        new AlertDialog.Builder(this)
                .setTitle("Error with login")
                .setMessage(message)
                .setPositiveButton(
                        "Okay",
                        (dialog, id) -> dialog.cancel())
                .show();
    }
}
