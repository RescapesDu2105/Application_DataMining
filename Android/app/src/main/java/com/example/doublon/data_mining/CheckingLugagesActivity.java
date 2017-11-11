package com.example.doublon.data_mining;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.example.doublon.data_mining.ConnexionServeur.Client;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ProtocoleLUGAPM.ReponseLUGAPM;
import ProtocoleLUGAPM.RequeteLUGAPM;

public class CheckingLugagesActivity extends AppCompatActivity {

    private CommunicationServerTask cTask = null;
    private int IdVol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IdVol = (int) getIntent().getExtras().get("IdVol");
        setContentView(R.layout.activity_checking_lugages);

        cTask = new CommunicationServerTask();
        cTask.execute((Void) null);

        System.out.println("Langue 3 = " + getResources().getConfiguration().locale);
    }

    public class CommunicationServerTask extends AsyncTask<Void, Void, Boolean> {
        Runnable runnableCheck;
        Runnable runnableLugages;
        ReponseLUGAPM Reponse = null;
        StableArrayAdapter adapter = null;
        View view = null;

        CommunicationServerTask()
        {
            runnableCheck = new Runnable()
            {
                @Override
                public void run()
                {
                    CheckedTextView check = (CheckedTextView)view;
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
            ReponseLUGAPM Rep = null;

            Client.EnvoyerRequete(Req);
            Rep = (ReponseLUGAPM) Client.RecevoirReponse();

            return Rep;
        }

        private void CreerListeBagages(ReponseLUGAPM Rep)
        {
            final ListView listview = (ListView) findViewById(R.id.listViewCLA);
            String[] values = new String[] { "752-11112017-0001",  "752-11112017-0002", "752-11112017-0003", "752-11112017-0004",
                    "752-11112017-0005", "752-11112017-0006", "752-11112017-0007", "752-11112017-0008", "752-11112017-0009", "752-11112017-0010"};

            HashMap <String, Object> hm = new HashMap<>();

            hm.put("IdVol", getVols().get("IdVol"));
            Req.setChargeUtile(hm);

            getClient().EnvoyerRequete(Req);
            ReponseLUGAP Rep = getClient().RecevoirReponse();

            if (Rep != null)
            {
                if (Rep.getCode() == ReponseLUGAP.LUGAGES_LOADED)
                {
                    DefaultTableModel dtm = (DefaultTableModel) jTableBagages.getModel();
                    HashMap<String, Object> Bagages = Rep.getChargeUtile();
                    Object[] ligne = new Object[7];

                    for (int Cpt = 1 ; Cpt <= Bagages.size() - 2 ; Cpt++)
                    {
                        hm = (HashMap) Bagages.get(Integer.toString(Cpt));
                        ligne[0] = hm.get("IdBagage");
                        ligne[1] = hm.get("Poids");
                        ligne[2] = hm.get("TypeBagage");
                        ligne[3] = hm.get("Receptionne");
                        ligne[4] = hm.get("Charge");
                        ligne[5] = hm.get("Verifie");
                        ligne[6] = hm.get("Remarques");
                        dtm.insertRow(Cpt - 1, ligne);
                    }
                }
                else if (Rep != null)
                {
                    JOptionPane.showMessageDialog(this, ReponseLUGAP.INTERNAL_SERVER_ERROR_MESSAGE, "Impossible de charger les bagages !", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Le serveur s'est déconnecté !", "Erreur", JOptionPane.ERROR_MESSAGE);
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
    }


}
