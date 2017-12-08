/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Philippe
 */
public class DisplayEmailPanel extends javax.swing.JPanel
{
    private final Application_EMail mainFrame;
    private final JPanel parent;
    private final JButton buttonSend;
    private final List<Message> messages;
    private final Message message;
    private final JList<String> liste_emails;
    
    /**
     * Creates new form NewJPanel
     * @param mainFrame
     * @param indexMessage
     */
    public DisplayEmailPanel(Application_EMail mainFrame, int indexMessage)
    {
        this.mainFrame = mainFrame;
        this.buttonSend = mainFrame.getjButton_SendMail();
        this.parent = mainFrame.getjPanel_Central();
        this.liste_emails = mainFrame.getjList_Emails();
        this.messages = mainFrame.getUser().getMessages();
        this.message = messages.get(indexMessage);
        
        initComponents();
        
        DisplayMessage();
        
    }

    public void DisplayMessage()
    {
        String Expediteur;
        String Destinataire;
        String Objet;
        String Date;
        try
        {
            Objet = message.getSubject() == null ? "Aucun" : message.getSubject();
            Expediteur = message.getHeader("Return-Path")[0];
            Expediteur = Expediteur.substring(1, Expediteur.length() - 1);
            Destinataire = mainFrame.getUser().getAdresseMail();            
            Date = message.getReceivedDate() == null ? "Date inconnue" : message.getReceivedDate().toString();
            
            /*Enumeration headers = message.getAllHeaders();
            
            while (headers.hasMoreElements()) 
            {
                Header h = (Header) headers.nextElement();
                if(h.getName().equals("Return-Path"))
                {
                    Expediteur = h.getValue().substring(1, h.getValue().length() - 1);
                }
                else if (h.getName().equals("Delivered-To"))
                {
                    Destinataire = h.getValue();
                } 
                //System.out.println("name = " + h.getName());
                //System.out.println("value = " + h.getValue());
            }*/
            
            
            //System.out.println("test = " + message);
            if(message.isMimeType("text/plain")) //|| message.isMimeType("text/html")) 
            { //Le mail est juste du texte
                jTA_Message.setText(message.getContent().toString());
            }
            else    
            { 
                Multipart contenu = (Multipart)message.getContent();
                int nbrDeMorceaux = contenu.getCount();

                for(int cpt = 0; cpt < nbrDeMorceaux; cpt++)
                {
                    Part morceau = contenu.getBodyPart(cpt);

                    //Récupération de l'emplacement de la pièce jointe (dans le mail ou sur un serveur distant)
                    String disposition  = morceau.getDisposition();
                    if(morceau.isMimeType("text/plain")  && disposition == null) //Si c'est du texte
                    {

                        jTA_Message.setText(jTA_Message.getText() + morceau.getContent().toString());

                    }

                    /*if(disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT))
                    {   
                        Path documentRecus = Paths.get(morceau.getFileName());
                        jLabel5.setVisible(true);

                        pieceJointeLabel.setVisible(true);
                        pieceJointeLabel.setText(documentRecus.getFileName().toString());
                        pieceJointeButton.setVisible(true);
                    }*/

                }
            }
            
            jLabel_Subject.setText(Objet);
            jLabel_From.setText(Expediteur);
            jLabel_To.setText(Destinataire);
            //System.out.println("Date = " + Date);
            jLabel_Date.setText(Date);
            jTA_Message.setText((String)message.getContent());
        }
        catch (MessagingException | IOException ex)
        {
            Logger.getLogger(DisplayEmailPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jButton_Answer = new javax.swing.JButton();
        jButton_Delete = new javax.swing.JButton();
        jLabelTo = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTA_Message = new javax.swing.JTextArea();
        jLabelFrom = new javax.swing.JLabel();
        jButton_Close = new javax.swing.JButton();
        jLabelSubject = new javax.swing.JLabel();
        jLabel_Subject = new javax.swing.JLabel();
        jLabel_From = new javax.swing.JLabel();
        jLabel_To = new javax.swing.JLabel();
        jLabel_Date = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(571, 476));

        jButton_Answer.setText("Répondre");
        jButton_Answer.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton_AnswerActionPerformed(evt);
            }
        });

        jButton_Delete.setText("Supprimer");
        jButton_Delete.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton_DeleteActionPerformed(evt);
            }
        });

        jLabelTo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelTo.setText("À :");

        jLabelDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelDate.setText("Envoyé à :");

        jTA_Message.setEditable(false);
        jTA_Message.setColumns(20);
        jTA_Message.setRows(5);
        jScrollPane1.setViewportView(jTA_Message);

        jLabelFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelFrom.setText("De :");

        jButton_Close.setText("Fermer");
        jButton_Close.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton_CloseActionPerformed(evt);
            }
        });

        jLabelSubject.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelSubject.setText("Objet :");

        jLabel_Subject.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jButton_Answer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_Delete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_Close)
                        .addGap(0, 152, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelDate)
                            .addComponent(jLabelTo)
                            .addComponent(jLabelFrom)
                            .addComponent(jLabelSubject))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_To, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_Subject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_Date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_From, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Delete)
                    .addComponent(jButton_Answer)
                    .addComponent(jButton_Close))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Subject, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFrom)
                    .addComponent(jLabel_Date))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTo)
                    .addComponent(jLabel_From))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_To)
                    .addComponent(jLabelDate))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_DeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_DeleteActionPerformed
    {//GEN-HEADEREND:event_jButton_DeleteActionPerformed
               
        //On affiche une boite de dialog pour confirmer la suppression
        String[] options = new String[] {"Oui", "Non"};
        int Choix = JOptionPane.showOptionDialog(null, "Voulez vous vraiment supprimer ce mail ?", "Supprimer l'email", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                
        if(Choix == JOptionPane.YES_OPTION)
        {
            try 
            {
                message.setFlag(Flags.Flag.DELETED, true);
            
                /* Refresh */
                
            } 
            catch (MessagingException ex) 
            {
                Logger.getLogger(DisplayEmailPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton_DeleteActionPerformed

    private void jButton_AnswerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_AnswerActionPerformed
    {//GEN-HEADEREND:event_jButton_AnswerActionPerformed
        Message answerMessage = new MimeMessage(mainFrame.getUser().getMailSession());
        try
        {
            answerMessage.setFrom(new InternetAddress(mainFrame.getUser().getAdresseMail()));
            Address[] adressesMail = new InternetAddress[1];
            adressesMail[0] = new InternetAddress(message.getHeader("Return-Path")[0]);
            answerMessage.setReplyTo(adressesMail);
        }
        catch (AddressException ex)
        {
            Logger.getLogger(DisplayEmailPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (MessagingException ex)
        {
            Logger.getLogger(DisplayEmailPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        buttonSend.setEnabled(false);
        parent.removeAll();
        parent.add(new SendMailPanel(parent, buttonSend, answerMessage));
    }//GEN-LAST:event_jButton_AnswerActionPerformed

    private void jButton_CloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_CloseActionPerformed
    {//GEN-HEADEREND:event_jButton_CloseActionPerformed
        //liste_emails.clearSelection();
        parent.removeAll();
        parent.repaint();
        buttonSend.setEnabled(true);
    }//GEN-LAST:event_jButton_CloseActionPerformed

    public JPanel getParent()
    {
        return parent;
    }

    public JButton getButtonSend()
    {
        return buttonSend;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public Message getMessage()
    {
        return message;
    }

    public JList<String> getListe_emails()
    {
        return liste_emails;
    }


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Answer;
    private javax.swing.JButton jButton_Close;
    private javax.swing.JButton jButton_Delete;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelFrom;
    private javax.swing.JLabel jLabelSubject;
    private javax.swing.JLabel jLabelTo;
    private javax.swing.JLabel jLabel_Date;
    private javax.swing.JLabel jLabel_From;
    private javax.swing.JLabel jLabel_Subject;
    private javax.swing.JLabel jLabel_To;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTA_Message;
    // End of variables declaration//GEN-END:variables
}
