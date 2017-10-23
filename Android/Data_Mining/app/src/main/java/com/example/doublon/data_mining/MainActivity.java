package com.example.doublon.data_mining;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ConnexionAndroid.Client;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) this.findViewById(R.id.OkButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                EditText text = (EditText)findViewById(R.id.LoginPlainText);
                text.setHintTextColor(Color.parseColor("#000000"));
                String value=text.getText().toString();
                if(!value.equals(""))
                {
                    Toast.makeText(MainActivity.this, value, Toast.LENGTH_LONG).show();
                }
                else
                {
                    text.setHintTextColor(Color.parseColor("#B9121B"));
                    text.setHint("Erreur");
                }

            }
        });

    }

}
