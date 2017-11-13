package com.example.doublon.data_mining;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doublon.data_mining.ConnexionServeur.Client;
import com.example.doublon.data_mining.ConnexionServeur.LoginActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import ProtocoleLUGAPM.ReponseLUGAPM;
import ProtocoleLUGAPM.RequeteLUGAPM;

public class SelectingFlightActivity extends AppCompatActivity
{
    private CommunicationServerTask cTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_selectingflights);
        setContentView(R.layout.activity_selecting_flight);
        cTask = new CommunicationServerTask();
        cTask.execute((Void) null);

        System.out.println("Langue 2 = " + getResources().getConfiguration().locale);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("item.getItemId() = " + item.getItemId());

        Locale locale = null;
        switch (item.getItemId())
        {
            case R.id.item_french:
                locale = new Locale("fr");
                break;

            case R.id.item_english:
                locale = new Locale("en");
                break;

            case R.id.item_dutch:
                locale = new Locale("nl");
                break;
        }

        Configuration config = getBaseContext().getResources().getConfiguration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        final Intent Refresh = new Intent().setClass(SelectingFlightActivity.this, SelectingFlightActivity.class);
        finish();
        startActivity(Refresh);

        return true;
    }

    public class CommunicationServerTask extends AsyncTask<Void, Void, Boolean>
    {
        ReponseLUGAPM Reponse = null;
        ArrayList<Integer> listIdVol = new ArrayList<>();


        CommunicationServerTask(){}

        @Override
        protected Boolean doInBackground(Void... params)
        {
            boolean Ok = true;
            ReponseLUGAPM Rep;

            Reponse = RecupererVols();

            if(Reponse != null)
            {
                if (Reponse.getCode() != ReponseLUGAPM.FLIGHTS_LOADED)
                    Ok = false;
            }
            else
                Ok = false;

            //Ok = false; // Test
            if(!Ok)
                Client.Deconnexion(new RequeteLUGAPM(RequeteLUGAPM.REQUEST_LOG_OUT_RAMP_AGENT));

            return Ok;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            if(success)
            {
                CreerListeVols(Reponse);
            }
            else
            {
                finish();
                final Intent LoginActivity = new Intent().setClass(SelectingFlightActivity.this, com.example.doublon.data_mining.ConnexionServeur.LoginActivity.class);
                startActivity(LoginActivity);

                Toast.makeText(getApplicationContext(), Reponse.getChargeUtile().get("Message").toString(), Toast.LENGTH_LONG).show();
            }
        }

        private ReponseLUGAPM RecupererVols()
        {
            RequeteLUGAPM Req = new RequeteLUGAPM(RequeteLUGAPM.REQUEST_LOAD_FLIGHTS);
            ReponseLUGAPM Rep = null;

            Client.EnvoyerRequete(Req);
            Rep = (ReponseLUGAPM) Client.RecevoirReponse();
            System.out.println("Rep.getChargeUtile() = " + Rep.getChargeUtile());

            return Rep;
        }

        private void CreerListeVols(ReponseLUGAPM Rep)
        {
            final ListView listview = (ListView) findViewById(R.id.listViewSFA);
            String[] values = new String[Rep.getChargeUtile().size() - 1];

            for (int i = 1 ; Rep.getChargeUtile().get(Integer.toString(i)) != null ; i++)
            {
                HashMap<String, Object> hm = (HashMap<String, Object>) Rep.getChargeUtile().get(Integer.toString(i));
                listIdVol.add((Integer) hm.get("IdVol"));
                //System.out.println("getBaseContext().getResources().getConfiguration().locale = " + getBaseContext().getResources().getConfiguration().locale);
                values[i-1] = getString(R.string.flight_name) + " " + hm.get("NumeroVol").toString() + " " + hm.get("NomCompagnie").toString() + "      -> " + hm.get("Destination").toString() + "\t" + DateFormat.getTimeInstance(DateFormat.SHORT, getBaseContext().getResources().getConfiguration().locale).format(hm.get("DateHeureDepart"));
            }

            final ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < values.length; ++i) {
                list.add(values[i]);
            }
            final StableArrayAdapter adapter = new StableArrayAdapter(SelectingFlightActivity.this, android.R.layout.simple_list_item_1, list);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
                {
                    System.out.println("position = " + position);

                    finish();
                    final Intent CheckingLugagesActivity = new Intent().setClass(SelectingFlightActivity.this, CheckingLugagesActivity.class);
                    CheckingLugagesActivity.putExtra("IdVol", listIdVol.get(position));
                    startActivity(CheckingLugagesActivity);
                }
            });
        }
    }

}
