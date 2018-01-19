/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveurpoolthreads;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import requetepoolthreads.ConsoleServeur;

/**
 *
 * @author Philippe
 */
public class Serveur extends Thread{
    private ConsoleServeur GUIApplication;
    private int Port;
    private int MaxThreads;        
    private Properties Prop = null;    
    private PrivateKey privateKeySSL;
    private SSLContext SSLContext;
    private SSLServerSocketFactory SSLSSFactory;
    private SSLServerSocket SSLSSocket;
    
    private ArrayList<ThreadServeur> threads = new ArrayList<>();

    
    public Serveur(ConsoleServeur GUIApplication) 
    {
        this.GUIApplication = GUIApplication;
        try 
        {
            this.Prop = (new ServerProperties()).getProp();
            this.Port = Integer.parseInt(this.Prop.getProperty("PORT"));
            this.MaxThreads = Integer.parseInt(this.Prop.getProperty("MAX_THREADS"));
        } 
        catch (IOException ex) 
        {
            this.GUIApplication.TraceEvenements("serveur#initialisation#failed to read properties file");
            System.exit(1);
        }
    }
       
    
    public void Init() throws IOException
    {  
        KeyStore ServerKs;        
        try
        {
            /*ServerKs = KeyStore.getInstance("JKS");
            String FICHIER_KEYSTORE = "c:\\makecert\\serveur_keystore";
            char[] PASSWD_KEYSTORE = "beaugosseser".toCharArray();
            FileInputStream ServerFK = new FileInputStream (FICHIER_KEYSTORE);
            ServerKs.load(ServerFK, PASSWD_KEYSTORE);
            // 2. Contexte
            SSLContext SslC = SSLContext.getInstance("SSLv3");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            char[] PASSWD_KEY = "sexyser".toCharArray();
            kmf.init(ServerKs, PASSWD_KEY);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ServerKs);
            SslC.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            // 3. Factory
            SSLServerSocketFactory SslSFac= SslC.getServerSocketFactory();
            // 4. Socket
            setSslSSocket((SSLServerSocket) SslSFac.createServerSocket(Port));*/
            
            SSLContext = SSLContext.getInstance("SSLv3");
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(".." + System.getProperty("file.separator") + "ServeurMastercard.jks"), "123Soleil".toCharArray());
            privateKeySSL = (PrivateKey) ks.getKey("TussetDimartino", "123Soleil".toCharArray());
        
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, "123Soleil".toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            SSLContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            SSLSSFactory = SSLContext.getServerSocketFactory();
            SSLSSocket = (SSLServerSocket) SSLSSFactory.createServerSocket(Port);
            
        }
        catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException ex)
        {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        for (int i = 0 ; i < getMaxThreads() ; i++) 
        {
            getThreads().add(new ThreadServeur("Thread du pool n°" + String.valueOf(i + 1), getSSLSSocket(), getGUIApplication(), getProp()));
            getThreads().get(i).start();
        }
    }        
    
    public void Stop() 
    {
        for (int i = 0 ; i < getMaxThreads() ; i++) 
        {
            getThreads().get(i).interrupt();
            try 
            {
                if (getThreads().get(i).getCSocket() != null)
                    getThreads().get(i).getCSocket().close();
                
                getSSLSSocket().close();
                System.out.println("Demande d'arrêt du " + getThreads().get(i).getNom());
            } 
            catch (IOException ex) 
            {
                System.err.println("Erreur socket ! [" + ex.getMessage() + "]");
            }
        }
    }    

    public int getMaxThreads()
    {
        return MaxThreads;
    }

    public void setMaxThreads(int MaxThreads)
    {
        this.MaxThreads = MaxThreads;
    }
    
    public int getPort()
    {
        return Port;
    }

    public void setPort(int Port)
    {
        this.Port = Port;
    }

    public ConsoleServeur getGUIApplication() {
        return GUIApplication;
    }

    public void setGUIApplication(ConsoleServeur GUIApplication) {
        this.GUIApplication = GUIApplication;
    }

    public Properties getProp() {
        return Prop;
    }

    public void setProp(Properties Prop) {
        this.Prop = Prop;
    }

    public ArrayList<ThreadServeur> getThreads()
    {
        return threads;
    }

    public void setThreads(ArrayList<ThreadServeur> threads)
    {
        this.threads = threads;
    }

    public SSLContext getSSLContext()
    {
        return SSLContext;
    }

    public void setSSLContext(SSLContext SSLContext)
    {
        this.SSLContext = SSLContext;
    }

    public PrivateKey getPrivateKeySSL()
    {
        return privateKeySSL;
    }

    public void setPrivateKeySSL(PrivateKey privateKeySSL)
    {
        this.privateKeySSL = privateKeySSL;
    }

    public SSLServerSocketFactory getSSLSSFactory()
    {
        return SSLSSFactory;
    }

    public void setSSLSSFactory(SSLServerSocketFactory SSLSSFactory)
    {
        this.SSLSSFactory = SSLSSFactory;
    }

    public SSLServerSocket getSSLSSocket()
    {
        return SSLSSocket;
    }

    public void setSSLSSocket(SSLServerSocket SSLSSocket)
    {
        this.SSLSSocket = SSLSSocket;
    }
    
}
