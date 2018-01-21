/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocoleSEBATRAP;

import java.io.Serializable;
import java.util.HashMap;
import requetepoolthreads.Reponse;

/**
 *
 * @author Philippe
 */
public class ReponseSEBATRAP implements Reponse, Serializable {
    
    public final static int NOT_ENOUGH_MONEY_TO_PAY = 401;
    public final static int UNKNOWN_IBAN = 402;
    
    public final static int PAYED = 200;
    
    public final static String NOT_ENOUGH_MONEY_TO_PAY_MESSAGE = "Pas assez d'argent pour payer";
    public final static String UNKNOWN_IBAN_MESSAGE = "Numéro de compte inconnu";
    public final static String PAYED_MESSAGE = "Paiement effectué";
    
    private int Code;
    private HashMap<String, Object> ChargeUtile;

    
    public ReponseSEBATRAP(int Code, HashMap ChargeUtile) 
    {
        setCode(Code);
        setChargeUtile(ChargeUtile);
    }
    
    public ReponseSEBATRAP(int Code) 
    {
        setCode(Code);
        setChargeUtile(new HashMap<>());
    }
    
    @Override
    public int getCode() {
        return this.Code;
    }    
    @Override
    public HashMap getChargeUtile() {
        return this.ChargeUtile;
    }    
    public void setChargeUtile(HashMap ChargeUtile) {
        this.ChargeUtile = ChargeUtile;
    }
    @Override
    public void setCode(int Code) {
        this.Code = Code;
    }
    
}
