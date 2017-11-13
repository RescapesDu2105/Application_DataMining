package com.example.philippe.application_dataminingmob.ConnexionServeur;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philippe.application_dataminingmob.R;

import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import ProtocoleLUGANAPM.ReponseLUGANAPM;
import ProtocoleLUGANAPM.RequeteLUGANAPM;

public class LoginActivity extends AppCompatActivity {
    private Client Client = null;

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mLoginView = (AutoCompleteTextView) findViewById(R.id.login);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);

        mLoginView.setText("Zey"); // Test
        mPasswordView.setText("123"); // Test

        // Store values at the time of the login attempt.
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid login
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(login, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public Client getClient() {
        return Client;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mLogin;
        private final String mPassword;

        UserLoginTask(String login, String password) {
            mLogin = login;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean isConnected = false;
            ReponseLUGANAPM Reponse;

            Reponse = contacteServeur(mLogin, mPassword);
            if (Reponse != null)
            {
                if (Reponse.getCode() == ReponseLUGANAPM.LOGIN_OK)
                {
                    System.out.println("Rep = " + Reponse.getChargeUtile().get("Message"));
                    Client.setNomUtilisateur(Reponse.getChargeUtile().get("Prenom").toString() + " " + (Reponse.getChargeUtile().get("Nom").toString()));
                    isConnected = true;
                }
                else
                {
                    Client.Deconnexion(new RequeteLUGANAPM(RequeteLUGANAPM.REQUEST_LOG_OUT_ANALYST));
                }
            }

            return isConnected;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success)
            {
                final Intent SelectingFlightActivity = new Intent().setClass(LoginActivity.this, LoginActivity.class); // Temp
                startActivity(SelectingFlightActivity);
                finish();
            }
            else
            {
                if(Client.isConnectedToServer())
                {
                    mLoginView.setError(getString(R.string.error_incorrect_login_password));
                    mPasswordView.setError(getString(R.string.error_incorrect_login_password));
                    mLoginView.requestFocus();
                }
                else
                {
                    String msg = "Problème de connexion : le serveur est injoignable !";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public ReponseLUGANAPM contacteServeur(String Login, String Psw)
    {
        Client = new Client();
        String[] AddressesIP = {"192.168.0.3", "10.59.22.101", "10.59.14.45" , "10.59.22.9"};

        Client.setPort(30043);
        for(int i = 0 ; i < AddressesIP.length && !Client.isConnectedToServer(); i++)
        {
            try
            {
                Client.setIP(InetAddress.getByName(AddressesIP[i]));
                Client.Connexion();
            }
            catch (IOException e) {}
        }

        ReponseLUGANAPM Rep = null;

        if (Client.isConnectedToServer())
        {
            try {
                Rep = (ReponseLUGANAPM) Client.Authenfication(new RequeteLUGANAPM(RequeteLUGANAPM.REQUEST_LOGIN_ANALYST), Login, Psw);
            } catch (IOException | NoSuchAlgorithmException | NoSuchProviderException e) {
                e.printStackTrace();
                String Error = "Can't reach the server";
            }
        }

        return Rep;
    }
}

