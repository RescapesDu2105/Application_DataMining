package com.example.doublon.data_mining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.doublon.data_mining.ConnexionAndroid.Client;

import java.io.Serializable;

import static com.example.doublon.data_mining.R.id.confirm_button;

public class LanguageActivity extends AppCompatActivity
{
    private Button bConfirmer;
    private Client Client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Client = getIntent().getParcelableExtra("Client");
        setContentView(R.layout.activity_language);
        bConfirmer = findViewById(confirm_button);
        bConfirmer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Test click");
                //final Intent LanguagePicker = new Intent().setClass(LoginActivity.this, LanguageActivity.class);
                //LanguagePicker.putExtra("Client", (Serializable) getClient());
                //startActivity(LanguagePicker);
                finish();
            }
        });
    }

    public Client getClient()
    {
        return Client;
    }
}
