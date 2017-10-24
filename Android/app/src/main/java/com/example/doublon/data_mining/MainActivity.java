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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity
{

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
                    /*Client cl =new Client();

                    try
                    {
                        DataInputStream dis = new DataInputStream(cl.getCliSock().getInputStream());;
                        DataOutputStream dos = new DataOutputStream(cl.getCliSock().getOutputStream());;
                        cl.getDos().writeUTF(valueLogin);
                        cl.getDos().writeUTF(valuePsw);
                        String Response = cl.getDis().readUTF();
                        Toast.makeText(MainActivity.this, Response, Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                } else {
                    text = (EditText) findViewById(R.id.LoginPlainText);
                    text.setHintTextColor(Color.parseColor("#B9121B"));
                    text.setHint("Erreur");
                }

            }
        });
    }

    private void contacteServeur(String Login,String Psw)
    {
        Socket cliSock = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try
        {
            cliSock = new Socket(InetAddress.getByName("10.59.22.31"),30042);
            Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_LONG).show();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            dis = new DataInputStream(cliSock.getInputStream());
            dos = new DataOutputStream(cliSock.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

