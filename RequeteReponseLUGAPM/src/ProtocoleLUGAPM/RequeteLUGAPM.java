/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocoleLUGAPM;

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
public class RequeteLUGAPM implements Requete, Serializable 
{
    private static final long serialVersionUID = 4296258939822015079L;
    
    public final static int REQUEST_LOG_OUT_RAMP_AGENT = 0;
    public final static int REQUEST_LOGIN_RAMP_AGENT = 1;
    public final static int REQUEST_LOAD_LUGAGES = 2;
    
    private int Type;
    private HashMap<String, Object> chargeUtile;
    private Socket SocketClient;
    
    private ReponseLUGAPM Reponse = null;
    private Properties Prop = null;
    
    public RequeteLUGAPM(int Type, HashMap chargeUtile) 
    {
        this.Type = Type;
        this.chargeUtile = chargeUtile;
    }
    
    public RequeteLUGAPM(int Type) 
    {
        this.Type = Type;
        this.chargeUtile = new HashMap<>();
    }
    
    @Override
    public Runnable createRunnable(Properties Prop) 
    {
        setProp(Prop);
        
        switch(getType())
        {
            case REQUEST_LOG_OUT_RAMP_AGENT:
                return new Runnable() 
                {
                    public void run() 
                    {
                        traiteRequeteLogOutRampAgent();
                    }            
                };
                
            case REQUEST_LOGIN_RAMP_AGENT:
                return new Runnable() 
                {
                    public void run() 
                    {
                        traiteRequeteLoginRampAgent();
                    }            
                };
            
            case REQUEST_LOAD_LUGAGES:
                return new Runnable() 
                {
                    public void run() 
                    {
                        traiteRequeteLoadLugages();
                    }            
                };
            
            default : return null;
        }
    }

    
    public void traiteRequeteLogOutRampAgent()
    {
        Reponse = new ReponseLUGAPM(ReponseLUGAPM.LOG_OUT_OK);
        Reponse.getChargeUtile().put("Message", ReponseLUGAPM.LOG_OUT_OK_MESSAGE);
    }
    
    public void traiteRequeteLoginRampAgent()
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
                    Reponse = new ReponseLUGAPM(ReponseLUGAPM.LOGIN_OK);
                    Reponse.getChargeUtile().put("Message", ReponseLUGAPM.LOGIN_OK_MESSAGE);
                    Reponse.getChargeUtile().put("Nom", Champs[1]);
                    Reponse.getChargeUtile().put("Prenom", Champs[2]);
                }
                else 
                {
                    Reponse = new ReponseLUGAPM(ReponseLUGAPM.LOGIN_KO);
                    Reponse.getChargeUtile().put("Message", ReponseLUGAPM.WRONG_USER_PASSWORD_MESSAGE);                        
                    System.out.println(ReponseLUGAPM.WRONG_USER_PASSWORD_MESSAGE);
                }
            } 
            catch (NoSuchAlgorithmException | NoSuchProviderException | IOException ex) 
            {
                Reponse = new ReponseLUGAPM(ReponseLUGAPM.INTERNAL_SERVER_ERROR);
                Reponse.getChargeUtile().put("Message", ReponseLUGAPM.INTERNAL_SERVER_ERROR_MESSAGE);
                System.out.println(ReponseLUGAPM.INTERNAL_SERVER_ERROR_MESSAGE);
            }; 
        }       
        else
        {
            Reponse = new ReponseLUGAPM(ReponseLUGAPM.LOGIN_KO);
            Reponse.getChargeUtile().put("Message", ReponseLUGAPM.WRONG_USER_PASSWORD_MESSAGE);                        
            System.out.println(ReponseLUGAPM.WRONG_USER_PASSWORD_MESSAGE);
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
                RS = BD_airport.Select("SELECT Password, Nom, Prenom FROM bd_airport.agents WHERE Poste = \"Bagagiste\" AND Login = \"" + user + "\"");
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
                Reponse = new ReponseLUGAPM(ReponseLUGAPM.INTERNAL_SERVER_ERROR);
                Reponse.getChargeUtile().put("Message", ReponseLUGAPM.INTERNAL_SERVER_ERROR_MESSAGE);
                System.out.println(ReponseLUGAPM.INTERNAL_SERVER_ERROR_MESSAGE + " : " + ex.getMessage());
            }
        }
        
        BD_airport.Deconnexion();
                
        return Champs;
    }
    
    public void traiteRequeteLoadLugages()
    {
        
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
                Reponse = new ReponseLUGAPM(ReponseLUGAPM.INTERNAL_SERVER_ERROR);
                Reponse.getChargeUtile().put("Message", ReponseLUGAPM.INTERNAL_SERVER_ERROR_MESSAGE);
                System.out.println(ReponseLUGAPM.INTERNAL_SERVER_ERROR_MESSAGE + " : " + Error);
            }                
        }        
        
        return BD_airport;
    }
    
    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public HashMap<String, Object> getChargeUtile() {
        return chargeUtile;
    }

    public void setChargeUtile(HashMap<String, Object> chargeUtile) {
        this.chargeUtile = chargeUtile;
    }

    public Socket getSocketClient() {
        return SocketClient;
    }

    public void setSocketClient(Socket SocketClient) {
        this.SocketClient = SocketClient;
    }

    @Override
    public ReponseLUGAPM getReponse() {
        return Reponse;
    }

    public void setReponse(ReponseLUGAPM Reponse) {
        this.Reponse = Reponse;
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
            case REQUEST_LOG_OUT_RAMP_AGENT : return "REQUEST_LOG_OUT_RAMP_AGENT";
            case REQUEST_LOGIN_RAMP_AGENT: return "REQUEST_LOGIN_RAMP_AGENT";  
            case REQUEST_LOAD_LUGAGES: return "REQUEST_LOAD_LUGAGES";
            default : return null;
        }
    }
}
