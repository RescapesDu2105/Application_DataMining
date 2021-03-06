/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import application_email.PieceAttachee;
import application_email.ThreadNotification;
import application_email.Utilisateur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Philippe
 */
public class SendMailPanel extends javax.swing.JPanel
{
    private final Application_EMail mainFrame;
    private final JPanel parent;
    private final JButton buttonSend;
    private final Utilisateur user;
    private Message answerMessage;
    private final Multipart MP;
    /**
     * Creates new form NewJPanel
     * @param mainFrame
     * @param answerMessage
     */
    public SendMailPanel(Application_EMail mainFrame, Message answerMessage)
    {
        this.mainFrame = mainFrame;
        this.user = mainFrame.getUser();
        this.buttonSend = mainFrame.getjButton_SendMail();
        this.parent = mainFrame.getjPanel_Central();
        this.answerMessage = answerMessage;
        MP = new MimeMultipart();
        
        initComponents();
        
        ActionListener itemListener = (ActionEvent e) -> 
        {
            if(e.getActionCommand().equals("comboBoxChanged") && e.getModifiers() == SwingUtilities.RIGHT)
            {
                File file = (File)jCB_Attachments.getSelectedItem();

                String[] options = new String[] {"Oui", "Non"};
                int Choix = JOptionPane.showOptionDialog(null, "Voulez vous vraiment retirer cette pièce jointe ?", "Retirer la pièce jointe", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

                if(Choix == JOptionPane.YES_OPTION)
                {
                    jCB_Attachments.removeItem(file);
                }
            }                
        };
        
        jCB_Attachments.addActionListener(itemListener);
        
        if(answerMessage != null)
        {
            try
            {
                jTF_SendTo.setText(answerMessage.getReplyTo()[0].toString());
                jTF_Subject.setText(answerMessage.getSubject());
            }
            catch (MessagingException ex)
            {
                Logger.getLogger(SendMailPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        jTF_SendTo = new javax.swing.JTextField();
        jTF_Subject = new javax.swing.JTextField();
        jButton_Add_Attachments = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTA_Message = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel_Subject = new javax.swing.JLabel();
        jCB_Attachments = new javax.swing.JComboBox<>();
        jButton_Send = new javax.swing.JButton();
        jButton_Cancel = new javax.swing.JButton();

        jButton_Add_Attachments.setText("Ajouter une pièce jointe");
        jButton_Add_Attachments.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton_Add_AttachmentsActionPerformed(evt);
            }
        });

        jTA_Message.setColumns(20);
        jTA_Message.setRows(5);
        jScrollPane1.setViewportView(jTA_Message);

        jLabel1.setText("À :");

        jLabel_Subject.setText("Objet :");

        jButton_Send.setText("Envoyer");
        jButton_Send.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton_SendActionPerformed(evt);
            }
        });

        jButton_Cancel.setText("Annuler");
        jButton_Cancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton_CancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton_Add_Attachments)
                        .addGap(18, 18, 18)
                        .addComponent(jCB_Attachments, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel_Subject)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTF_SendTo)
                            .addComponent(jTF_Subject))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(jButton_Send)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Cancel)
                .addContainerGap(205, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Cancel)
                    .addComponent(jButton_Send))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_SendTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_Subject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Subject))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Add_Attachments)
                    .addComponent(jCB_Attachments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_CancelActionPerformed
    {//GEN-HEADEREND:event_jButton_CancelActionPerformed
        parent.removeAll();
        parent.repaint();
        buttonSend.setEnabled(true);
    }//GEN-LAST:event_jButton_CancelActionPerformed

    private void jButton_SendActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_SendActionPerformed
    {//GEN-HEADEREND:event_jButton_SendActionPerformed
        boolean Ok = true;
                
        try 
        {
            if(answerMessage == null)
            {
                answerMessage = new MimeMessage(mainFrame.getUser().getMailSession());
                // From
                answerMessage.setFrom(new InternetAddress(user.getAdresseMail()));
            }
            else 
                answerMessage.getFlags().add(Flag.ANSWERED);
            
            // dimartino@u2.tech.hepl.local
                            
            // To
            
            InternetAddress adresse = new InternetAddress();
            adresse.setAddress(jTF_SendTo.getText());
            /*if(!adresse.toString().split("@")[1].matches("u2.tech.hepl.local"))
                adresse.setAddress("dimartino@u2.tech.hepl.local"); */            
                        
            if (!"".equals(jTF_SendTo.getText()))
                answerMessage.setRecipient(Message.RecipientType.TO, adresse);
            else
            {
                JOptionPane.showMessageDialog(this, "Le champ de l'adresse mail du destinataire ne peut être vide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                Ok = false;
            }                

            if(Ok)
            {
                // Subject
                String Objet = jTF_Subject.getText();
                //System.out.println("Objet = " + Objet);
                if(!"".equals(Objet))
                    answerMessage.setSubject(Objet);
                else
                {                    
                    JOptionPane.showMessageDialog(this, "Le champ de l'objet est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    Ok = false;
                }
            }

            if(Ok)
            {
                MimeBodyPart BP = new MimeBodyPart();
                BP.setText(jTA_Message.getText());
                MP.addBodyPart(BP);

                for(int i = 0 ; i < jCB_Attachments.getItemCount() ; i++)
                {
                    PieceAttachee pa = jCB_Attachments.getItemAt(i);
                    System.out.println("pa = " + pa.getFile().getName());
                    MP.addBodyPart(pa.getPieceAttachee());
                    //answerMessage.addHeader("X-Digest-" + (i+1), Arrays.toString(pa.getDigest()));
                }

                answerMessage.setContent(MP);
                answerMessage.getFlags().add(Flag.RECENT);
                answerMessage.saveChanges();
                
                Transport.send(answerMessage);
                
                ThreadNotification thread = new ThreadNotification(mainFrame.getjLabel_Notification(), "L'email a bien été envoyé");
                thread.start();
                
                parent.removeAll();
                parent.repaint();
                buttonSend.setEnabled(true); 
            }
        }
        catch (AddressException ex) 
        {
            Logger.getLogger(SendMailPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (MessagingException ex)
        {
            Logger.getLogger(SendMailPanel.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_jButton_SendActionPerformed

    private void jButton_Add_AttachmentsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_Add_AttachmentsActionPerformed
    {//GEN-HEADEREND:event_jButton_Add_AttachmentsActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Ajouter une pièce jointe");
        
        int returnVal = fileChooser.showOpenDialog(SendMailPanel.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = fileChooser.getSelectedFile();
            try
            {
                //This is where a real application would save the file.
                //System.out.println("File: " + file.getName());
                PieceAttachee pa = new PieceAttachee(file);
                //jCB_Attachments.addItem(file);
                jCB_Attachments.addItem(pa);
            }
            catch (MessagingException ex)
            {
                Logger.getLogger(SendMailPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else 
        {
            System.out.println("Save command cancelled by user");
        }
    }//GEN-LAST:event_jButton_Add_AttachmentsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Add_Attachments;
    private javax.swing.JButton jButton_Cancel;
    private javax.swing.JButton jButton_Send;
    private javax.swing.JComboBox<PieceAttachee> jCB_Attachments;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_Subject;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTA_Message;
    private javax.swing.JTextField jTF_SendTo;
    private javax.swing.JTextField jTF_Subject;
    // End of variables declaration//GEN-END:variables
}
