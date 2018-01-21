/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocoleSEBATRAP;

import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;
import requetepoolthreads.Requete;

/**
 *
 * @author Philippe
 */
public class RequeteSEBATRAP implements Requete, Serializable{
    
    public final static int REQUEST_PAYER = 1;
        
    private int Type;
    private HashMap<String, Object> chargeUtile;
    private Socket SocketClient;
    
    private ReponseSEBATRAP Reponse = null;
    private Properties Prop = null;

    public RequeteSEBATRAP(int Type, HashMap chargeUtile) 
    {
        this.Type = Type;
        this.chargeUtile = chargeUtile;
    }
    
    public RequeteSEBATRAP(int Type) 
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
            case REQUEST_PAYER:
                return new Runnable() 
                {
                    public void run() 
                    {
                        Payer();
                    }            
                };                
            
            default : return null;
        }
    }    
        
    public void Payer()
    {
        if(getChargeUtile().get("IBAN").equals("BE12 3456 7890"))
        {
            int Argent = 140;
            
            if(Integer.parseInt(getChargeUtile().get("Montant").toString()) > Argent)
            {
                //Pas assez d'argent
                Reponse = new ReponseSEBATRAP(ReponseSEBATRAP.NOT_ENOUGH_MONEY_TO_PAY);
                Reponse.getChargeUtile().put("Message", ReponseSEBATRAP.NOT_ENOUGH_MONEY_TO_PAY_MESSAGE);
            }
            else
            {
                //Payer
                Reponse = new ReponseSEBATRAP(ReponseSEBATRAP.PAYED);
                Reponse.getChargeUtile().put("Message", ReponseSEBATRAP.PAYED_MESSAGE);
            }
        }
        else
        {
            //Inconnu            
            Reponse = new ReponseSEBATRAP(ReponseSEBATRAP.UNKNOWN_IBAN);
            Reponse.getChargeUtile().put("Message", ReponseSEBATRAP.UNKNOWN_IBAN_MESSAGE);
        }
    }
    
    
    @Override
    public ReponseSEBATRAP getReponse() {
        return Reponse;
    }

    public void setReponse(ReponseSEBATRAP Reponse) {
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
            case REQUEST_PAYER: return "REQUEST_PAYER";
            default : return null;
        }
    }
}
