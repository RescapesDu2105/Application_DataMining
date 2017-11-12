package com.example.doublon.data_mining;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doublon.data_mining.ConnexionServeur.Client;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ProtocoleLUGAPM.ReponseLUGAPM;
import ProtocoleLUGAPM.RequeteLUGAPM;

public class CheckingLugagesActivity extends AppCompatActivity {

    private ListeBagagesTask LBTask = null;
    private EnvoyerBagagesTask EBTask = null;
    private int IdVol;
    private int NbBagages;
    private int NbBagages_Checked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public class ListeBagagesTask extends AsyncTask<Void, Void, Boolean>
    {
        Runnable runnableCheck;
        Runnable runnableLugages;
        Runnable runnableSendLugages;
        ReponseLUGAPM Reponse = null;
        StableArrayAdapter adapter = null;
        View view = null;

        ListeBagagesTask()
        {
            runnableCheck = new Runnable()
            {
                @Override
                public void run()
                {
                    CheckedTextView check = (CheckedTextView)view;
                    if(!check.isChecked())
                        NbBagages_Checked++;
                    else
                        NbBagages_Checked--;

                    check.setChecked(!check.isChecked());
                    adapter.notifyDataSetChanged();
                    view.setAlpha(1);
                }
            };

            runnableLugages = new Runnable() {
                @Override
                public void run() {
                    CreerListeBagages(Reponse);
                }
            };
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean Ok = true;
            ReponseLUGAPM Rep;

            Reponse = RecupererBagages();
            runOnUiThread(runnableLugages);

            return Ok;
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
            /*String[] values = new String[] { "752-11112017-0001",  "752-11112017-0002", "752-11112017-0003", "752-11112017-0004",
                    "752-11112017-0005", "752-11112017-0006", "752-11112017-0007", "752-11112017-0008", "752-11112017-0009", "752-11112017-0010"};*/
            HashMap<String, Object> Bagages = Rep.getChargeUtile();

            if (Rep != null)
            {
                if (Rep.getCode() == ReponseLUGAPM.LUGAGES_LOADED)
                {
                    NbBagages = Rep.getChargeUtile().size() - 2;
                    String[] values = new String[NbBagages];
                    System.out.println("Bagages = " + Bagages);

                    for (int Cpt = 1 ; Cpt <= Bagages.size() - 2 ; Cpt++)
                    {
                        HashMap <String, Object> hm = (HashMap) Bagages.get(Integer.toString(Cpt));
                        System.out.println("Bagage = " + hm.get("IdBagage").toString() + " " + hm.get("Poids").toString() + " " + " " + hm.get("TypeBagage").toString());
                        values[Cpt-1] = hm.get("IdBagage").toString() + " " + hm.get("Poids").toString() + " " + " " + hm.get("TypeBagage").toString();
                    }

                    final ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < values.length; ++i) {
                        list.add(values[i]);
                    }
                    adapter = new StableArrayAdapter(CheckingLugagesActivity.this,
                            android.R.layout.simple_list_item_multiple_choice, list);
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View pView, int position, long id)
                        {
                            view = pView;
                            pView.animate().setDuration(50).alpha(0).withEndAction(runnableCheck);
                        }
                    });
                }
                else if (Rep != null)
                {
                    //JOptionPane.showMessageDialog(this, ReponseLUGAP.INTERNAL_SERVER_ERROR_MESSAGE, "Impossible de charger les bagages !", JOptionPane.ERROR_MESSAGE);
                    //System.exit(1);
                }
            }
            else
            {
                //JOptionPane.showMessageDialog(this, "Le serveur s'est déconnecté !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class EnvoyerBagagesTask extends AsyncTask<Void, Void, Boolean>
    {
        Runnable runnableSendLugages;
        ReponseLUGAPM Reponse = null;

        EnvoyerBagagesTask()
        {
            runnableSendLugages = new Runnable() {
                @Override
                public void run() {
                    EnvoyerBagages();
                }
            };
        }

        public boolean EnvoyerBagages()
        {
            boolean Ok = false;

            RequeteLUGAPM Req = new RequeteLUGAPM(RequeteLUGAPM.REQUEST_SAVE_LUGAGES);
            HashMap <String, Object> hm = new HashMap<>();

            hm.put("IdVol", IdVol);
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
            if (Ok)
            {
                String msg = "Tous les bagages sont dans la soute !";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            else
            {
                String msg = "Problème interne au serveur !";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }
}
