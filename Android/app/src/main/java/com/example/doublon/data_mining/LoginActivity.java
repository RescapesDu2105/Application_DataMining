package com.example.doublon.data_mining;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.example.doublon.data_mining.ConnexionAndroid.Client;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import ProtocoleLUGAPM.ReponseLUGAPM;
import ProtocoleLUGAPM.RequeteLUGAPM;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private Client Client = null;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            ReponseLUGAPM Reponse;

            try
            {
                Reponse = contacteServeur(mLogin, mPassword);
                if (Reponse != null)
                {
                    if (Reponse.getCode() == ReponseLUGAPM.LOGIN_OK)
                    {
                        System.out.println("Rep = " + Reponse.getChargeUtile().get("Message"));
                        Client.setNomUtilisateur(Reponse.getChargeUtile().get("Prenom").toString() + " " + (Reponse.getChargeUtile().get("Nom").toString()));
                        isConnected = true;
                    }
                    else
                    {
                        Client.Deconnexion(new RequeteLUGAPM(RequeteLUGAPM.REQUEST_LOG_OUT_RAMP_AGENT));
                    }
                }
                // Simulate network access.
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                return false;
            }

            return isConnected;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success)
            {
                final Intent LanguagePicker = new Intent().setClass(LoginActivity.this, LanguageActivity.class);
                LanguagePicker.putExtra("Client", getClient());
                startActivity(LanguagePicker);
                finish();
            }
            else
            {
                mLoginView.setError(getString(R.string.error_incorrect_login_password));
                mPasswordView.setError(getString(R.string.error_incorrect_login_password));
                mLoginView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public ReponseLUGAPM contacteServeur(String Login, String Psw)
    {
        Client = new Client();

        try
        {
            //Client.setIP(InetAddress.getByName("10.59.14.141"));
            Client.setIP(InetAddress.getByName("192.168.0.3"));
            //Client.setIP(InetAddress.getLocalHost());
            System.out.println("Client = " + Client.getIP());
            Client.setPort(30042);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ReponseLUGAPM Rep = null;
        try
        {
            Rep = (ReponseLUGAPM) Client.Authenfication(new RequeteLUGAPM(RequeteLUGAPM.REQUEST_LOGIN_RAMP_AGENT), Login, Psw);
        }
        catch (IOException | NoSuchAlgorithmException | NoSuchProviderException e)
        {
            e.printStackTrace();
            String Error = "Can't reach the server";
        }

        return Rep;
    }
}

