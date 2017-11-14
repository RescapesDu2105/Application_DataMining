/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocoleLUGANAP;

import Evaluation1.ConnexionRServe;
import Evaluation1.MainFrame;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;
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
    public final static int REG_CORR_LUG = 3;
    public final static int REG_CORR_LUG_PLUS=4;
    public final static int ANOVA_L_LUG = 6;
    public final static int ANOVA_L_LUG2 = 7;
    
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
                
            case REQUEST_INIT:
                return new Runnable() 
                {
                    public void run() 
                    {
                        traiteRequeteInit();
                    }            
                };
            
            case REG_CORR_LUG:
                return new Runnable()
                {
                    public void run() 
                    {
                        traiteRequeteCorrLug();
                    }                        
                };
            
            case ANOVA_L_LUG :
                return new Runnable()
                {
                    public void run() 
                    {
                        TraiterAnovaLug();
                    }                        
                };
            
            case ANOVA_L_LUG2 :
                return new Runnable()
                {
                    public void run() 
                    {
                        TraiterAnovaLug2();
                    }                        
                };      
                
            case REG_CORR_LUG_PLUS :
                return new Runnable()
                {
                    public void run() 
                    {
                        traiteRequeteCorrLugPlus();
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
    
    public void traiteRequeteInit()
    {
        Bean_DB_Access BD_airport;
        ResultSet RS;
        
        int i = 1;
        
        BD_airport = Connexion_DB();
        
        if (BD_airport != null)
        {
            try
            {                        
                RS = BD_airport.Select("SELECT DISTINCT DATE_FORMAT(HeureDepart, '%m') AS Mois, DATE_FORMAT(HeureDepart, '%Y') AS Annee, NomCompagnie "
                        + "FROM bd_airport.vols natural join bd_airport.Avions Natural Join bd_airport.compagnies "
                        + "ORDER BY Annee, Mois");
                if (RS != null) 
                {
                    Reponse = new ReponseLUGANAP(ReponseLUGANAP.INITIATED);
                    HashMap<String, Object> Annees = new HashMap<>();
                    
                    while(RS.next())
                    {
                        int mois = RS.getInt("Mois");
                        int Annee = RS.getInt("Annee");
                        String NomCompagnie = RS.getString("NomCompagnie");
                        
                        Annees.putIfAbsent(Integer.toString(Annee), new HashMap<>());
                        HashMap<String, Object> Mois = (HashMap<String, Object>) Annees.get(Integer.toString(Annee));
                        Mois.putIfAbsent(Integer.toString(mois), new ArrayList<>());         
                        //System.out.println("Mois = " + Mois);               
                        ArrayList<String> Compagnies = (ArrayList<String>) Mois.get(Integer.toString(mois));
                        Compagnies.add(NomCompagnie);
                        //System.out.println("Compagnies = " + Compagnies);
                        
                        System.out.println("Annees = " + Annees);
                    }
                    
                    Reponse.getChargeUtile().put("Data", Annees);
                }
            }
            catch (SQLException Ex)
            {
                Ex.printStackTrace();
            }
        }
    }
            
    public Bean_DB_Access Connexion_DB()
    {
        Bean_DB_Access BD_airport;
        String Error;
        
        BD_airport = new Bean_DB_Access(Bean_DB_Access.DRIVER_MYSQL, getProp().getProperty("HOST_BD"), getProp().getProperty("PORT_BD"), "Zeydax", "1234", getProp().getProperty("SCHEMA_BD"));
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
 
    private void traiteRequeteCorrLug()
    {
        Bean_DB_Access BD_airport;
        ResultSet RS;
        
        int i = 1;
        
        BD_airport = Connexion_DB();
        String Annee , Compagnie;
        int Mois;
        
        //Annee= getChargeUtile().get("Annee").toString();
       // Mois= getChargeUtile().get("Mois").toString();
        //Compagnie= getChargeUtile().get("Compagnie").toString();
        //if(Mois.equals("Toute l'année"))  
        //if(Compagnie.equals("Toutes les compagnies"))
        /*switch(Mois)
        {
            case "Janvier":
                Mois = "1";
                break;
            case "Février":
                Mois = "2";
                break;
            case "Mars":
                Mois = "3";
                break;
            case "Avril":
                Mois = "4";
                break;
            case "Mai":
                Mois = "5";
                break;
            case "Juin":
                Mois = "6";
                break;
            case "Juillet":
                Mois = "7";
                break;
            case "Août":
                Mois = "8";
                break;
            case "Septembre":
                Mois = "9";
                break;
            case "Octobre":
                Mois = "10";
                break;
            case "Novembre":
                Mois = "11";
                break;
            case "Décembre":
                Mois = "12";
                break;
            default : Mois = "-1";
        }*/
        
        Annee="2017";
        Mois=11;
        Compagnie="AIR FRANCE CANAILLE";
        System.out.println(Annee);
        System.out.println(Mois);
        System.out.println(Compagnie);

        if(BD_airport !=null)
        {
            if(/*!Mois.equals("Toute l'année") && */(!Compagnie.equals("Toutes les compagnies")))
            {
                try 
                {  
                    /*RS = BD_airport.Select("SELECT poids, distance\n" +
                            "FROM Bagages NATURAL JOIN Billets NATURAL JOIN vols NATURAL JOIN avions NATURAL JOIN compagnies\n" +
                            "WHERE extract(YEAR FROM HeureDepart)="+Annee+"\n" +
                            "AND extract(MONTH FROM HeureDepart)="+Mois+"n" +               
                            "AND NomCompagnie = '"+Compagnie+"'");*/
                    RS = BD_airport.Select("SELECT poids, distance\n" +
                            "FROM Bagages NATURAL JOIN Billets NATURAL JOIN vols NATURAL JOIN avions NATURAL JOIN compagnies\n" +
                            "WHERE extract(YEAR FROM HeureDepart)= "+Annee+"\n" +
                            "AND extract(MONTH FROM HeureDepart)="+Mois);
                    //ajouter la compagnie
                        
                if (RS != null) 
                {
                    Reponse = new ReponseLUGANAP(ReponseLUGANAP.REG_CORR_LUG_OK);
                    //ArrayList<Integer> DataCorr = new ArrayList<>();
                    //int[] DataCorr =null ;
                    List DataCorr = new ArrayList(); 
                    while(RS.next())
                    {
                        int Poids = RS.getInt("Poids");
                        int Distance = RS.getInt("Distance");
                        DataCorr.add(Poids);
                        DataCorr.add(Distance);         
                    }
                    
                    Reponse.getChargeUtile().put("Data", DataCorr);
                }
                else
                {
                   System.out.println("cassé"); 
                }
                        
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(RequeteLUGANAP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    
    private void traiteRequeteCorrLugPlus()
    {
        Bean_DB_Access BD_airport;
        ResultSet RS;
        
        int i = 1;
        
        BD_airport = Connexion_DB();
        String Annee , Compagnie;
        int Mois;
        
        //Annee= getChargeUtile().get("Annee").toString();
       // Mois= getChargeUtile().get("Mois").toString();
        //Compagnie= getChargeUtile().get("Compagnie").toString();
        //if(Mois.equals("Toute l'année"))  
        //if(Compagnie.equals("Toutes les compagnies"))
        /*switch(Mois)
        {
            case "Janvier":
                Mois = "1";
                break;
            case "Février":
                Mois = "2";
                break;
            case "Mars":
                Mois = "3";
                break;
            case "Avril":
                Mois = "4";
                break;
            case "Mai":
                Mois = "5";
                break;
            case "Juin":
                Mois = "6";
                break;
            case "Juillet":
                Mois = "7";
                break;
            case "Août":
                Mois = "8";
                break;
            case "Septembre":
                Mois = "9";
                break;
            case "Octobre":
                Mois = "10";
                break;
            case "Novembre":
                Mois = "11";
                break;
            case "Décembre":
                Mois = "12";
                break;
            default : Mois = "-1";
        }*/
        
        Annee="2017";
        Mois=11;
        Compagnie="AIR FRANCE CANAILLE";
        System.out.println(Annee);
        System.out.println(Mois);
        System.out.println(Compagnie);

        if(BD_airport !=null)
        {
            if(/*!Mois.equals("Toute l'année") && */(!Compagnie.equals("Toutes les compagnies")))
            {
                try 
                {  
                    RS = BD_airport.Select("SELECT poids, distance,NbAccompagnants\n" +
                            "FROM Bagages NATURAL JOIN Billets NATURAL JOIN vols NATURAL JOIN avions NATURAL JOIN compagnies\n" +
                            "WHERE extract(YEAR FROM HeureDepart)= "+Annee+"\n" +
                            "AND extract(MONTH FROM HeureDepart)="+Mois);
                    //ajouter la compagnie
                        
                if (RS != null) 
                {
                    //ArrayList<Integer> DataCorr = new ArrayList<>();
                    Reponse = new ReponseLUGANAP(ReponseLUGANAP.REG_CORR_LUG_PLUS_OK);
                    Double[] LPoids =null , LDistance=null , LAccompagant=null;
                    Double P,D,A;
                    //List DataCorr = new ArrayList(); 
                    while(RS.next())
                    {
                        int Poids = RS.getInt("poids");
                        int Distance = RS.getInt("distance");
                        int Accompagant=RS.getInt("NbAccompagnants");
                        
                        LPoids[i]=(double)Poids;
                        LDistance[i]=(double)Distance;
                        LAccompagant[i]=(double)Accompagant;
                        i++;
                        //DataCorr.add(Distance);         
                    }
                    ConnexionRServe CRS = null;
                    Double Coef = null,p_value1 = null,p_value2 = null;
                    double []H0 = null,t = null;
                    
                    CRS.Connexion();
                    try 
                    {
                        CRS.getRConnexion().voidEval("poids <- c()");
                        CRS.getRConnexion().voidEval("distance <- c()");
                        CRS.getRConnexion().voidEval("accompagant <- c()");
                        //Reponse.getChargeUtile().put("Data", DataCorr); 
                        for (int j =0 ; j<LPoids.length;j++)
                        {
                            CRS.getRConnexion().voidEval("poids <- c(poids,"+LPoids[i]+")");
                            CRS.getRConnexion().voidEval("distance <- c(distance,"+LDistance[i]+")");
                            CRS.getRConnexion().voidEval("accompagant <- c(accompagant,"+LAccompagant[i]+")");
                        }
                        CRS.getRConnexion().voidEval("df = data.frame(poids,distance,accompagant)");
                        CRS.getRConnexion().voidEval("test<-lm(data$poids~data$distance+data$accompagant)");
                        H0=CRS.getRConnexion().eval("summary(test)$coefficients[,\"Pr(>|t|)\"]").asDoubles();
                        Coef=CRS.getRConnexion().eval("summary(test)$r.squared").asDouble();
                        CRS.getRConnexion().voidEval("fstat<-summary(test)$fstatistic");
                        p_value1=CRS.getRConnexion().eval("pf(fstat[1],fstat[2],fstat[3],lower.tail=FALSE)").asDouble();
                        CRS.getRConnexion().voidEval("test<-lm(data$temps~data$volume+data$nombre.de.grandes.pieces-1)");
                        t=CRS.getRConnexion().eval("summary(test)$coefficients[,\"Pr(>|t|)\"]").asDoubles();
                        Coef=CRS.getRConnexion().eval("summary(test)$r.squared").asDouble();
                        CRS.getRConnexion().voidEval("fstat<-summary(test)$fstatistic");
                        p_value2=CRS.getRConnexion().eval("pf(fstat[1],fstat[2],fstat[3],lower.tail=FALSE)").asDouble();
                    } 
                    catch (RserveException | REXPMismatchException ex) 
                    {
                        Logger.getLogger(RequeteLUGANAP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    Reponse.getChargeUtile().put("Coef", Coef);
                    /*Reponse.getChargeUtile().put("p_value1", p_value1);
                    Reponse.getChargeUtile().put("p_value2", p_value2);
                    Reponse.getChargeUtile().put("H0", H0);
                    Reponse.getChargeUtile().put("t", t);*/
                    
                }
                else
                {
                   System.out.println("cassé"); 
                }
                        
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(RequeteLUGANAP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    
    public void TraiterAnovaLug()
    {
        Bean_DB_Access BD_airport;
        ResultSet RS;
        
        int i = 1;
        
        BD_airport = Connexion_DB();
        String Annee , Compagnie;
        int Mois;
        
        //Annee= getChargeUtile().get("Annee").toString();
       // Mois= getChargeUtile().get("Mois").toString();
        //Compagnie= getChargeUtile().get("Compagnie").toString();
        //if(Mois.equals("Toute l'année"))  
        //if(Compagnie.equals("Toutes les compagnies"))    
        
        Annee="2017";
        Mois=11;
        Compagnie="AIR FRANCE CANAILLE";

        if(BD_airport !=null)
        {
            if(/*!Mois.equals("Toute l'année") && */(!Compagnie.equals("Toutes les compagnies")))
            {
                try 
                {  
                    RS = BD_airport.Select("SELECT AVG(poids) as Moy, destination as Dest\n" +
                            "FROM Bagages NATURAL JOIN Billets NATURAL JOIN vols NATURAL JOIN avions NATURAL JOIN compagnies\n" +
                            "WHERE extract(YEAR FROM HeureDepart)= "+Annee+"\n" +
                            "AND extract(MONTH FROM HeureDepart)="+Mois+"\n" +
                            "GROUP BY Destination");

                    //ajouter la compagnie
                        
                if (RS != null) 
                {
                    Reponse = new ReponseLUGANAP(ReponseLUGANAP.ANOVA_L_LUG_OK);
                    //ArrayList<Integer> DataCorr = new ArrayList<>();
                    //int[] DataCorr =null ;
                    List DataCorr = new ArrayList(); 
                    while(RS.next())
                    {
                        int Moyenne = RS.getInt("Moy");
                        String Destination = RS.getString("Dest");
                        DataCorr.add(Moyenne);
                        DataCorr.add(Destination);         
                    }
                    
                    Reponse.getChargeUtile().put("Data", DataCorr);
                }
                else
                {
                   System.out.println("cassé"); 
                }
                        
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(RequeteLUGANAP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void TraiterAnovaLug2()
    {
        Bean_DB_Access BD_airport;
        ResultSet RS;
        
        int i = 1;
        
        BD_airport = Connexion_DB();
        String Annee , Compagnie;
        int Mois;
        
        //Annee= getChargeUtile().get("Annee").toString();
       // Mois= getChargeUtile().get("Mois").toString();
        //Compagnie= getChargeUtile().get("Compagnie").toString();
        //if(Mois.equals("Toute l'année"))  
        //if(Compagnie.equals("Toutes les compagnies"))    
        
        Annee="2017";
        Mois=11;
        Compagnie="AIR FRANCE CANAILLE";

        if(BD_airport !=null)
        {
            if(/*!Mois.equals("Toute l'année") && */(!Compagnie.equals("Toutes les compagnies")))
            {
                try 
                {  
                    RS = BD_airport.Select("SELECT poids, destination\n" +
                            "FROM Bagages NATURAL JOIN Billets NATURAL JOIN vols NATURAL JOIN avions NATURAL JOIN compagnies\n" +
                            "WHERE extract(YEAR FROM HeureDepart)= "+Annee+"\n" +
                            "AND extract(MONTH FROM HeureDepart)="+Mois+"\n" +
                            "order by destination");

                    //ajouter la compagnie
                        
                if (RS != null) 
                {
                    Reponse = new ReponseLUGANAP(ReponseLUGANAP.ANOVA_L_LUG_OK);
                    //ArrayList<Integer> DataCorr = new ArrayList<>();
                    //int[] DataCorr =null ;
                    List DataCorr = new ArrayList(); 
                    while(RS.next())
                    {
                        int Moyenne = RS.getInt("poids");
                        String Destination = RS.getString("destination");
                        DataCorr.add(Moyenne);
                        DataCorr.add(Destination);         
                    }
                    Reponse.getChargeUtile().put("Data", DataCorr);
                }
                else
                {
                   System.out.println("cassé"); 
                }
                        
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(RequeteLUGANAP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
