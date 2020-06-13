package dk.via.sharestead.view.authentication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import dk.via.sharestead.R;
import dk.via.sharestead.view.preference.PreferenceActivity;
import dk.via.sharestead.view.dialog.ProgressDialog;
import dk.via.sharestead.viewmodel.authentication.AuthenticationViewModel;

public class AuthenticationActivity extends AppCompatActivity {
    private EditText emailField, passwordField;
    private final String TAG = "Progress dialog";
    private ProgressDialog progressDialog;
    private TextInputLayout passwordLayout;
    private AuthenticationViewModel authenticationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);

        checkInternetPermission();

        authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        setLayout();
        progressDialog = new ProgressDialog();
    }

    public void onLoginBtnClicked(View view) {
        progressDialog.show(getSupportFragmentManager(), TAG);

        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        passwordLayout.setError(null);

        int validation = authenticationViewModel.checkEmptyFields(email, password);

        authenticationViewModel.getLoginSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String success) {
                if(success.equals("valid"))
                {
                    startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
                    finish();
                }
                else if (!success.equals("")){
                    showAlertDialog(success);
                }
                progressDialog.dismiss();
                authenticationViewModel.getLoginSuccess().removeObserver(this);
            }
        });

        switch (validation) {
            case 1:
                emailField.setError((getResources().getString(R.string.empty_email)));
                progressDialog.dismiss();
                break;
            case 2:
                passwordLayout.setError((getResources().getString(R.string.empty_password)));
                progressDialog.dismiss();
                break;
            default:
                authenticationViewModel.signIn(email, password);
        }
    }

    public void onRegisterClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void onForgetPasswordClicked(View view) {
        LinearLayout linearLayout = new LinearLayout(this);
        EditText emailFieldRecovery = new EditText(this);
        emailFieldRecovery.setHint(getResources().getString(R.string.hint_email));
        emailFieldRecovery.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        emailFieldRecovery.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailFieldRecovery);
        linearLayout.setPadding(50, 10, 50, 10);

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.recover_dialog_title))
                .setView(linearLayout)
                .setPositiveButton(getResources().getString(R.string.recover_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = emailFieldRecovery.getText().toString().trim();
                        if (!authenticationViewModel.checkEmptyField(email)) {
                            sendRecovery(email);
                        } else {
                            showAlertDialog(getResources().getString(R.string.empty_email));
                        }
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
        authenticationViewModel.getResetSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String success) {

                if(success.equals("valid"))
                {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.recovery_email_successful), Toast.LENGTH_SHORT).show();
                }
                else if (!success.equals("")){
                    showAlertDialog(success);
                }
                progressDialog.dismiss();
                authenticationViewModel.getResetSuccess().removeObserver(this);

            }
        });
        authenticationViewModel.sendReset(email);
    }

    private void checkInternetPermission() {

    }

    private void setLayout()
    {
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        passwordLayout = findViewById(R.id.passwordLayout);
    }
}
