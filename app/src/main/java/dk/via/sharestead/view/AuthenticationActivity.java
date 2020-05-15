package dk.via.sharestead.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import dk.via.sharestead.R;

public class AuthenticationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText emailField, passwordField;
    private Button loginBtn;
    private TextView registerTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);
        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.logInBtn);
        registerTxt = findViewById(R.id.register);
    }

    public void onLoginBtnClicked(View view)
    {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Error with login", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
