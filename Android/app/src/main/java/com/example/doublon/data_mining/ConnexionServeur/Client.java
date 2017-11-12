/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.doublon.data_mining.ConnexionServeur;

import requetepoolthreads.Reponse;
import requetepoolthreads.Requete;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Philippe
 */
public class Client {
    private static int Port;
    private static InetAddress IP;
    private static Socket cliSocket = null;
    
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;
    
    private Properties Prop = new Properties();
    
    private static String NomUtilisateur;
    private static boolean ConnectedToServer;
    
    public Client()
    {
        ConnectedToServer = false;
    }
    
    
    public void LireProperties() throws IOException
    {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        String nomFichier = System.getProperty("user.dir").split("/dist")[0] + System.getProperty("file.separator")+ "src" + System.getProperty("file.separator") + this.getClass().getPackage().getName()+ System.getProperty("file.separator") + "config.properties";

        try 
        {
            fis = new FileInputStream(nomFichier);
            getProp().load(fis);
            fis.close();
        } 
        catch (FileNotFoundException ex) 
        {            
            fos = new FileOutputStream(nomFichier);

            getProp().setProperty("PORT_BAGAGES", Integer.toString(30042));
            getProp().setProperty("ADRESSEIP", "127.0.0.1");
            
            getProp().store(fos, null);
        } 
        
        if (fis != null || fos != null) 
        {
            setPort(Integer.parseInt(getProp().getProperty("PORT_BAGAGES")));            
            setIP(InetAddress.getByName(getProp().getProperty("ADRESSEIP")));
            
            System.out.println("Port : " + getPort());
            System.out.println("IP : " + getIP());
        }
}
    
    public void Connexion() throws IOException
    {
        System.out.println("IP = " + getIP());
        //setCliSocket(new Socket(getIP(), getPort()));
        setCliSocket(new Socket());
        getCliSocket().connect(new InetSocketAddress(getIP(), getPort()), 1000);
        //getCliSocket().setSoTimeout(15);
        //getCliSocket().connect(getCliSocket().getRemoteSocketAddress(), 15);
        
        if (getCliSocket().isConnected()) 
        {
            System.out.println("Connexion OK");
            
            try 
            {        
                System.out.println("Création des flux");
                setOos(new ObjectOutputStream(getCliSocket().getOutputStream()));
                getOos().flush();
                System.out.println("Fin de la création des flux");
            }
            catch(IOException ex) 
            {
                System.out.println(ex.getMessage());
            }
            System.out.println("Client prêt");
            System.out.println("Connected = " + getCliSocket().isConnected());
            setConnectedToServer(true);
        }
        else 
        {            
            System.out.println("Client pas prêt !");
        }
    }

    public void Connexion (InetAddress IP, int Port) throws IOException
    {
        setIP(IP);
        setPort(Port);
        setCliSocket(new Socket(IP, Port));

        if (getCliSocket().isConnected())
        {
            System.out.println("Connexion OK");

            try
            {
                System.out.println("Création des flux");
                setOos(new ObjectOutputStream(getCliSocket().getOutputStream()));
                getOos().flush();
                System.out.println("Fin de la création des flux");
            }
            catch(IOException ex)
            {
                System.out.println(ex.getMessage());
            }
            System.out.println("Client prêt");
            System.out.println("Connected = " + getCliSocket().isConnected());
            setConnectedToServer(true);
        }
        else
        {
            System.out.println("Client pas prêt !");
        }
    }

    public static String Deconnexion(Requete Req)
    {
        Reponse Rep;
        
        EnvoyerRequete(Req);
        Rep = RecevoirReponse();
        
        if (Rep != null)
        {
            if (Rep.getCode() == Reponse.LOG_OUT_OK)
            {
                try 
                {
                    getOis().close();
                    ois = null;
                    getOos().close();
                    oos = null;
                    getCliSocket().close();
                    cliSocket = null;
                    NomUtilisateur = "";
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                }
                ConnectedToServer = false;

                return null;
            }
            else
            {
                return Rep.getChargeUtile().get("Message").toString();
            }
        }
        else
        {
            ConnectedToServer = false;
            return null;
        }
    }
    
    public Reponse Authenfication(Requete Req, String Login, String Password) throws IOException, NoSuchAlgorithmException, NoSuchProviderException
    {
        Reponse Rep = null;

        if (!isConnectedToServer())
            Connexion();
        
        Security.addProvider(new BouncyCastleProvider());

        System.out.println("Instanciation du message digest");

        Req.getChargeUtile().put("Login", Login);

        MessageDigest md = MessageDigest.getInstance("SHA-256", "BC");

        md.update(Login.getBytes());
        md.update(Password.getBytes()); 

        long Temps = (new Date()).getTime();
        double Random = Math.random();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream bdos = new DataOutputStream(baos);
        bdos.writeLong(Temps); bdos.writeDouble(Random);
        md.update(baos.toByteArray());
        byte[] msgD = md.digest();

        Req.getChargeUtile().put("Temps", Temps);
        Req.getChargeUtile().put("Random", Random);
        Req.getChargeUtile().put("Digest", msgD);         

        System.out.println("Req = " + Req.getNomTypeRequete());
        EnvoyerRequete(Req);
        Rep = RecevoirReponse();
        
        return Rep;
    }
    
    public static void EnvoyerRequete(Requete Req)
    {
        try 
        {            
            getOos().writeObject(Req);
            getOos().flush();
        } 
        catch (IOException ex) 
        {
            ConnectedToServer = false;
        }
    }
    
    public static Reponse RecevoirReponse()
    {
        Reponse Rep = null;
        
        try 
        {
            if (getOis() == null)
                ois = new ObjectInputStream(getCliSocket().getInputStream());
            
            Rep = (Reponse) getOis().readObject();
        } 
        catch (IOException ex) 
        {
            ConnectedToServer = false;
            Rep = null;
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Rep;
    }
                
        
    // Getters - Setters
    public static ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public static ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int Port) {
        this.Port = Port;
    }

    public InetAddress getIP() {
        return IP;
    }

    public void setIP(InetAddress IP) {
        this.IP = IP;
    }

    public static synchronized Socket getCliSocket() {
        return cliSocket;
    }

    public synchronized void setCliSocket(Socket cS) {
        cliSocket = cS;
    }

    public Properties getProp() {
        return Prop;
    }

    public void setProp(Properties Prop) {
        this.Prop = Prop;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String NomUtilisateur) {
        this.NomUtilisateur = NomUtilisateur;
    }

    public boolean isConnectedToServer() {
        return ConnectedToServer;
    }

    public void setConnectedToServer(boolean ConnectedToServer) {
        this.ConnectedToServer = ConnectedToServer;
    }
    
    
}
