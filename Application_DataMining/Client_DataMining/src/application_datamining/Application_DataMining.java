/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_datamining;

import ProtocoleLUGANAP.ReponseLUGANAP;
import ProtocoleLUGANAP.RequeteLUGANAP;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerListModel;

/**
 *
 * @author Doublon
 */
public class Application_DataMining extends javax.swing.JFrame {
    private final Client Client;
    private HashMap<String, Object> Annees = null;
    private List DataCorr = new ArrayList(); 
    private int [] Poids = null , Distance=null;
    
    /**
     * Creates new form MainFrame_AppDataMining
     * @param client
     */

    public Application_DataMining(Client client) 
    {
        this.Client = client;
                
        setLocationRelativeTo(null); 
        initComponents();
        InitialisationSpinners();
        //((DefaultEditor) jSpinnerMois.getEditor()).getTextField().setEditable(false);
        //((DefaultEditor) jSpinnerCompagnies.getEditor()).getTextField().setEditable(false);
    }
    
    public void InitialisationSpinners() 
    {
        ReponseLUGANAP Rep = null;
        
        RequeteLUGANAP Req = new RequeteLUGANAP(RequeteLUGANAP.REQUEST_INIT);
        Client.EnvoyerRequete(Req);
        Rep = Client.RecevoirReponse();
        
        if(Rep != null)
        {
            if(Rep.getCode() == ReponseLUGANAP.INITIATED)
            {
                Annees = (HashMap<String, Object>) Rep.getChargeUtile().get("Data");
                System.out.println("Annees = " + Annees);
                
                Set keySet = Annees.keySet();
                Object[] annees = (Object[]) keySet.toArray();
                System.out.println("annees = " + Arrays.toString(annees));
                
                SpinnerListModel SpinnerListModel = new SpinnerListModel(annees);
                jSpinnerAnnees.setModel(SpinnerListModel);
                jSpinnerAnnees.setValue(annees[annees.length-1]);
                
                if (annees.length == 1)
                    MAJ_Mois();
                
                ((DefaultEditor) jSpinnerAnnees.getEditor()).getTextField().setEditable(false);
            }
        }
        else
        {
            
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSpinnerCompagnies = new javax.swing.JSpinner();
        jLabelMois = new javax.swing.JLabel();
        jSpinnerMois = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerAnnees = new javax.swing.JSpinner();
        jButtonAnova1 = new javax.swing.JButton();
        jButtonAnova2 = new javax.swing.JButton();
        jButtonRegCorr = new javax.swing.JButton();
        jButtonRegCorrLugPlus = new javax.swing.JButton();
        jButtonEffacer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Compagnie :");

        jSpinnerCompagnies.setModel(new javax.swing.SpinnerListModel(new String[] {""}));

        jLabelMois.setText("Mois :");

        jSpinnerMois.setModel(new javax.swing.SpinnerListModel(new String[] {""}));
        jSpinnerMois.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerMoisStateChanged(evt);
            }
        });

        jLabel2.setText("Année :");

        jSpinnerAnnees.setModel(new javax.swing.SpinnerListModel(new String[] {""}));
        jSpinnerAnnees.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerAnneesStateChanged(evt);
            }
        });

        jButtonAnova1.setText("Anova 1");
        jButtonAnova1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnova1ActionPerformed(evt);
            }
        });

        jButtonAnova2.setText("Anova 2");

        jButtonRegCorr.setText("Regression Corrélation");
        jButtonRegCorr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegCorrActionPerformed(evt);
            }
        });

        jButtonRegCorrLugPlus.setText("Régression Corrélation Plus");

        jButtonEffacer.setText("Effacer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonRegCorr, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRegCorrLugPlus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAnova1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAnova2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jButtonEffacer))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerAnnees, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelMois)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerMois, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerCompagnies)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSpinnerAnnees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinnerCompagnies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMois)
                    .addComponent(jSpinnerMois, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAnova1)
                    .addComponent(jButtonRegCorr)
                    .addComponent(jButtonRegCorrLugPlus)
                    .addComponent(jButtonAnova2)
                    .addComponent(jButtonEffacer))
                .addContainerGap(246, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAnova1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnova1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAnova1ActionPerformed

    private void jSpinnerAnneesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerAnneesStateChanged
        MAJ_Mois();
    }//GEN-LAST:event_jSpinnerAnneesStateChanged

    private void jSpinnerMoisStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerMoisStateChanged
        MAJ_Compagnies();
    }//GEN-LAST:event_jSpinnerMoisStateChanged


    private void jButtonRegCorrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegCorrActionPerformed
        ReponseLUGANAP Rep = null;
        RequeteLUGANAP Req = new RequeteLUGANAP(RequeteLUGANAP.REG_CORR_LUG);        

        Req.getChargeUtile().put("Annee", jSpinnerAnnees.getValue());
        Req.getChargeUtile().put("Mois", jSpinnerMois.getValue());
        Req.getChargeUtile().put("Compagnie", jSpinnerCompagnies.getValue());

        Client.EnvoyerRequete(Req);
        Rep = Client.RecevoirReponse();
        if(Rep != null)
        {
            List P = new ArrayList(); 
            List D = new ArrayList(); 
            if(Rep.getCode() == ReponseLUGANAP.REG_CORR_LUG_OK)
            {
                DataCorr = (List) Rep.getChargeUtile().get("Data");
                for(int i=0 ;i<DataCorr.size();i=i+2)
                {
                    
                    P.add(DataCorr.get(i));
                    D.add(DataCorr.get(i+1));
                    //System.out.println("Poids : "+DataCorr.get(i));
                    //System.out.println("Distance : "+DataCorr.get(i+1));
                }
                Poids=toIntArray(P);
                Distance=toIntArray(D);
                
                
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new HistogrammeGUI(Poids , Distance).setVisible(true);
                new ScatterPlotGUI(Poids , Distance).setVisible(true);
            }
        });
            }
        }        
    }//GEN-LAST:event_jButtonRegCorrActionPerformed

    public void MAJ_Mois()
    {
        jSpinnerCompagnies.setModel(new SpinnerListModel());
        
        Set keySet = ((HashMap<String, Object>) Annees.get(jSpinnerAnnees.getValue())).keySet();
        Object[] obj = (Object[]) keySet.toArray();
        Arrays.sort(obj, Collections.reverseOrder());
        //System.out.println("mois = " + Arrays.toString(obj));
        Object[] mois = new Object[obj.length + 1];
        for (int i = 0 ; i < obj.length ; i++) 
        {
            java.util.Date date= new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.MONTH, Integer.parseInt(obj[i].toString()) - 1);
            String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRENCH);
            month = month.substring(0, 1).toUpperCase() + month.substring(1);
            mois[i] = month;
        }
        
        mois[obj.length] = "Toute l'année";
        //System.out.println("mois = " + Arrays.toString(mois));
        
        SpinnerListModel SpinnerListModel = new SpinnerListModel(mois);
        jSpinnerMois.setModel(SpinnerListModel);
        jSpinnerMois.setValue("Toute l'année");

        //jSpinnerMois.setEnabled(true);
        ((DefaultEditor) jSpinnerMois.getEditor()).getTextField().setEditable(false);
        
        MAJ_Compagnies();
    }
    
    public void MAJ_Compagnies()
    {
        jSpinnerCompagnies.setModel(new SpinnerListModel());
                
        String month = jSpinnerMois.getValue().toString();
        ArrayList<String> Compagnies = null;
        System.out.println("Equals = " + "Toute l'année".equals(month));
        int mois = -1;
        if(!"Toute l'année".equals(month))
        {
            mois = NumberOfMonth(month);
        }
        
        HashMap<String, Object> Mois = (HashMap<String, Object>) Annees.get(jSpinnerAnnees.getValue());
        
        if (mois != -1)
        {            
            Compagnies = (ArrayList<String>) Mois.get(Integer.toString(mois));
            Compagnies.add("Toutes les compagnies");
            
            SpinnerListModel SpinnerListModel = new SpinnerListModel(Compagnies.toArray());
            jSpinnerCompagnies.setModel(SpinnerListModel);
            jSpinnerCompagnies.setValue("Toutes les compagnies");

            //jSpinnerCompagnies.setEnabled(true);
            ((DefaultEditor) jSpinnerCompagnies.getEditor()).getTextField().setEditable(false);
        }
        else
        {
            Object[] obj = ((SpinnerListModel)jSpinnerMois.getModel()).getList().toArray();
            Compagnies = new ArrayList<>();
            for (int i = 0 ; i < obj.length - 1 ; i++)
            {
                mois = NumberOfMonth(obj[i].toString());
                ArrayList<String> Temp = (ArrayList<String>) Mois.get(Integer.toString(mois));
                Temp.removeAll(Compagnies);
                Compagnies.addAll(Temp);
            }
            
            Compagnies.add("Toutes les compagnies");

            SpinnerListModel SpinnerListModel = new SpinnerListModel(Compagnies.toArray());
            jSpinnerCompagnies.setModel(SpinnerListModel);
            jSpinnerCompagnies.setValue("Toutes les compagnies");

            //jSpinnerCompagnies.setEnabled(true);
            ((DefaultEditor) jSpinnerCompagnies.getEditor()).getTextField().setEditable(false);
        }
    }
    
    public int NumberOfMonth(String month) 
    {
        int mois;
        
        switch(month)
        {
            case "Janvier":
                mois = 1;
                break;
            case "Février":
                mois = 2;
                break;
            case "Mars":
                mois = 3;
                break;
            case "Avril":
                mois = 4;
                break;
            case "Mai":
                mois = 5;
                break;
            case "Juin":
                mois = 6;
                break;
            case "Juillet":
                mois = 7;
                break;
            case "Août":
                mois = 8;
                break;
            case "Septembre":
                mois = 9;
                break;
            case "Octobre":
                mois = 10;
                break;
            case "Novembre":
                mois = 11;
                break;
            case "Décembre":
                mois = 12;
                break;
            default : mois = -1;
        }
        
        return mois;
    }
    int[] toIntArray(List<Integer> list)
    {
        int[] ret = new int[list.size()];
        for(int i = 0;i < ret.length;i++)
            ret[i] = list.get(i);
        return ret;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application_DataMining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application_DataMining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application_DataMining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application_DataMining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new Application_DataMining(null).setVisible(true);
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnova1;
    private javax.swing.JButton jButtonAnova2;
    private javax.swing.JButton jButtonEffacer;
    private javax.swing.JButton jButtonRegCorr;
    private javax.swing.JButton jButtonRegCorrLugPlus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelMois;
    private javax.swing.JSpinner jSpinnerAnnees;
    private javax.swing.JSpinner jSpinnerCompagnies;
    private javax.swing.JSpinner jSpinnerMois;
    // End of variables declaration//GEN-END:variables
}
