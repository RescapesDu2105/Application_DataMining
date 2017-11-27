/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_email;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author Philippe
 */
public class Application_EMail extends javax.swing.JFrame
{
    private LoginFrame loginFrame;
    private Session mailSession;
    private Store mailStore;
    private Properties mailProperties;
    private String user;
    /**
     * Creates new form Application_EMail
     */
    public Application_EMail()
    {
        initComponents();
        
        /*java.net.URL url = ClassLoader.getSystemResource("icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        setIconImage(img);
        */
        loginFrame = new LoginFrame(this);
        this.setVisible(true);
        loginFrame.setVisible(true);
    }

    public Properties LoadProperties()
    {
        //String pathProperties = server + "_prop.txt";
        String pathProperties = "config.properties";
        Properties properties = new Properties();

        try
        {
            FileInputStream Oread = new FileInputStream(pathProperties);
            properties.load(Oread);
        }
        catch(FileNotFoundException ex)
        {
            try 
            {
                FileOutputStream fos = new FileOutputStream(pathProperties);
                
        
                /*switch(server)
                {
                    case "U2" :
                        properties.put("mail.pop3.host", "10.59.26.134");
                        properties.put("mail.pop3.port", "110");
                        properties.put("mail.smtp.host", "10.59.26.134");
                        properties.put("mail.smtp.port", "25");
                        properties.put("mail.disable.top", "true");
                        properties.put("store", "pop3");
                        
                    break;*/
                    //case "Gmail":
                        properties.put("mail.pop3.host", "pop.gmail.com");
                        properties.put("mail.pop3.port", "995");
                        properties.put("mail.pop3.socketFactory.port", "995");
                        properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        properties.put("mail.pop3s.ssl.trust", "pop.gmail.com");
                        
                        properties.put("mail.smtp.host", "smtp.gmail.com");
                        properties.put("mail.smtp.auth", "true");
                        properties.put("mail.smtp.port", "587");
                        properties.put("mail.smtp.socketFactory.port", "465");
                        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        
                        properties.put("store", "pop3s");
                        /*break;
                }*/
                //paramCo.setProperty("MAIL_HOST", "10.59.26.134");
                try 
                {
                    properties.store(fos, null);
                }
                catch (IOException ex1) 
                {
                    System.err.println(ex1.getStackTrace());
                    System.exit(0);
                }
            } 
            catch (FileNotFoundException ex1) 
            {
                System.err.println(ex1.getStackTrace());
                System.exit(0);
            }
        }
        catch(IOException ex)
        {
            System.err.println(ex.getStackTrace());
            System.exit(0);
        }

        return properties;
    }
    
    public void Connect(final String User, final String Pwd) throws NoSuchProviderException, MessagingException, GeneralSecurityException 
    {
        //POP3
        
        Store st;
        setMailProperties(LoadProperties());
        
        setMailSession(Session.getInstance(getMailProperties(), new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(User, Pwd);
            }
        }));
        
        st = getMailSession().getStore("pop3");
        st.connect(getMailProperties().getProperty("mail.pop3.host"), User, Pwd);
        
        setUser(User);
        setMailStore(st);
        
        // IMAPS
        
        /*
        final Properties props = new Properties();
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.setProperty("mail.imaps.host", "imap.gmail.com");
        props.setProperty("mail.imaps.user", User);
        props.setProperty("mail.imaps.password", Pwd);
        props.setProperty("mail.imaps.port", "993");
        props.setProperty("mail.imaps.auth", "true");
        props.setProperty("mail.debug", "true");
        props.put("mail.imaps.starttls.enable", "true");
        props.put("mail.imaps.ssl.socketFactory", sf);
    
        Authenticator auth = new Authenticator() {
           @Override
           public PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(User, Pwd);
           }
       };
    
        setMailSession(Session.getDefaultInstance(props, auth));

        Store store = getMailSession().getStore("imaps");
        store.connect(User, Pwd);
        */
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public LoginFrame getLoginFrame()
    {
        return loginFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame)
    {
        this.loginFrame = loginFrame;
    }

    public Session getMailSession()
    {
        return mailSession;
    }

    public void setMailSession(Session mailSession)
    {
        this.mailSession = mailSession;
    }

    public Store getMailStore()
    {
        return mailStore;
    }

    public void setMailStore(Store mailStore)
    {
        this.mailStore = mailStore;
    }

    public Properties getMailProperties()
    {
        return mailProperties;
    }

    public void setMailProperties(Properties mailProperties)
    {
        this.mailProperties = mailProperties;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
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
    // End of variables declaration//GEN-END:variables
}
