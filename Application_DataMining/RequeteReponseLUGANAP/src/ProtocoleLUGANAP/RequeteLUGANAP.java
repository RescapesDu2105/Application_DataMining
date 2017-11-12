/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocoleLUGANAP;

import database.utilities.Bean_DB_Access;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import requetepoolthreads.Requete;

/**
 *
 * @author Philippe
 */
public class RequeteLUGANAP implements Requete, Serializable
{
    public final static int REQUEST_LOG_OUT_ANALYST = 0;
    public final static int REQUEST_LOGIN_ANALYST = 1;
    public final static int REQUEST_INIT = 2;
        
    private int Type;
    private HashMap<String, Object> chargeUtile;
    private Socket SocketClient;
    
    private ReponseLUGANAP Reponse = null;
    private Properties Prop = null;

    public RequeteLUGANAP(int Type, HashMap chargeUtile) 
    {
        this.Type = Type;
        this.chargeUtile = chargeUtile;
    }
    
    public RequeteLUGANAP(int Type) 
    {
        this.Type = Type;
        this.chargeUtile = new HashMap<>();
    }
    
    @Override
    public Runnable createRunnable(Properties Prop) 
    //public Runnable createRunnable(HashMap<String, Object> Tab) 
    {
        setProp(Prop);
        
        switch(getType())
        {
            case REQUEST_LOG_OUT_ANALYST:
                return new Runnable() 
                {
                    public void run() 
                    {
                        traiteRequeteLogOutAnalyst();
                    }            
                };
                
            case REQUEST_LOGIN_ANALYST:
                return new Runnable() 
                {
                    public void run() 
                    {
                        traiteRequeteLoginAnalyst();
                    }            
                };               
            
            default : return null;
        }
    }    
    
    private void traiteRequeteLogOutAnalyst() 
    {
        Reponse = new ReponseLUGANAP(ReponseLUGANAP.LOG_OUT_OK);
        Reponse.getChargeUtile().put("Message", ReponseLUGANAP.LOG_OUT_OK_MESSAGE);
    }
    
    private void traiteRequeteLoginAnalyst() 
    {        
        String user = getChargeUtile().get("Login").toString();
        long Temps = (long) getChargeUtile().get("Temps");
        double Random = (double) getChargeUtile().get("Random");
        byte[] msgD = (byte[]) getChargeUtile().get("Digest");

        // JBDC
        String[] Champs = ChercheMotdePasse(user);
        if (Champs != null) 
        {
            MessageDigest md;
            try 
            {
                Security.addProvider(new BouncyCastleProvider());   

                md = MessageDigest.getInstance("SHA-256", "BC");                    
                md.update(user.getBytes());
                md.update(Champs[0].getBytes());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream bdos = new DataOutputStream(baos);
                bdos.writeLong(Temps);
                bdos.writeDouble(Random);
                md.update(baos.toByteArray());
                byte[] msgDLocal = md.digest();

                if (MessageDigest.isEqual(msgD, msgDLocal)) 
                {
                    Reponse = new ReponseLUGANAP(ReponseLUGANAP.LOGIN_OK);
                    Reponse.getChargeUtile().put("Message", ReponseLUGANAP.LOGIN_OK_MESSAGE);
                    Reponse.getChargeUtile().put("Nom", Champs[1]);
                    Reponse.getChargeUtile().put("Prenom", Champs[2]);
                }
                else 
                {
                    Reponse = new ReponseLUGANAP(ReponseLUGANAP.LOGIN_KO);
                    Reponse.getChargeUtile().put("Message", ReponseLUGANAP.WRONG_USER_PASSWORD_MESSAGE);                        
                    System.out.println(ReponseLUGANAP.WRONG_USER_PASSWORD_MESSAGE);
                }
            } 
            catch (NoSuchAlgorithmException | NoSuchProviderException | IOException ex) 
            {
                Reponse = new ReponseLUGANAP(ReponseLUGANAP.INTERNAL_SERVER_ERROR);
                Reponse.getChargeUtile().put("Message", ReponseLUGANAP.INTERNAL_SERVER_ERROR_MESSAGE);
                System.out.println(ReponseLUGANAP.INTERNAL_SERVER_ERROR_MESSAGE);
            }; 
        }       
        else
        {
            Reponse = new ReponseLUGANAP(ReponseLUGANAP.LOGIN_KO);
            Reponse.getChargeUtile().put("Message", ReponseLUGANAP.WRONG_USER_PASSWORD_MESSAGE);                        
            System.out.println(ReponseLUGANAP.WRONG_USER_PASSWORD_MESSAGE);
        }               
    }
    
