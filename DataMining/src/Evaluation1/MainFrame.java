/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

/**
 *
 * @author Philippe
 */
public class MainFrame extends javax.swing.JFrame{
       private final ConnexionRServe CRS;
    
    /**
     * Creates new form MainFrame
     * @param CRS
     */
    public MainFrame(ConnexionRServe CRS) {
        this.CRS = CRS;
                
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonConnexion = new javax.swing.JButton();
        jButtonEx1 = new javax.swing.JButton();
        jButtonEx2 = new javax.swing.JButton();
        jButtonEx3 = new javax.swing.JButton();
        jButtonEx4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaConclusion = new javax.swing.JTextArea();
        jInternalFrameGraphique = new javax.swing.JInternalFrame();
        DeleteValButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonConnexion.setText("Connexion à RServe");
        jButtonConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnexionActionPerformed(evt);
            }
        });

        jButtonEx1.setText("Exercice 1");
        jButtonEx1.setEnabled(false);
        jButtonEx1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEx1ActionPerformed(evt);
            }
        });

        jButtonEx2.setText("Exercice 2");
        jButtonEx2.setEnabled(false);
        jButtonEx2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEx2ActionPerformed(evt);
            }
        });

        jButtonEx3.setText("Exercice 3");
        jButtonEx3.setEnabled(false);
        jButtonEx3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEx3ActionPerformed(evt);
            }
        });

        jButtonEx4.setText("Exercice 4");
        jButtonEx4.setEnabled(false);
        jButtonEx4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEx4ActionPerformed(evt);
            }
        });

        jTextAreaConclusion.setColumns(20);
        jTextAreaConclusion.setRows(5);
        jScrollPane1.setViewportView(jTextAreaConclusion);

        jInternalFrameGraphique.setVisible(true);

        javax.swing.GroupLayout jInternalFrameGraphiqueLayout = new javax.swing.GroupLayout(jInternalFrameGraphique.getContentPane());
        jInternalFrameGraphique.getContentPane().setLayout(jInternalFrameGraphiqueLayout);
        jInternalFrameGraphiqueLayout.setHorizontalGroup(
            jInternalFrameGraphiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrameGraphiqueLayout.setVerticalGroup(
            jInternalFrameGraphiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
        );

        DeleteValButton.setText("Retirer Les valeurs abérantes");
        DeleteValButton.setEnabled(false);
        DeleteValButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteValButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 242, Short.MAX_VALUE)
                .addComponent(jButtonConnexion)
                .addGap(244, 244, 244))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jInternalFrameGraphique)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jButtonEx1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEx2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEx3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEx4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(DeleteValButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonConnexion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEx1)
                    .addComponent(jButtonEx2)
                    .addComponent(jButtonEx3)
                    .addComponent(jButtonEx4))
                .addGap(18, 18, 18)
                .addComponent(jInternalFrameGraphique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(DeleteValButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnexionActionPerformed
        if (CRS.getRConnexion() == null || !CRS.getRConnexion().isConnected()){            
            CRS.Connexion();
            jButtonConnexion.setText("Déconnexion");
            //jButtonConnexion.setBounds(jButtonConnexion.getX(), jButtonConnexion.getY(), jButtonConnexion.getWidth(), jButtonConnexion.getHeight() +50);
            jButtonEx1.setEnabled(true);
            jButtonEx2.setEnabled(true);
            jButtonEx3.setEnabled(true);
            jButtonEx4.setEnabled(true);
        }
        else if (CRS.getRConnexion().isConnected()) {
            jInternalFrameGraphique.setVisible(false);
            CRS.getRConnexion().close();
            jButtonConnexion.setText("Connexion à RServe");
            jButtonEx1.setEnabled(false);
            jButtonEx2.setEnabled(false);
            jButtonEx3.setEnabled(false);
            jButtonEx4.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonConnexionActionPerformed

    private void jButtonEx1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEx1ActionPerformed
        Double mean, median, FirstQT, ThirdQT, min, max;
        int jMin = 1, iMax = 11;
        int NbValues, NbLevels;
        List<Double> list = new ArrayList<>();
        String CurrentLevel = null, Temp = null;
        String[] Headers;
        DefaultBoxAndWhiskerCategoryDataset ds = new DefaultBoxAndWhiskerCategoryDataset();
        
        DeleteValButton.setEnabled(false);
        jTextAreaConclusion.setText(null);
        
        try {
            // Setup des variables utiles
            
            //Path Zeydax :
            //CRS.getRConnexion().voidEval("data<-read.table(\"E:/Dropbox/B3/e-Commerce/Application_DataMining/1_Anova_asthme.csv\",h=TRUE,sep=\";\")"); 
            // Path Doublon : 
            CRS.getRConnexion().voidEval("data<-read.table(\"C:/Users/Doublon/Desktop/R_jar/1_Anova_asthme.csv\",h=TRUE,sep=\";\")"); 
            CRS.getRConnexion().voidEval("attach(data)");
            Headers = CRS.getRConnexion().eval("colnames(data)").asStrings();            
            NbValues = CRS.getRConnexion().eval("length(data$" + Headers[1] + ")").asInteger();
            NbLevels = CRS.getRConnexion().eval("length(levels(data$" + Headers[0] + "))").asInteger();
            
            // Récupération des données
            CurrentLevel = CRS.getRConnexion().eval("data$" + Headers[0] + "[1]").asString();            
            for (int i = 1, j = 1 ; j <= NbValues ; j++) {
                Temp = CRS.getRConnexion().eval("data$" + Headers[0] + "[" + j + "]").asString();
                //System.out.println("Temp : " +Temp);
                if (j != 1 && !Temp.equals(CurrentLevel)) {
                    //System.out.println("Check : " + i);
                    ds.add(list, "Serie 1", CurrentLevel);
                    CurrentLevel = Temp;
                   //System.out.println("CurrentLevel : " +CurrentLevel);
                    i++;
                    list.clear();
                }
                list.add(CRS.getRConnexion().eval("data$" + Headers[1] + "[" + j + "]").asDouble());
                //System.out.println("Valeur : " + CRS.getRConnexion().eval("data$Mesure[" + j + "]").asInteger());                
            }        
            ds.add(list, "Serie 1", CurrentLevel);
                        
            // Création et gestion du graphique
            JFreeChart jfc = ChartFactory.createBoxAndWhiskerChart("Exercice 1 : traitements contre l'asthme", Headers[0] , Headers[1], ds, false);
            CategoryPlot plot = (CategoryPlot) jfc.getCategoryPlot();
            BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
            renderer.setSeriesPaint(0, Color.GREEN);
            plot.setRenderer(0, renderer);            
            ChartPanel cp = new ChartPanel(jfc);
            jInternalFrameGraphique.setContentPane(cp);
            jInternalFrameGraphique.setVisible(true);
            
            // Récupération des informations pour la conclusion
            CRS.getRConnexion().voidEval("model<-lm(" + Headers[1] + "~" + Headers[0] + ")");
            CRS.getRConnexion().voidEval("anova<-aov(" + Headers[1] + "~" + Headers[0] + ")");
            // Fonction pvalue ?
            Double pvalue = CRS.getRConnexion().eval("summary(model)$coefficients[1,4]").asDouble();
            System.out.println("p-value de l'ANOVA = " + pvalue);
            jTextAreaConclusion.setText("p-value de l'ANOVA = " + pvalue);
            AfficherAnalysePvalue(pvalue);
            
            int Length = CRS.getRConnexion().eval("length(rownames(TukeyHSD(anova)$" + Headers[0] + "))").asInteger();            
            for (int i = 1 ; i <= Length ; i++) {
                String[] populations_testees = CRS.getRConnexion().eval("rownames(TukeyHSD(anova)$" + Headers[0] + ")[" + i + "]").asString().split("-");
                
                jTextAreaConclusion.setText(jTextAreaConclusion.getText() + "\nPopulations : " + populations_testees[0] + " et " + populations_testees[1] + " => ");
                pvalue = CRS.getRConnexion().eval("TukeyHSD(anova)$" + Headers[0] + "[" + i + ",4]").asDouble();
                
                AfficherAnalysePvalue(pvalue, populations_testees[0], populations_testees[1]);
            }
            
        }catch(RserveException | REXPMismatchException Ex){           
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, Ex);
        }
    }//GEN-LAST:event_jButtonEx1ActionPerformed

    public void AfficherAnalysePvalue(Double pvalue) {
        if (pvalue < 0.05) {
            System.out.println(">>> Il y a une différence significative entre les populations");
            jTextAreaConclusion.setText(jTextAreaConclusion.getText() + "\n>>> Il y a une différence significative entre les populations");
        }
        else {
            System.out.println(">>> Il n'y a pas de différence significative entre les populations");
            jTextAreaConclusion.setText(jTextAreaConclusion.getText() + "\n>>> Il n'y a pas de différence significative entre les populations");
        }
    }
    
    public void AfficherAnalysePvalue(Double pvalue, String pop1, String pop2){
        System.out.println("Populations : " + pop1 + " et " + pop2 + " => p-value = " + pvalue);
        jTextAreaConclusion.setText(jTextAreaConclusion.getText() + "p-value = " + pvalue);
        if (pvalue < 0.05) {
            System.out.println(">>> Il y a une différence significative entre les deux populations");
            jTextAreaConclusion.setText(jTextAreaConclusion.getText() + "\n>>> Il y a une différence significative entre les deux populations");
        }
        else {
            System.out.println(">>> Il n'y a pas de différence significative entre les deux populations");
            jTextAreaConclusion.setText(jTextAreaConclusion.getText() + "\n>>> Il n'y a pas de différence significative entre les deux populations\n\nConclusion : Seules la population " + pop1 + " et " + pop2 + " sont similaires");
        }
    }
    
    private void jButtonEx2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEx2ActionPerformed
        int NbValues, NbLevels1 , NbLevels2;
        List<Double> list = new ArrayList<>();
        String CurrentLevel1 = null, Temp1 = null ,CurrentLevel2 = null , Temp2= null ;
        String[] Headers;
        DefaultBoxAndWhiskerCategoryDataset ds = new DefaultBoxAndWhiskerCategoryDataset();
        Double pvalue = null ;
        
        jInternalFrameGraphique.setVisible(true);
        DeleteValButton.setEnabled(true);
        jTextAreaConclusion.setText(null);

        try
        {
            //Temp2=Deuxieme colonne Idem NbLevels2
            // Path Zeydax :
            // Path Doublon : 
            CRS.getRConnexion().voidEval("data<-read.table(\"C:/Users/Doublon/Desktop/R_jar/2_Batracie.csv\",h=TRUE,sep=\";\")"); 
            Headers = CRS.getRConnexion().eval("colnames(data)").asStrings();  
            NbValues = CRS.getRConnexion().eval("length(data$" + Headers[1] + ")").asInteger();
            NbLevels1 = CRS.getRConnexion().eval("length(levels(data$" + Headers[0] + "))").asInteger();
            NbLevels2 = CRS.getRConnexion().eval("length(levels(data$" + Headers[1] + "))").asInteger();
            CurrentLevel1 = CRS.getRConnexion().eval("data$" + Headers[0] + "[1]").asString();     
            CurrentLevel2 = CRS.getRConnexion().eval("data$" + Headers[1] + "[1]").asString(); 
            
            //Recup VAL
            for (int j = 1 ; j <= NbValues ; j++) 
            {
                Temp1 = CRS.getRConnexion().eval("data$" + Headers[0] + "[" + j + "]").asString();
                Temp2 = CRS.getRConnexion().eval("data$" + Headers[1] + "[" + j + "]").asString();
                if(j != 1 && !Temp1.equals(CurrentLevel1))
                {
                    //On ajoute dans le DateSet sous forme : Val,Molecules,Administration => l'inverse de notre fichier.csv
                    ds.add(list, CurrentLevel2, CurrentLevel1);
                    CurrentLevel1=Temp1;
                    if(!Temp2.equals(CurrentLevel2))
                    {
                        CurrentLevel2 = Temp2;
                    }
                    list.clear();
                }
                list.add(CRS.getRConnexion().eval("data$" + Headers[2] + "[" + j + "]").asDouble());           
            }        
            ds.add(list, CurrentLevel2 ,CurrentLevel1);
            
            //Construction du Grahique
            JFreeChart jfc = ChartFactory.createBoxAndWhiskerChart("Exercice 2", Headers[1]+Headers[0], Headers[2], ds, false);
            CategoryPlot plot = (CategoryPlot) jfc.getCategoryPlot();
            BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();        
            ChartPanel cp = new ChartPanel(jfc);
            jInternalFrameGraphique.setContentPane(cp);
            jInternalFrameGraphique.setVisible(true);
            
            //Conclusion
            CRS.getRConnexion().voidEval("model<-lm(data$" + Headers[2] + "~" +"data$"+ Headers[0] + "* data$"+Headers[1]+" )");
            CRS.getRConnexion().voidEval("aov<-anova(model)");
            CRS.getRConnexion().voidEval("temp<-aov$\"Pr(>F)\"");
            
            pvalue =CRS.getRConnexion().eval("temp[3]").asDouble();
            jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"p-value de l'interaction = " + pvalue+"\n");
            if(pvalue<0.05)
            {
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Vu que la p_value est inferieur a 5% , nous pouvons dire qu'il n'y pas d'interaction siginificative.\n");
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"C'est-a-dire qu'on ne sait pas dire si : "+Headers[0]+" et/ou : " +Headers[1]+" ont une influence soit une soit les deux!\n" );
            }
            else
            {
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Vu que la p_value est superieur a 5% , nous pouvons dire qu'il y a une d'interaction siginificative.\n");
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"C'est-a-dire qu'on peut dire que : "+Headers[0]+" et : " +Headers[1]+" ont une influence sur les resultats!\n" );   
            }
        }
        catch(RserveException | REXPMismatchException Ex)
        {           
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, Ex);
        }
    }//GEN-LAST:event_jButtonEx2ActionPerformed

    private void DeleteValButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteValButtonActionPerformed
        int NbValues, NbLevels1 , NbLevels2;
        List<Double> list = new ArrayList<>();
        String CurrentLevel1 = null, Temp1 = null ,CurrentLevel2 = null , Temp2= null ;
        String[] Headers;
        DefaultBoxAndWhiskerCategoryDataset ds = new DefaultBoxAndWhiskerCategoryDataset();
        Double pvalue = null ;
        
        DeleteValButton.setEnabled(true);
        jTextAreaConclusion.setText(null);

        try
        {
            //Temp2=Deuxieme colonne Idem NbLevels2
            //Path Zeydax :
            //Path Doublon :
            CRS.getRConnexion().voidEval("data<-read.table(\"C:/Users/Doublon/Desktop/R_jar/2_Batracie.csv\",h=TRUE,sep=\";\")"); 
            //Retrait de la valeur
            CRS.getRConnexion().voidEval("data<-data[-44,]");
            Headers = CRS.getRConnexion().eval("colnames(data)").asStrings();  
            NbValues = CRS.getRConnexion().eval("length(data$" + Headers[1] + ")").asInteger();
            NbLevels1 = CRS.getRConnexion().eval("length(levels(data$" + Headers[0] + "))").asInteger();
            NbLevels2 = CRS.getRConnexion().eval("length(levels(data$" + Headers[1] + "))").asInteger();
            CurrentLevel1 = CRS.getRConnexion().eval("data$" + Headers[0] + "[1]").asString();     
            CurrentLevel2 = CRS.getRConnexion().eval("data$" + Headers[1] + "[1]").asString(); 
            
            //Recup VAL
            for (int j = 1 ; j <= NbValues ; j++) 
            {
                Temp1 = CRS.getRConnexion().eval("data$" + Headers[0] + "[" + j + "]").asString();
                Temp2 = CRS.getRConnexion().eval("data$" + Headers[1] + "[" + j + "]").asString();
                if(j != 1 && !Temp1.equals(CurrentLevel1))
                {
                    //On ajoute dans le DateSet sous forme : Val,Molecules,Administration => l'inverse de notre fichier.csv
                    ds.add(list, CurrentLevel2, CurrentLevel1);
                    CurrentLevel1=Temp1;
                    if(!Temp2.equals(CurrentLevel2))
                    {
                        CurrentLevel2 = Temp2;
                    }
                    list.clear();
                }
                list.add(CRS.getRConnexion().eval("data$" + Headers[2] + "[" + j + "]").asDouble());           
            }        
            ds.add(list, CurrentLevel2 ,CurrentLevel1);
            
            //Construction du Grahique
            JFreeChart jfc = ChartFactory.createBoxAndWhiskerChart("Exercice 2", Headers[1]+Headers[0], Headers[2], ds, false);
            CategoryPlot plot = (CategoryPlot) jfc.getCategoryPlot();
            BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();        
            ChartPanel cp = new ChartPanel(jfc);
            jInternalFrameGraphique.setContentPane(cp);
            jInternalFrameGraphique.setVisible(true);
            
            //Conclusion
            CRS.getRConnexion().voidEval("model<-lm(data$" + Headers[2] + "~" +"data$"+ Headers[0] + "* data$"+Headers[1]+" )");
            CRS.getRConnexion().voidEval("aov<-anova(model)");
            CRS.getRConnexion().voidEval("temp<-aov$\"Pr(>F)\"");
            
            pvalue =CRS.getRConnexion().eval("temp[3]").asDouble();
            jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"p-value de l'interaction = " + pvalue+"\n");
            if(pvalue<0.05)
            {
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Vu que la p_value est inferieur a 5% , nous pouvons dire qu'il n'y pas d'interaction siginificative.\n");
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"C'est-a-dire qu'on ne sait pas dire si : "+Headers[0]+" et/ou : " +Headers[1]+" ont une influence soit une soit les deux!\n" );
            }
            else
            {
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Vu que la p_value est superieur a 5% , nous pouvons dire qu'il y a une d'interaction siginificative.\n");
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"C'est-a-dire qu'on peut dire que : "+Headers[0]+" et : " +Headers[1]+" ont une influence sur les resultats!\n" );   
            }
        }
        catch(RserveException | REXPMismatchException Ex)
        {           
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, Ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteValButtonActionPerformed

    private void jButtonEx3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEx3ActionPerformed
        String[] Headers,HeadersTemp;
        Double H0,Coef,p_value;
        double [] t = null;
        jInternalFrameGraphique.setVisible(false);
        jTextAreaConclusion.setText(null);
        try
        { 
            //Path Zeydax :
            // Path Doublon : 
            CRS.getRConnexion().voidEval("data<-read.table(\"C:/Users/Doublon/Desktop/R_jar/3_Demenagement.csv\",h=TRUE,sep=\";\")");
            Headers = CRS.getRConnexion().eval("colnames(data)").asStrings(); 
            CRS.getRConnexion().voidEval("test<-lm(data$" +Headers[0]+ "~data$" +Headers[1]+ "+data$" +Headers[2]+ ")");
            H0=CRS.getRConnexion().eval("summary(test)$coefficients[,\"Pr(>|t|)\"]").asDouble();
            Coef=CRS.getRConnexion().eval("summary(test)$r.squared").asDouble();
            jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"p-value de H0 = " + H0+"\n");
            jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Coéficient de corrélation = " + Coef+"\n");
            if(H0<0.05)
            {
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"les tests de signification sur les coefficients indiquent un rejet de l'hypothèse nulle, à 5%\n");;
                CRS.getRConnexion().voidEval("fstat<-summary(test)$fstatistic");
                p_value=CRS.getRConnexion().eval("pf(fstat[1],fstat[2],fstat[3],lower.tail=FALSE)").asDouble();
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"p-value = " + p_value+"\n");
                if(p_value<0.05)
                {
                    jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"le test de signification de la régression nous fait rejeter l'hypothèse nulle, à 5%\n");
                    jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"et nous conduit à accepter l'existence d'une régression fondée\n");
                }
                 jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"\n");
                 CRS.getRConnexion().voidEval("test<-lm(data$temps~data$volume+data$nombre.de.grandes.pieces-1)");
                 t=CRS.getRConnexion().eval("summary(test)$coefficients[,\"Pr(>|t|)\"]").asDoubles();
                 for(int i = 0 ; i<t.length;i++)
                 {
                     jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"t : " +Headers[i]+ " : " +t[i]+ "\n");
                     if(t[i]>0.05)
                     {
                         jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Comme le terme "+Headers[i]+" est peu significatif on peut passer au sous-modèle qui ne l'utilise pas\n");
                     }
                     else
                        jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Comme le terme "+Headers[i]+" significatif on peut le garder pour une eventuel sous-modèle\n");
                 }

                 Coef=CRS.getRConnexion().eval("summary(test)$r.squared").asDouble();
                 jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Nouveau coéficient de corrélation = " + Coef+"\n");
                 CRS.getRConnexion().voidEval("fstat<-summary(test)$fstatistic");
                 p_value=CRS.getRConnexion().eval("pf(fstat[1],fstat[2],fstat[3],lower.tail=FALSE)").asDouble();
                 jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"p-value = " + p_value+"\n");
                 if(p_value<0.05)
                 {
                     jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"le test de signification de la régression nous fait rejeter l'hypothèse nulle, à 5%\n");
                     jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"et nous conduit à accepter l'existence d'une régression fondée\n");
                 }                     
            }
            else
            {
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"les tests de signification sur les coefficients indiquent une acceptation de l'hypothèse nulle, à 5%\n");
                p_value=CRS.getRConnexion().eval("pf(fstat[1],fstat[2],fstat[3],lower.tail=FALSE)").asDouble();
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"p-value = " + p_value+"\n");
                if(p_value<0.05)
                {
                    jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"le test de signification de la régression nous fait rejeter l'hypothèse nulle, à 5%\n");
                    jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"et nous conduit à accepter l'existence d'une régression fondée\n");
                }
            }
        } 
        catch (RserveException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (REXPMismatchException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonEx3ActionPerformed

    private void jButtonEx4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEx4ActionPerformed
        String[] Headers,HeadersTemp;
        Double H0,CoefX,CoefY,p_value;
        double [] t = null;
        jInternalFrameGraphique.setVisible(false);
        jTextAreaConclusion.setText(null);
        
        try 
        {
            CRS.getRConnexion().voidEval("data<-read.table(\"C:/Users/Doublon/Desktop/R_jar/RdtFromage.txt\",h=TRUE)");
            Headers = CRS.getRConnexion().eval("colnames(data)").asStrings();
            System.out.println(Headers[16]);
            for(int i=0 ; i<3 ; i++)
            {
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"TEST SUR : "+Headers[i]+"\n");
                CRS.getRConnexion().voidEval("regression<-lm(data$"+Headers[i]+"~data$"+Headers[Headers.length-1]+")");
                CRS.getRConnexion().voidEval("test<-summary(regression)");
                t=CRS.getRConnexion().eval("test$coefficients[,\"Pr(>|t|)\"]").asDoubles();
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"valeurs des tests de signification sur les coefficients : "+t[0]+" et : "+t[1]+"\n");
                CRS.getRConnexion().voidEval("fstat<-test$fstatistic");
                p_value=CRS.getRConnexion().eval("pf(fstat[1],fstat[2],fstat[3],lower.tail=FALSE)").asDouble();
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"p_value du test : "+p_value+"\n");
                CRS.getRConnexion().voidEval("droite<-test$coefficients");
                CoefY=CRS.getRConnexion().eval("test$coefficients[1]").asDouble();
                CoefX=CRS.getRConnexion().eval("test$coefficients[2]").asDouble();
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Equation de la droite de régréssion : y = "+CoefX+"x + "+CoefY+"\n");
                CRS.getRConnexion().voidEval("newdata<-data.frame(data$"+Headers[i]+",data$"+Headers[Headers.length-1]+")");
                CRS.getRConnexion().voidEval("correlation<-cor(newdata)");
                CoefX=CRS.getRConnexion().eval("correlation[2]").asDouble();
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"Coéfficient de corrélation de "+CoefX+"\n");
                jTextAreaConclusion.setText(jTextAreaConclusion.getText()+"\n");
            }
        } 
        catch (RserveException | REXPMismatchException ex) 
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }//GEN-LAST:event_jButtonEx4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(new ConnexionRServe()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteValButton;
    private javax.swing.JButton jButtonConnexion;
    private javax.swing.JButton jButtonEx1;
    private javax.swing.JButton jButtonEx2;
    private javax.swing.JButton jButtonEx3;
    private javax.swing.JButton jButtonEx4;
    private javax.swing.JInternalFrame jInternalFrameGraphique;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaConclusion;
    // End of variables declaration//GEN-END:variables
}
