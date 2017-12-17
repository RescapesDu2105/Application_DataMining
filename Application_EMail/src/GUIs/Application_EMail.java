/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import application_email.BoxMailsCellRenderer;
import application_email.ThreadReception;
import application_email.Utilisateur;
import java.awt.CardLayout;
import java.awt.Color;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Philippe
 */
public class Application_EMail extends javax.swing.JFrame
{
    private LoginFrame loginFrame;
    private final Utilisateur user;
    private ThreadReception thread;
    
    
    
    /**
     * Creates new form Application_EMail
     */
    public Application_EMail()
    {
        initComponents();        
        jPanel_Central.setLayout(new CardLayout());
        jList_Emails.setCellRenderer(new BoxMailsCellRenderer());
        
        user = new Utilisateur();
        
        loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }

    
    
    public void Connect(final String User, final String Pwd) throws NoSuchProviderException, MessagingException, GeneralSecurityException 
    {
        Store st;
                
        try
        {
            user.setMailSession(Session.getDefaultInstance(user.getMailProperties()));
            System.out.println("User = " + User);
            System.out.println("Pwd = " + Pwd);
            st = user.getMailSession().getStore("pop3");
            
            //st.connect(user.getMailProperties().getProperty("mail.pop3.host"), User, Pwd);
            st.connect(user.getMailProperties().getProperty("mail.pop3.host"), User  + "@u2.tech.hepl.local", Pwd);

            user.setAdresseMail(User + "@u2.tech.hepl.local");
            user.setMailStore(st);
            
            this.setTitle(user.getAdresseMail());
        }
        catch(MessagingException ex) 
        {
            ex.printStackTrace();
            System.exit(1); 
        }
    }
    
    public void ChargementEmailsListe()
    {
        if(user.getMailSession() != null)
        {
            try
            {
                user.ChargerEmails();
                if (user.getMessages().isEmpty())
                {
                    jLabel_Error.setText("Aucun email dans la boite");                    
                    DefaultListModel Liste_Emails = new DefaultListModel();                    
                    jList_Emails.setModel(Liste_Emails);
                    
                    /*jPanel_Central.removeAll();
                    jPanel_Central.repaint();
                    jButton_SendMail.setEnabled(true);*/
                }
                else
                {
                    int index = jList_Emails.getSelectedIndex();
                    DefaultListModel Liste_Emails = new DefaultListModel();
                    jList_Emails.setSelectionBackground(new Color(0, 120, 215));

                    user.getMessages().forEach((message) ->
                    {
                        Liste_Emails.addElement(message);
                    });

                    jList_Emails.setModel(Liste_Emails);
                    jList_Emails.repaint();
                    jList_Emails.setSelectedIndex(index);
                    
                    jLabel_Error.setText(user.getMessages().size() + " email(s) dont " + " non lus");
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        else
        {
            try
            {
                DefaultListModel Liste_Emails = new DefaultListModel();
                jList_Emails.setSelectionBackground(new Color(0, 120, 215));
                
                user.setMailSession(Session.getDefaultInstance(user.getMailProperties()));
                Message test = new MimeMessage(user.getMailSession());
                
                Liste_Emails.addElement(test);
                Liste_Emails.addElement(test);
                Liste_Emails.addElement(test);

                jList_Emails.setModel(Liste_Emails);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                System.exit(1);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList_Emails = new javax.swing.JList<>();
        jButton_Refresh = new javax.swing.JButton();
        jPanel_Central = new javax.swing.JPanel();
        jButton_SendMail = new javax.swing.JButton();
        jLabel_Mailbox = new javax.swing.JLabel();
        jLabel_Error = new javax.swing.JLabel();
        jLabel_Notification = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosed(java.awt.event.WindowEvent evt)
            {
                formWindowClosed(evt);
            }
        });

        jList_Emails.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList_Emails.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jList_Emails.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                jList_EmailsMouseDragged(evt);
            }
        });
        jList_Emails.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jList_EmailsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                jList_EmailsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jList_Emails);

