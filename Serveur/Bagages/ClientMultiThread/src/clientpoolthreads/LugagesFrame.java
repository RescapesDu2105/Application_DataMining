/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientpoolthreads;

import ProtocoleLUGAP.ReponseLUGAP;
import ProtocoleLUGAP.RequeteLUGAP;
import java.awt.Frame;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Philippe
 */
public class LugagesFrame extends javax.swing.JFrame {
    private final int IdVol;
    private final String NomCompagnie;
    private final String Destination;
    private final String HeureDepart;
    private final Frame FenAuthentification;
    private final Client Client;
        
    /**
     * Creates new form LugagesFrame
     */
    LugagesFrame(Frame FenAuthentification, Client Client, int IdVol, String NomCompagnie, String Destination, String HeureDepart) {   
        this.IdVol = IdVol;
        this.NomCompagnie = NomCompagnie;
        this.Destination = Destination;
        this.HeureDepart = HeureDepart;
        this.FenAuthentification = FenAuthentification;
        this.Client = Client;
        
        setTitle("Bagages de : " + this.toString());     
        this.setLocation(400, 400);           
        initComponents();
        initTableauBagages();
        /*TableModel model = jTableBagages.getModel();
        jTableBagages.setModel(new ModelLugages((DefaultTableModel) model));*/
        //model.addTableModelListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBagages = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTableBagages.setModel(new DefaultTableModel(new Object [][] {}, new String [] {"Identifiant", "Poids", "Type", "Réceptionné (O/N)", "Chargé en soute (O/R/N)", "Vérifié par la douane (O/N)", "Remarques" }) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }

            @Override
            public void setValueAt(Object aValue, int row, int column)
            {
                switch (column)
                {
                    case 3:
                    case 5:
                    if (!aValue.toString().toUpperCase().equals("O") && !aValue.toString().toUpperCase().equals("N"))
                    {
                        aValue = "N";
                        javax.swing.JOptionPane.showMessageDialog(jTableBagages, "Vous ne pouvez entrer que \"O\" ou \"N\" comme valeurs pour la colonne " + this.getColumnName(column) + " !", "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    aValue = aValue.toString().toUpperCase();
                    break;
                    case 4:
                    if (!aValue.toString().toUpperCase().equals("O") && !aValue.toString().toUpperCase().equals("N") && !aValue.toString().toUpperCase().equals("R"))
                    {
                        aValue = "N";
                        javax.swing.JOptionPane.showMessageDialog(jTableBagages, "Vous ne pouvez entrer que \"O\" ou \"R\" ou \"N\" comme valeurs pour la colonne " + this.getColumnName(column) + " !", "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    aValue = aValue.toString().toUpperCase();
                    break;
                    case 6:
                    if (aValue.toString().equals(""))
                    {
                        aValue = "NEANT";
                    }
                    break;
                    default:
                    break;
                }
                super.setValueAt(aValue, row, column);
            }
        }
    );
    jTableBagages.setRowSelectionAllowed(false);
    jScrollPane1.setViewportView(jTableBagages);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(14, Short.MAX_VALUE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    new DefaultTableModel(new Object [][] {}, new String [] {"Identifiant", "Poids", "Type", "Réceptionné (O/N)", "Chargé en soute (O/R/N)", "Vérifié par la douane (O/N)", "Remarques" }) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                };
                boolean[] canEdit = new boolean [] {
                    false, false, false, true, true, true, true
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
    
                @Override
                public void setValueAt(Object aValue, int row, int column) 
                {
                    switch (column) 
                    {
                        case 3:
                        case 5:
                            if (!aValue.toString().toUpperCase().equals("O") && !aValue.toString().toUpperCase().equals("N"))
                            {
                                aValue = "N";
                                javax.swing.JOptionPane.showMessageDialog(jTableBagages, "Vous ne pouvez entrer que \"O\" ou \"N\" comme valeurs pour la colonne " + this.getColumnName(column) + " !", "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
                            }
                            else
                                aValue = aValue.toString().toUpperCase();
                            break;
                        case 4:
                            if (!aValue.toString().toUpperCase().equals("O") && !aValue.toString().toUpperCase().equals("N") && !aValue.toString().toUpperCase().equals("R"))
                            {
                                aValue = "N";
                                javax.swing.JOptionPane.showMessageDialog(jTableBagages, "Vous ne pouvez entrer que \"O\" ou \"R\" ou \"N\" comme valeurs pour la colonne " + this.getColumnName(column) + " !", "Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);
                            }
                            else
                                aValue = aValue.toString().toUpperCase();
                            break;
                        case 6:
                            if (aValue.toString().equals("")) 
                            {
                                aValue = "NEANT";
                            }
                            break;
                        default:
                            break;
                    }
                    super.setValueAt(aValue, row, column); 
                }
}
            */
    public void initTableauBagages() {
        RequeteLUGAP Req = new RequeteLUGAP(RequeteLUGAP.REQUEST_LOAD_LUGAGES);
        HashMap <String, Object> hm = new HashMap<>();
        
        hm.put("IdVol", getIdVol());
        //hm.put("NomCompagnie", getNomCompagnie());
        //hm.put("Destination", getDestination());
        //hm.put("HeureDepart", getHeureDepart());        
        Req.setChargeUtile(hm);
        hm = null;
        
        getClient().EnvoyerRequete(Req);       
        ReponseLUGAP Rep = getClient().RecevoirReponse();
        
        if (Rep != null)
        {            
            DefaultTableModel dtm = (DefaultTableModel) jTableBagages.getModel();
            HashMap<String, Object> Bagages = Rep.getChargeUtile();
            Object[] ligne = new Object[7];
            
            for (int Cpt = 1 ; Cpt <= Bagages.size() ; Cpt++) 
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
        else
            System.out.println("J'AI RIEN LOL");
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        boolean Fini = true;//false;
                
        Fini = CheckBagagesCharges();
        
        if (Fini)
        {            
            Client.Deconnexion();
            this.dispose();
            FenAuthentification.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(this, "Il reste encore des bagages à charger !", "Attention", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_formWindowClosing
    
    private boolean CheckBagagesCharges() 
    {
        boolean Fini = true;
        
        TableModel model = jTableBagages.getModel();
        for (int i = 0 ; Fini && i < model.getRowCount() ; i++)
        {            
            for (int j = 3 ; Fini && j < model.getColumnCount() ; j++)
            {                
                if (j == 3 && !model.getValueAt(i, j).equals("O"))
                    Fini = false;
                else if (j == 4 && (!model.getValueAt(i, j).equals("O") && !model.getValueAt(i, j).equals("R")))
                    Fini = false;
            }
        }
        
        if (Fini)
        {
            RequeteLUGAP Req = new RequeteLUGAP(RequeteLUGAP.REQUEST_SAVE_LUGAGES);
            HashMap <String, Object> Bagages = Req.getChargeUtile();
            DefaultTableModel dtm = (DefaultTableModel) jTableBagages.getModel();
            
            for (int row = 0 ; row < dtm.getRowCount() ; row++)
            {
                HashMap <String, Object> hm = new HashMap<>();
                System.out.println("dtm = " + dtm.getValueAt(row, 0));
                hm.put("Identifiant", dtm.getValueAt(row, 0));                
                for (int column = 3 ; column < dtm.getColumnCount() ; column++)
                {       
                    switch (column) 
                    {
                        case 3:
                            hm.put("Receptionne", dtm.getValueAt(row, column));
                            break;
                        case 4:
                            hm.put("Charge", dtm.getValueAt(row, column));
                            break;
                        case 5:
                            hm.put("Verifie", dtm.getValueAt(row, column));
                            break;
                        case 6:
                            hm.put("Remarques", dtm.getValueAt(row, column));
                            break;
                        default:
                            break;
                    } 
                }
                Bagages.put(Integer.toString(row + 1), hm);
            }
            
            getClient().EnvoyerRequete(Req);
            ReponseLUGAP Rep = getClient().RecevoirReponse();
            
            if (Rep.getCode() == ReponseLUGAP.STATUS_OK)
                JOptionPane.showMessageDialog(this, "Travail terminé !", "Travail terminé", JOptionPane.INFORMATION_MESSAGE);
        }
        
        return Fini;
    }
    
    public int getIdVol() {
        return IdVol;
    }

    public String getNomCompagnie() {
        return NomCompagnie;
    }

    public String getDestination() {
        return Destination;
    }

    public String getHeureDepart() {
        return HeureDepart;
    }

    public Frame getFenAuthentification() {
        return FenAuthentification;
    }

    public Client getClient() {
        return Client;
    }
    
    @Override
    public String toString()
    {
        return "VOL " + getIdVol() + " " + getNomCompagnie() + " - " + getDestination() + " " + getHeureDepart();
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBagages;
    // End of variables declaration//GEN-END:variables

}