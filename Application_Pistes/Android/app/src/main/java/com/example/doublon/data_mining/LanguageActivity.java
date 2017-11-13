package com.example.doublon.data_mining;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.widget.Toast;

import com.example.doublon.data_mining.ConnexionServeur.Client;

import java.util.Locale;

import ProtocoleLUGAPM.RequeteLUGAPM;

public class LanguageActivity extends AppCompatActivity
{
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Button bConfirmer = findViewById(R.id.confirm_button);
        bConfirmer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Locale locale = null;
                int Choix = 0;

                if(((RadioButton)findViewById(R.id.radioButton_french)).isChecked())
                    Choix = 1;
                else if (((RadioButton)findViewById(R.id.radioButton_english)).isChecked())
                    Choix = 2;
                else if (((RadioButton)findViewById(R.id.radioButton_dutch)).isChecked())
                    Choix = 3;

                System.out.println("Choix = " + Choix);

                switch (Choix)
                {
                    case 0:
                        break;
                    case 1:
                        locale = new Locale("fr_BE");
                        break;
                    case 2:
                        locale = new Locale("en_US");
                        break;
                    case 3:
                        locale = new Locale("nl_NL");
                        break;
                    default: break;
                }

                System.out.println("Langue 0 = " + getResources().getConfiguration().locale);
                getBaseContext().getResources().getConfiguration().setLocale(locale);
                System.out.println("Langue 1 = " + getResources().getConfiguration().locale);
                getBaseContext().getResources().updateConfiguration(getResources().getConfiguration(), getBaseContext().getResources().getDisplayMetrics());

                if (Choix == 0)
                {
                    String msg = "Veuillez selectionner une langue";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
                else
                {
                    final Intent SelectingFlightActivity = new Intent().setClass(LanguageActivity.this, SelectingFlightActivity.class);
                    startActivity(SelectingFlightActivity);
                    finish();
                }
            }
        });
    }

// Tentative de deconnexion au serveur avant de fermer l'application
    /*@Override
    protected void onDestroy() {
        System.out.println("Destroy");
        DeconnexionServeurTask DST = new DeconnexionServeurTask();
        DST.execute();

        super.onDestroy();
    }

    public class DeconnexionServeurTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... voids)
        {
            Client.Deconnexion(new RequeteLUGAPM(RequeteLUGAPM.REQUEST_LOG_OUT_RAMP_AGENT));

            return true;
        }

    }

    @Override
    protected void onStop() {
        System.out.println("Stop");
        super.onStop();
    }*/

// Tentative de changement de langue
    /*@SuppressWarnings("deprecation")
    public Locale getSystemLocaleLegacy(Configuration config){
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public Locale getSystemLocale(Configuration config){
        return config.getLocales().get(0);
    }

    @SuppressWarnings("deprecation")
    public void setSystemLocaleLegacy(Configuration config, Locale locale){
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void setSystemLocale(Configuration config, Locale locale){
        config.setLocale(locale);
    }

    public void setLanguage(Context context, String languageCode){
        System.out.println("languageCode = " + languageCode);
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale);
        }else{
            setSystemLocaleLegacy(config, locale);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            context.getApplicationContext().getResources().updateConfiguration(config,
                    context.getResources().getDisplayMetrics());
    }*/
}
