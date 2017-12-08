/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import application_email.BoxCellRenderer;
import application_email.Utilisateur;
import java.awt.CardLayout;
import java.awt.Color;
import java.security.GeneralSecurityException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
    /**
     * Creates new form Application_EMail
     */
    public Application_EMail()
    {
        initComponents();        
        jPanel_Central.setLayout(new CardLayout());
        //jPanel_Central.add(new SendMailPanel(jPanel_Central, jButton_SendMail));
        
        jList_Emails.setCellRenderer(new BoxCellRenderer());
        
        user = new Utilisateur();
        
        /*java.net.URL url = ClassLoader.getSystemResource("icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        setIconImage(img);
        */
        loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }

    
    
    public void Connect(final String User, final String Pwd) throws NoSuchProviderException, MessagingException, GeneralSecurityException 
    {
        Store st;
                
        try
        {
            user.setMailSession(Session.getDefaultInstance(user.getMailProperties()));
            //System.out.println("User = " + User);
            //System.out.println("Pwd = " + Pwd);
            st = user.getMailSession().getStore("pop3");
            st.connect(user.getMailProperties().getProperty("mail.pop3.host"), User, Pwd);

            user.setAdresseMail(User + "@u2.tech.hepl.local");
            user.setMailStore(st);
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
                DefaultListModel Liste_Emails = new DefaultListModel();
                jList_Emails.setSelectionBackground(new Color(0, 120, 215));

                user.getMessages().forEach((message) ->
                {
                    Liste_Emails.addElement(message);
                });

                jList_Emails.setModel(Liste_Emails);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
            .addGap(0, 546, Short.MAX_VALUE)
        );
        jPanel_CentralLayout.setVerticalGroup(
            jPanel_CentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 596, Short.MAX_VALUE)
        );

        jButton_SendMail.setText("Envoyer un mail");
        jButton_SendMail.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton_SendMailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton_Refresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_SendMail))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Central, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Refresh)
                    .addComponent(jButton_SendMail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addComponent(jPanel_Central, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_RefreshActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_RefreshActionPerformed
    {//GEN-HEADEREND:event_jButton_RefreshActionPerformed
        
    }//GEN-LAST:event_jButton_RefreshActionPerformed

    private void jList_EmailsMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jList_EmailsMouseClicked
    {//GEN-HEADEREND:event_jList_EmailsMouseClicked
        System.out.println("Click = " + jList_Emails.getSelectedIndex());
        
        int index = jList_Emails.getSelectedIndex();
        //System.out.println("jList_Emails.getModel().getElementAt(index)) = " + user.getMessages().get(index).getContent().toString());
        
        if(index >= 0)
        {
            
            System.out.println("Email selectionne");
            jPanel_Central.removeAll();
            jPanel_Central.add(new DisplayEmailPanel(this, index));
            jPanel_Central.add(new SendMailPanel(jPanel_Central, jButton_SendMail, null));
            //jPanel_Central.add(new NewJPanel());
        }
    }//GEN-LAST:event_jList_EmailsMouseClicked

    private void jButton_SendMailActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton_SendMailActionPerformed
    {//GEN-HEADEREND:event_jButton_SendMailActionPerformed
        jButton_SendMail.setEnabled(false);
        jPanel_Central.removeAll();
        jPanel_Central.add(new SendMailPanel(jPanel_Central, jButton_SendMail, null));
    }//GEN-LAST:event_jButton_SendMailActionPerformed

    private void jList_EmailsMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jList_EmailsMousePressed
    {//GEN-HEADEREND:event_jList_EmailsMousePressed
        System.out.println("Pressed = " + jList_Emails.getSelectedIndex());
        int index = jList_Emails.locationToIndex(evt.getPoint());
        System.out.println("InBounds = " + jList_Emails.getCellBounds(index, index).contains(evt.getPoint()));
        if (!jList_Emails.getCellBounds(index, index).contains(evt.getPoint())) 
        {
            jList_Emails.clearSelection();
        }
    }//GEN-LAST:event_jList_EmailsMousePressed

    private void jList_EmailsMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jList_EmailsMouseDragged
    {//GEN-HEADEREND:event_jList_EmailsMouseDragged
        System.out.println("Dragged = " + jList_Emails.getSelectedIndex());
        int index = jList_Emails.locationToIndex(evt.getPoint());
        System.out.println("InBounds = " + jList_Emails.getCellBounds(index, index).contains(evt.getPoint()));
        if (!jList_Emails.getCellBounds(index, index).contains(evt.getPoint())) 
        {
            jList_Emails.clearSelection();
        }
    }//GEN-LAST:event_jList_EmailsMouseDragged

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
    private javax.swing.JList<String> jList_Emails;
    private javax.swing.JPanel jPanel_Central;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}



