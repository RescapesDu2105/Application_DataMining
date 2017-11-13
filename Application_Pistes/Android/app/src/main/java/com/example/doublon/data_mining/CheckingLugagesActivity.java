package com.example.doublon.data_mining;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doublon.data_mining.ConnexionServeur.Client;
import com.example.doublon.data_mining.ConnexionServeur.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import ProtocoleLUGAPM.ReponseLUGAPM;
import ProtocoleLUGAPM.RequeteLUGAPM;

public class CheckingLugagesActivity extends AppCompatActivity {

    private ListeBagagesTask LBTask = null;
    private EnvoyerBagagesTask EBTask = null;
    private int IdVol;
    private int NbBagages;
    private int NbBagages_Checked = 0;
    private ArrayList<String> IdsBagages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_checkinglugages);

        IdVol = (int) getIntent().getExtras().get("IdVol");
        setContentView(R.layout.activity_checking_lugages);
        LBTask = new ListeBagagesTask();
        EBTask = new EnvoyerBagagesTask();
        Button bConfirmer = findViewById(R.id.button_confirm);
        bConfirmer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (NbBagages_Checked == NbBagages)
                {
                    EBTask.execute();
                }
                else
                {
                    String msg = "Certains bagages ne sont pas dans la soute !";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });

        LBTask.execute();

        System.out.println("Langue 3 = " + getResources().getConfiguration().locale);
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

        final Intent Refresh = new Intent().setClass(CheckingLugagesActivity.this, CheckingLugagesActivity.class);
        Refresh.putExtra("IdVol", IdVol);
        finish();
        startActivity(Refresh);

        return true;
    }

    public class ListeBagagesTask extends AsyncTask<Void, Void, Boolean>
    {
        ReponseLUGAPM Reponse = null;

        ListeBagagesTask() {}

        @Override
        protected Boolean doInBackground(Void... params)
        {
            boolean Ok = true;

            Reponse = RecupererBagages();
            if (Reponse != null)
            {
                if (Reponse.getCode() != ReponseLUGAPM.LUGAGES_LOADED)
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
                CreerListeBagages(Reponse);
            }
            else
            {
                finish();
                final Intent LoginActivity = new Intent().setClass(CheckingLugagesActivity.this, LoginActivity.class);
                startActivity(LoginActivity);

                Toast.makeText(getApplicationContext(), Reponse.getChargeUtile().get("Message").toString(), Toast.LENGTH_LONG).show();
            }
        }

        private ReponseLUGAPM RecupererBagages()
        {
            RequeteLUGAPM Req = new RequeteLUGAPM(RequeteLUGAPM.REQUEST_LOAD_LUGAGES);
            HashMap <String, Object> hm = new HashMap<>();

            hm.put("IdVol", IdVol);
            Req.setChargeUtile(hm);

            Client.EnvoyerRequete(Req);
            ReponseLUGAPM Rep = (ReponseLUGAPM) Client.RecevoirReponse();

            return Rep;
        }

        private void CreerListeBagages(ReponseLUGAPM Rep)
        {
            final ListView listview = (ListView) findViewById(R.id.listViewCLA);
            HashMap<String, Object> Bagages = Rep.getChargeUtile();

            NbBagages = Rep.getChargeUtile().size() - 2;
            String[] values = new String[NbBagages];
            System.out.println("Bagages = " + Bagages);

            for (int Cpt = 1 ; Cpt <= Bagages.size() - 2 ; Cpt++)
            {
                HashMap <String, Object> hm = (HashMap) Bagages.get(Integer.toString(Cpt));
                IdsBagages.add(hm.get("IdBagage").toString());
                System.out.println("Bagage = " + hm.get("IdBagage").toString() + " " + hm.get("Poids").toString() + " " + " " + hm.get("TypeBagage").toString());
                String Valise;
                if (hm.get("TypeBagage").toString().equals("PasValise"))
                    Valise = getString(R.string.noSuitcase_name);
                else
                    Valise = getString(R.string.suitcase_name);

                values[Cpt-1] = hm.get("IdBagage").toString() + " " + hm.get("Poids").toString() + " kg" + " " + Valise;
            }

            final ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < values.length; ++i) {
                list.add(values[i]);
            }
            final StableArrayAdapter adapter = new StableArrayAdapter(CheckingLugagesActivity.this,
                    android.R.layout.simple_list_item_multiple_choice, list);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
                {
                    //view = pView;
                    view.animate().setDuration(50).alpha(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            CheckedTextView check = (CheckedTextView)view;
                            if(!check.isChecked())
                                NbBagages_Checked++;
                            else
                                NbBagages_Checked--;

                            check.setChecked(!check.isChecked());
                            adapter.notifyDataSetChanged();
                            view.setAlpha(1);
                        }
                    });
                }
            });
        }
    }

    public class EnvoyerBagagesTask extends AsyncTask<Void, Void, Boolean>
    {
        String ErrorMsg = "";

        EnvoyerBagagesTask(){}

        public boolean EnvoyerBagages()
        {
            boolean Ok = false;

            RequeteLUGAPM Req = new RequeteLUGAPM(RequeteLUGAPM.REQUEST_SAVE_LUGAGES);
            HashMap <String, Object> hm = new HashMap<>();

            hm.put("IdVol", IdVol);
            hm.put("IdsBagages", IdsBagages);
            Req.setChargeUtile(hm);

            Client.EnvoyerRequete(Req);
            ReponseLUGAPM Rep = (ReponseLUGAPM) Client.RecevoirReponse();

            if(Rep.getCode() == ReponseLUGAPM.LUGAGES_SAVED)
                Ok = true;

            return Ok;
        }

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            boolean Ok;

            Ok = EnvoyerBagages();
            //Ok = false; // TEST
            if (!Ok)
            {
                ErrorMsg = "Problème interne au serveur !";
            }
            Client.Deconnexion(new RequeteLUGAPM(RequeteLUGAPM.REQUEST_LOG_OUT_RAMP_AGENT));

            return Ok;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            finish();
            final Intent LoginActivity = new Intent().setClass(CheckingLugagesActivity.this, LoginActivity.class);
            startActivity(LoginActivity);

            if(success)
            {
                String msg = "Tous les bagages sont dans la soute, vous êtes donc déconnecté du serveur!";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), ErrorMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
