/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.utilities;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Philippe
 */
public class Bean_DB_Access implements Serializable, Drivers, URLs_Database {
    private String Driver;
    private String Host;
    private String Port;
    private String Login;
    private String Password;
    private String Schema;
    private Connection Connection;
    private Statement Statement;
    private boolean Connected;
    
    
    public Bean_DB_Access(String d, String h, String p, String l, String pwd, String s)
    {
        setDriver(d);
        setHost(h);
        setPort(p);
        setLogin(l);
        setPassword(pwd);
        setSchema(s);
        setConnected(false);
    }
    
    /* Connexion - Deconnexion */
    public String Connexion()
    {
        try 
        {
            Class.forName(getDriver());
            
            try 
            {
                setConnection(DriverManager.getConnection(getURL(), getLogin(), getPassword()));
                setStatement(getConnection().createStatement());
            } 
            catch (SQLException ex) 
            {
                return "Code SQL : " + ex.getErrorCode() + "\nMessage : " + ex.getMessage();
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            return "Erreur de définition de classe [" + ex.getMessage() + "]";
        }
        
        setConnected(true);
        
        return null;
    }
    
    public String Deconnexion()
    {
        if (Statement != null) 
        {
            try 
            {
                Statement.close();
            } 
            catch (SQLException ex) 
            {
                return "Code SQL : " + ex.getErrorCode() + "\nMessage : " + ex.getMessage();
            }
        }
        
        setConnected(false);
        
        return null;
    }
        
    /* Requetes */
    public synchronized ResultSet Select(String Requete) throws SQLException
    {
        try
        {
            Statement stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return stmt.executeQuery(Requete);
        }
        catch (SQLException Ex)
        {
            if (!Connection.getAutoCommit())
                Rollback();
        }
        
        return null;
    }    
    public synchronized int Update(String Requete) throws SQLException
    {
        try
        {
            int ret = Statement.executeUpdate(Requete);
            if(!getAutoCommit())
                Commit();
            
            return ret;
        }
        catch (SQLException Ex)
        {
            if (!Connection.getAutoCommit())
                Rollback();
        }
        
        return 0;
    }    
    
    /* Commit - Rollback */
    public void setAutoCommit(boolean AC) throws SQLException 
    {
        try
        {
            Connection.setAutoCommit(AC);
        }
        catch (SQLException Ex)
        {
            if (!Connection.getAutoCommit())
                Rollback();
            System.out.println("Code SQL : " + Ex.getErrorCode() + "Erreur SQL : " + Ex.getMessage());
        }
    }
    public boolean getAutoCommit() throws SQLException 
    {
        boolean bool = false;
        
        try
        {
            bool = Connection.getAutoCommit();
        }
        catch (SQLException Ex)
        {
            if (!Connection.getAutoCommit())
                Rollback();
            System.out.println("Code SQL : " + Ex.getErrorCode() + "Erreur SQL : " + Ex.getMessage());
        }
        
        return bool;
    }
    public void Commit() throws SQLException 
    {
        try{
            Connection.commit();
        }
        catch (SQLException Ex) {
            if (!Connection.getAutoCommit())
                Rollback();
            System.out.println("Code SQL : " + Ex.getErrorCode() + "Erreur SQL : " + Ex.getMessage());
        }
    }    
    public void Rollback() 
    {
        try{
            Connection.rollback();
        }
        catch (SQLException Ex) {
            System.out.println("Code SQL : " + Ex.getErrorCode() + " - Erreur SQL : " + Ex.getMessage());
        }
    }
    
    
    /* Setters - Getters*/

    @Override
    public String getURL() 
    {
        switch (getDriver())
        {
            case DRIVER_MYSQL: return URL_MYSQL + getHost() + ":" + getPort() + "/" + getSchema();
            case DRIVER_ORACLE: return URL_ORACLE + getHost() + ":" + getPort() + "/" + getSchema();
            default : return null;
        }
    }
    
    public String getDriver() {
        return Driver;
    }

    public void setDriver(String Driver) {
        this.Driver = Driver;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String Host) {
        this.Host = Host;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String Port) {
        this.Port = Port;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getSchema() {
        return Schema;
    }

    public void setSchema(String Schema) {
        this.Schema = Schema;
    }

    public Connection getConnection() {
        return Connection;
    }

    public void setConnection(Connection Connection) {
        this.Connection = Connection;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement = Statement;
    }

    public boolean isConnected() {
        return Connected;
    }

    public void setConnected(boolean Connected) {
        this.Connected = Connected;
    }
}
