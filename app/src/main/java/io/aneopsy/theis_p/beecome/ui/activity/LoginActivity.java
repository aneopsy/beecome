package io.aneopsy.theis_p.beecome.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.aneopsy.theis_p.beecome.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Connexion...");
        progressDialog.show();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 1000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement authentication
                this.finish();
            }
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        int[] startingLocation = new int[2];
        getWindow().findViewById(android.R.id.content).getLocationOnScreen(startingLocation);
        startingLocation[0] += getWindow().findViewById(android.R.id.content).getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
        finish();
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Echec de la connexion", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        if (email.isEmpty() || email.length() < 4) {
            _emailText.setError("Entrez votre identifiant");
            valid = false;
        } else {_emailText.setError(null);}
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Le mot de passe a un format incorrect");
            valid = false;
        } else {_passwordText.setError(null);}
        return valid;
    }
    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        login();
    }
    @OnClick(R.id.link_signup)
    public void onSignupClick() {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }
}
