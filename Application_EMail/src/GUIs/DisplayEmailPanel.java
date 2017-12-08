/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import application_email.BoxAttachmentsCellRenderer;
import application_email.ThreadNotification;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
        
        jList_Attachments.setCellRenderer(new BoxAttachmentsCellRenderer());
        DefaultListModel<Part> dlm = new DefaultListModel();
        jList_Attachments.setModel(dlm);
        jList_Attachments.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent evt)
            {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) 
                {
                    int index = list.locationToIndex(evt.getPoint());
                    Part part = (Part) list.getModel().getElementAt(index);
                    
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    try                    
                    {
                        fileChooser.setSelectedFile(new File(part.getFileName()));
                        
                        int returnVal = fileChooser.showSaveDialog(DisplayEmailPanel.this);
                        if (returnVal == JFileChooser.APPROVE_OPTION) 
                        {
                            InputStream is = part.getInputStream();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            int c;
                            while ((c = is.read()) != -1) baos.write(c);
                            baos.flush();
                            FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile() + System.getProperty("file.separator") + part.getFileName());
                            baos.writeTo(fos);   
                            
                            ThreadNotification thread = new ThreadNotification(mainFrame.getjLabel_Notification(), "La pièce jointe a bien été enregistrée !");
                            thread.start();
                        } 
                        else 
                        {
                            System.out.println("Save command cancelled by user");
                        }
                    }
                    catch (MessagingException | IOException ex)
                    {
                        Logger.getLogger(DisplayEmailPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } 
            }
        });
        
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
            if(message.getFrom() != null)
                Expediteur = message.getFrom()[0].toString();
            else
                Expediteur = message.getHeader("Return-Path")[0];
            
            //Expediteur = Expediteur.substring(1, Expediteur.length() - 1);
            Destinataire = mainFrame.getUser().getAdresseMail();            
            Date = message.getReceivedDate() == null ? message.getSentDate() == null ? "Date inconnue" : DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(message.getSentDate()) : DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(message.getReceivedDate());
                        
            jLabel_Subject.setText(Objet);
            jLabel_From.setText(Expediteur);
            jLabel_To.setText(Destinataire);
            jLabel_Date.setText(Date);
            String Texte = null;
            
            Enumeration e = message.getAllHeaders();
            while (e.hasMoreElements())
            {                
                Header h = (Header)e.nextElement();
                if(h.getName().equals(h.getValue()))
                {
                    //System.out.println("name = " + h.getName());
                    Texte = h.getValue();
                    break;
                }
            }
            
            //System.out.println("ContentType = " + message.getContentType());
            if(message.isMimeType("text/plain")) //|| message.isMimeType("text/html")) 
            { //Le mail est juste du texte
                if(Texte == null)
                    jTA_Message.setText(message.getContent().toString());
                else
                    jTA_Message.setText(Texte);
            }
            else    
            { 
                Multipart contenu = (Multipart)message.getContent();
                
                for(int i = 0 ; i < contenu.getCount() ; i++)
                {
                    Part part = contenu.getBodyPart(i);
                    //System.out.println("part = " + part.getDisposition());
                    String disposition = part.getDisposition();
                    
                    if(disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT))
                    {
                        InputStream is = part.getInputStream();
                        DefaultListModel lm = (DefaultListModel) jList_Attachments.getModel();
                        lm.addElement(part);
                        
                    }
                    else
                    {
                        jTA_Message.setText((String) part.getContent());
                    }
                }
            }            
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
        jLabel_To = new javax.swing.JLabel();
        jLabel_Date = new javax.swing.JLabel();
        jLabel_From = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList_Attachments = new javax.swing.JList<>();

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

        jList_Attachments.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jList_Attachments);

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
                        .addGap(131, 131, 131)
                        .addComponent(jButton_Answer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_Delete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_Close)
                        .addGap(0, 183, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelDate)
                            .addComponent(jLabelTo)
                            .addComponent(jLabelFrom)
                            .addComponent(jLabelSubject))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel_Subject, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                            .addComponent(jLabel_From, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_Date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_To, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Delete)
                    .addComponent(jButton_Answer)
                    .addComponent(jButton_Close))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Subject, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFrom)
                            .addComponent(jLabel_From))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTo)
                            .addComponent(jLabel_To))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_Date)
                            .addComponent(jLabelDate)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
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
                mainFrame.ChargementEmailsListe();
                
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
            answerMessage.setRecipient (Message.RecipientType.TO, InternetAddress.getLocalAddress(mainFrame.getUser().getMailSession()));
            answerMessage.setSubject("Re: " + message.getSubject());
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
        parent.add(new SendMailPanel(mainFrame, answerMessage));
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
    private javax.swing.JList<Part> jList_Attachments;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTA_Message;
    // End of variables declaration//GEN-END:variables
}
