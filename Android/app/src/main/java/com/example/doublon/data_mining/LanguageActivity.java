package com.example.doublon.data_mining;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LanguageActivity extends AppCompatActivity implements Button.OnClickListener {
    private Button bConfirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        bConfirmer = (Button) findViewById(R.id.confirm_button);
    }

    @Override
    public void onClick(View v) {
        System.out.println("Test click");
    }
}
