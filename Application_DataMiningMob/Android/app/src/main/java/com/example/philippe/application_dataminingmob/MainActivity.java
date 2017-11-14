package com.example.philippe.application_dataminingmob;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.philippe.application_dataminingmob.ConnexionServeur.Client;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ProtocoleLUGANAPM.ReponseLUGANAPM;
import ProtocoleLUGANAPM.RequeteLUGANAPM;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    HashMap<String, Object> Annees;
    Spinner spAnnees;
    Spinner spMois;
    Spinner spCompagnies;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spAnnees = findViewById(R.id.spinnerAnnees);
        spAnnees.setOnItemSelectedListener(this);
        spMois = findViewById(R.id.spinnerMois);
        spMois.setOnItemSelectedListener(this);
        spCompagnies = findViewById(R.id.spinnerCompagnies);

        Button bRegCorr = findViewById(R.id.button_RegCorrLug);
        bRegCorr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String msg = "RegCorr";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

        Button bAnova1 = findViewById(R.id.button_Anova1);
        bAnova1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String msg = "Anova1";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

        InitTask Init = new InitTask();
        Init.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        //System.out.println("ITEM SELECTED");

        switch (parent.getId())
        {
            case R.id.spinnerAnnees:
                //System.out.println("spinnerAnnees");
                MAJ_Mois();
                break;
            case R.id.spinnerMois:
                //System.out.println("spinnerMois");
                MAJ_Compagnies();
                break;
            default : break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void MAJ_Mois()
    {
        //Mois
        Set MoiskeySet = ((HashMap<String, Object>) Annees.get(spAnnees.getSelectedItem().toString())).keySet();
        //System.out.println("Selected = " + spAnnees.getSelectedItem());
        Object[] mois = (Object[]) MoiskeySet.toArray();
        String[] values = Arrays.copyOf(mois, mois.length, String[].class);
        //values[values.length - 1] = "Toute l'année";
        //System.out.println("values = " + Arrays.toString(values));
        //System.out.println("values.length = " + values.length);
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(values));
        Collections.sort(list);
        list.add(0, "Toute l'année");
        //System.out.println("list = " + list);
        ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMois.setAdapter(adapter);
        spMois.setSelection(0);

        MAJ_Compagnies();
    }

    public void MAJ_Compagnies()
    {
        //Compagnies

        //System.out.println("Annees = " + Annees);
        String month = spMois.getSelectedItem().toString();
        //System.out.println("month = " + month);
        HashMap<String, Object> Mois = (HashMap<String, Object>) Annees.get(spAnnees.getSelectedItem());
        //System.out.println("Mois = " + Mois);
        ArrayList<String> Compagnies = null;

        if(month != "Toute l'année")
        {
            Compagnies = (ArrayList<String>) Mois.get(month);
            //System.out.println("Compagnies 0 = " + Compagnies);
        }
        else
        {
            Compagnies = new ArrayList<>();
            for(int i = 1 ; i < spMois.getCount() ; i++)
            {
                String mois = spMois.getItemAtPosition(i).toString();
                //System.out.println("mois = " + mois);
                ArrayList<String> Temp = new ArrayList<>((ArrayList<String>) Mois.get(mois));
                //System.out.println("Temp = " + Temp);
                //System.out.println("Compagnies 1 = " + Compagnies);
                Temp.removeAll(Compagnies);
                Compagnies.addAll(Temp);
                //System.out.println("Compagnies 2 = " + Compagnies);
            }
        }

        if (Compagnies.contains("Toutes les compagnies"))
            Compagnies.remove("Toutes les compagnies");

        Compagnies.add(0, "Toutes les compagnies");

        ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, Compagnies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCompagnies.setAdapter(adapter);
        spCompagnies.setSelection(0);
    }

    public class InitTask extends AsyncTask<Void, Void, Boolean>
    {
        private ReponseLUGANAPM Reponse = null;

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            boolean Ok = true;

            Reponse = RecupererDataSpinners();

            if(Reponse != null)
            {
                if (Reponse.getCode() != ReponseLUGANAPM.INITIATED)
                    Ok = false;
            }
            else
                Ok = false;

            //Ok = false; // Test
            if(!Ok)
                Client.Deconnexion(new RequeteLUGANAPM(RequeteLUGANAPM.REQUEST_LOG_OUT_ANALYST));

            return Ok;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            if(success)
            {
                //Années
                Spinner spAnnees = findViewById(R.id.spinnerAnnees);
                Annees = (HashMap<String, Object>) Reponse.getChargeUtile().get("Data");
                Set AnneeskeySet = Annees.keySet();
                Object[] annees = (Object[]) AnneeskeySet.toArray();
                String[] values = Arrays.copyOf(annees, annees.length, String[].class);

                //System.out.println("values = " + Arrays.toString(annees));
                List<String> list = Arrays.asList(values);
                Collections.sort(list);
                //System.out.println("list = " + list);
                ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spAnnees.setAdapter(adapter);
            }
        }

        private ReponseLUGANAPM RecupererDataSpinners()
        {
            ReponseLUGANAPM Rep = null;

            RequeteLUGANAPM Req = new RequeteLUGANAPM(RequeteLUGANAPM.REQUEST_INIT);

            Client.EnvoyerRequete(Req);
            Rep = (ReponseLUGANAPM) Client.RecevoirReponse();
            System.out.println("Rep.getChargeUtile() = " + Rep.getChargeUtile());

            return Rep;
        }
    }
}
