package com.example.doublon.data_mining.ConnexionServeur;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.doublon.data_mining.CheckingLugagesActivity;
import com.example.doublon.data_mining.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import ProtocoleLUGAPM.ReponseLUGAPM;
import ProtocoleLUGAPM.RequeteLUGAPM;

/**
 * Created by Philippe on 11/11/2017.
 */

public class CommunicationServerTask //extends AsyncTask<Integer, Void, Boolean>
{
    /*Runnable runnableFlights;
    Runnable runnableLugages;
    ReponseLUGAPM Reponse = null;
    ArrayList<Integer> listIdVol = new ArrayList<>();


    CommunicationServerTask()
    {
        runnableFlights = new Runnable() {
            @Override
            public void run() {
                CreerListeVols(Reponse);
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
    protected Boolean doInBackground(Integer... params) {
        boolean Ok = true;
        ReponseLUGAPM Rep;

        switch (params[0])
        {
            case RequeteLUGAPM.REQUEST_LOAD_FLIGHTS:
                Reponse = RecupererVols(RequeteLUGAPM.REQUEST_LOAD_FLIGHTS);
                runOnUiThread(runnableFlights);

                break;
            case RequeteLUGAPM.REQUEST_LOAD_LUGAGES:
                Reponse = RecupererVols(RequeteLUGAPM.REQUEST_LOAD_LUGAGES);
                runOnUiThread(runnableLugages);
                break;
            default:
                Ok = false;
                break;
        }

        return Ok;
    }

    private ReponseLUGAPM RecupererVols(int Requete)
    {
        RequeteLUGAPM Req = new RequeteLUGAPM(Requete);
        ReponseLUGAPM Rep = null;

        Client.EnvoyerRequete(Req);
        Rep = (ReponseLUGAPM) Client.RecevoirReponse();
        System.out.println("Rep.getChargeUtile() = " + Rep.getChargeUtile());

        return Rep;
    }

    private void CreerListeVols(ReponseLUGAPM Rep)
    {
        final ListView listview = (ListView) findViewById(R.id.listView);
        String[] values = new String[Rep.getChargeUtile().size() - 1];

        for (int i = 1 ; Rep.getChargeUtile().get(Integer.toString(i)) != null ; i++)
        {
            HashMap<String, Object> hm = (HashMap<String, Object>) Rep.getChargeUtile().get(Integer.toString(i));
            listIdVol.add((Integer) hm.get("IdVol"));
            System.out.println("Ligne = " + hm.get("NumeroVol").toString() + " " + hm.get("NomCompagnie").toString() + " " + hm.get("Destination").toString() + " " + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.FRANCE).format(hm.get("DateHeureDepart")));
            values[i-1] = "Vol " + hm.get("NumeroVol").toString() + " " + hm.get("NomCompagnie").toString() + " " + hm.get("Destination").toString() + " " + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.FRANCE).format(hm.get("DateHeureDepart"));
        }

        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final CheckingLugagesActivity.StableArrayAdapter adapter = new CheckingLugagesActivity.StableArrayAdapter(CheckingLugagesActivity.this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                System.out.println("position = " + position);

                CheckingLugagesActivity.CommunicationServerTask test = new CheckingLugagesActivity.CommunicationServerTask();
                test.execute(RequeteLUGAPM.REQUEST_LOAD_LUGAGES);
            }

        });
    }

    private ReponseLUGAPM RecupererBagages(int Requete)
    {
        RequeteLUGAPM Req = new RequeteLUGAPM(Requete);
        ReponseLUGAPM Rep = null;

        Client.EnvoyerRequete(Req);
        Rep = (ReponseLUGAPM) Client.RecevoirReponse();

        return Rep;
    }

    private void CreerListeBagages(ReponseLUGAPM Rep)
    {
        final ListView listview = (ListView) findViewById(R.id.listView);
        String[] values = new String[] { "752-11112017-0001",  "752-11112017-0002", "752-11112017-0003", "752-11112017-0004",
                "752-11112017-0005", "752-11112017-0006", "752-11112017-0007", "752-11112017-0008", "752-11112017-0009", "752-11112017-0010"};

        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final CheckingLugagesActivity.StableArrayAdapter adapter = new CheckingLugagesActivity.StableArrayAdapter(CheckingLugagesActivity.this,
                android.R.layout.simple_list_item_multiple_choice, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                    view.animate().setDuration(50).alpha(0).withEndAction(runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            CheckedTextView check = (CheckedTextView)view;
                            check.setChecked(!check.isChecked());
                            adapter.notifyDataSetChanged();
                            view.setAlpha(1);
                        }
                    }));
            }

        });
    }
}*/
}
