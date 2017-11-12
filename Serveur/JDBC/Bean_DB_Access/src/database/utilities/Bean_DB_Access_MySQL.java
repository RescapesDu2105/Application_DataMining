/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.utilities;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Philippe
 */
public class Bean_DB_Access_MySQL extends Bean_DB_Access {
    
   public ResultSet RS;
   
   public Bean_DB_Access_MySQL(String h, String p, String l, String pwd, String s) {
       super(h, p, l, pwd, s);
       setDriver("com.mysql.jdbc.Driver");
   }

    @Override
    public String Connexion() {
        String URL = "jdbc:mysql://" + getHost() + ":" + getPort() + "/" + getSchema();
        
        try
        {
            Class.forName(getDriver());
                    
            setConnection(DriverManager.getConnection(URL, getLogin(), getPassword()));
            setStatement(getConnection().createStatement());
        }
        catch (SQLException Ex)
        {
            System.out.println("Code SQL : " + Ex.getErrorCode() + " - Erreur SQL : " + Ex.getMessage());
            return Ex.getMessage();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Bean_DB_Access_Oracle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}