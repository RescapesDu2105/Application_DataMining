package com.example.doublon.data_mining;

import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.doublon.data_mining.ConnexionAndroid.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import ProtocoleLUGAPM.*;

public class MainActivity extends AppCompatActivity
{
    private Client Client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Button button = (Button) this.findViewById(R.id.OkButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.LoginPlainText);
                text.setHintTextColor(Color.parseColor("#000000"));
                String valueLogin = text.getText().toString();
                text = (EditText) findViewById(R.id.pswPassword);
                text.setHintTextColor(Color.parseColor("#000000"));
                String valuePsw = text.getText().toString();

                if (!valueLogin.equals("")) {
                    contacteServeur(valueLogin, valuePsw);
                } else {
                    text = (EditText) findViewById(R.id.LoginPlainText);
                    text.setHintTextColor(Color.parseColor("#B9121B"));
                    text.setHint("Erreur");
                }

            }
        });
    }

    public void contacteServeur(String Login,String Psw)
    {
        this.Client = new Client();

        try
        {
            this.Client.setIP(InetAddress.getByName("192.168.0.3"));
            this.Client.setPort(30042);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ReponseLUGAPM Rep = null;
        try
        {
            Rep = (ReponseLUGAPM) this.Client.Authenfication(new RequeteLUGAPM(RequeteLUGAPM.REQUEST_LOGIN_RAMP_AGENT), Login, Psw);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchProviderException e)
        {
            e.printStackTrace();
        }

        if (Rep != null)
        {
            if (Rep.getCode() == ReponseLUGAPM.LOGIN_OK)
            {
                System.out.println("Rep = " + Rep.getChargeUtile().get("Message"));
                this.Client.setNomUtilisateur(Rep.getChargeUtile().get("Prenom").toString() + " " + (Rep.getChargeUtile().get("Nom").toString()));

                Toast.makeText(MainActivity.this, "Logged", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(MainActivity.this, Rep.getChargeUtile().get("Message").toString(), Toast.LENGTH_LONG).show();
                this.Client.Deconnexion(new RequeteLUGAPM(RequeteLUGAPM.REQUEST_LOG_OUT_RAMP_AGENT));
            }
        }
    }

}