        jButton_Refresh.setText("Actualiser");
        jButton_Refresh.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton_RefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_CentralLayout = new javax.swing.GroupLayout(jPanel_Central);
        jPanel_Central.setLayout(jPanel_CentralLayout);
        jPanel_CentralLayout.setHorizontalGroup(
            jPanel_CentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel_CentralLayout.setVerticalGroup(
            jPanel_CentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton_SendMail.setText("Envoyer un mail");
        jButton_SendMail.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton_SendMailActionPerformed(evt);
            }
        });

        jLabel_Mailbox.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel_Mailbox.setText("<html><u>Boite de réception</u></html>");

        jLabel_Error.setForeground(new java.awt.Color(204, 0, 0));
        jLabel_Error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Error.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel_Notification.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_Notification.setForeground(new java.awt.Color(51, 153, 0));
        jLabel_Notification.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Notification.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_Refresh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_SendMail))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel_Mailbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_Error, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_Notification, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel_Central, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Notification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_Refresh)
                        .addComponent(jButton_SendMail)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_Mailbox, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(jLabel_Error, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jPanel_Central, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_RefreshActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_RefreshActionPerformed
    {//GEN-HEADEREND:event_jButton_RefreshActionPerformed
        ChargementEmailsListe();
    }//GEN-LAST:event_jButton_RefreshActionPerformed

    private void jList_EmailsMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jList_EmailsMouseClicked
    {//GEN-HEADEREND:event_jList_EmailsMouseClicked
        //System.out.println("Click = " + jList_Emails.getSelectedIndex());
        
        int index = jList_Emails.getSelectedIndex();
        //System.out.println("jList_Emails.getModel().getElementAt(index)) = " + user.getMessages().get(index).getContent().toString());
        
        if(index >= 0)
        {
            //System.out.println("Email selectionne");
            jPanel_Central.removeAll();
            jPanel_Central.add(new DisplayEmailPanel(this, index));
            jPanel_Central.add(new SendMailPanel(this, null));
            //jPanel_Central.add(new NewJPanel());
        }
    }//GEN-LAST:event_jList_EmailsMouseClicked

    private void jButton_SendMailActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_SendMailActionPerformed
    {//GEN-HEADEREND:event_jButton_SendMailActionPerformed
        jButton_SendMail.setEnabled(false);
        jPanel_Central.removeAll();
        jPanel_Central.add(new SendMailPanel(this, null));
    }//GEN-LAST:event_jButton_SendMailActionPerformed

    private void jList_EmailsMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jList_EmailsMousePressed
    {//GEN-HEADEREND:event_jList_EmailsMousePressed
        //System.out.println("Pressed = " + jList_Emails.getSelectedIndex());
        int index = jList_Emails.locationToIndex(evt.getPoint());
        //System.out.println("InBounds = " + jList_Emails.getCellBounds(index, index).contains(evt.getPoint()));
        if (jList_Emails.getModel().getSize() > 0 && !jList_Emails.getCellBounds(index, index).contains(evt.getPoint())) 
        {
            jList_Emails.clearSelection();
        }
    }//GEN-LAST:event_jList_EmailsMousePressed

    private void jList_EmailsMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jList_EmailsMouseDragged
    {//GEN-HEADEREND:event_jList_EmailsMouseDragged
        //System.out.println("Dragged = " + jList_Emails.getSelectedIndex());
        int index = jList_Emails.locationToIndex(evt.getPoint());
        //System.out.println("InBounds = " + jList_Emails.getCellBounds(index, index).contains(evt.getPoint()));
        if (!jList_Emails.getCellBounds(index, index).contains(evt.getPoint())) 
        {
            jList_Emails.clearSelection();
        }
    }//GEN-LAST:event_jList_EmailsMouseDragged

    private void formWindowClosed(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosed
    {//GEN-HEADEREND:event_formWindowClosed
        getThread().interrupt();
        try
        {
            user.getFolder().close(true);
        }
        catch (MessagingException ex)
        {
            Logger.getLogger(Application_EMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosed

    public LoginFrame getLoginFrame()
    {
        return loginFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame)
    {
        this.loginFrame = loginFrame;
    }   

    public JButton getjButton_Refresh()
    {
        return jButton_Refresh;
    }

    public JButton getjButton_SendMail()
    {
        return jButton_SendMail;
    }

    public JList<String> getjList_Emails()
    {
        return jList_Emails;
    }

    public JPanel getjPanel_Central()
    {
        return jPanel_Central;
    }

    public Utilisateur getUser()
    {
        return user;
    }

    public JLabel getjLabel_Error()
    {
        return jLabel_Error;
    }

    public JLabel getjLabel_Notification()
    {
        return jLabel_Notification;
    }

    public ThreadReception getThread()
    {
        return thread;
    }

    public void setThread(ThreadReception thread)
    {
        this.thread = thread;
    }    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Application_EMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Application_EMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Application_EMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Application_EMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() ->
        {
            Application_EMail frame = new Application_EMail();
            frame.addWindowListener(new java.awt.event.WindowAdapter()
            {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e)
                {
                    System.exit(0);
                }
            });
            //frame.setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Refresh;
    private javax.swing.JButton jButton_SendMail;
    private javax.swing.JLabel jLabel_Error;
    private javax.swing.JLabel jLabel_Mailbox;
    private javax.swing.JLabel jLabel_Notification;
    private javax.swing.JList<String> jList_Emails;
    private javax.swing.JPanel jPanel_Central;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}