    private String[] ChercheMotdePasse(String user) 
    {        
        Bean_DB_Access BD_airport;
        ResultSet RS;
        String[] Champs = null;
        
        BD_airport = Connexion_DB();
        
        if (BD_airport != null)
        {
            try 
            {                        
                RS = BD_airport.Select("SELECT Password, Nom, Prenom FROM bd_airport.agents WHERE Poste = \"Analyste\" AND Login = \"" + user + "\"");
                if (RS != null) 
                {
                    if(RS.next())
                    {
                        Champs = new String[3];
                        Champs[0] = RS.getString("Password");  
                        Champs[1] = RS.getString("Nom");
                        Champs[2] = RS.getString("Prenom");
                    }
                }            
            } 
            catch (SQLException ex) 
            {            
                Reponse = new ReponseLUGANAP(ReponseLUGANAP.INTERNAL_SERVER_ERROR);
                Reponse.getChargeUtile().put("Message", ReponseLUGANAP.INTERNAL_SERVER_ERROR_MESSAGE);
                System.out.println(ReponseLUGANAP.INTERNAL_SERVER_ERROR_MESSAGE + " : " + ex.getMessage());
            }
        }
        
        BD_airport.Deconnexion();
                
        return Champs;
    }
            
    public Bean_DB_Access Connexion_DB()
    {
        Bean_DB_Access BD_airport;
        String Error;
        
        BD_airport = new Bean_DB_Access(Bean_DB_Access.DRIVER_MYSQL, getProp().getProperty("HOST_BD"), getProp().getProperty("PORT_BD"), "Zeydax", "1234", getProp().getProperty("SCHEMA_BD"));
        //BD_airport = new Bean_DB_Access(Bean_DB_Access.DRIVER_MYSQL, "localhost", "3306", "Zeydax", "1234", "bd_airport");
        if (BD_airport != null)
        {
            Error = BD_airport.Connexion();
            if (Error != null)
            {
                Reponse = new ReponseLUGANAP(ReponseLUGANAP.INTERNAL_SERVER_ERROR);
                Reponse.getChargeUtile().put("Message", ReponseLUGANAP.INTERNAL_SERVER_ERROR_MESSAGE);
                System.out.println(ReponseLUGANAP.INTERNAL_SERVER_ERROR_MESSAGE + " : " + Error);
            }                
        }        
        
        return BD_airport;
    }
    
    @Override
    public ReponseLUGANAP getReponse() {
        return Reponse;
    }

    public void setReponse(ReponseLUGANAP Reponse) {
        this.Reponse = Reponse;
    }
    
    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    @Override
    public HashMap getChargeUtile() {
        return chargeUtile;
    }

    public void setChargeUtile(HashMap chargeUtile) {
        this.chargeUtile = chargeUtile;
    }

    public Socket getSocketClient() {
        return SocketClient;
    }

    public void setSocketClient(Socket SocketClient) {
        this.SocketClient = SocketClient;
    }

    public Properties getProp() {
        return Prop;
    }

    public void setProp(Properties Prop) {
        this.Prop = Prop;
    }
    
    @Override
    public String getNomTypeRequete() 
    {
        switch(getType()) 
        {
            case REQUEST_LOG_OUT_ANALYST: return "REQUEST_LOG_OUT_ANALYST";
            case REQUEST_LOGIN_ANALYST: return "REQUEST_LOGIN_ANALYST"; 
            default : return null;
        }
    }
}
