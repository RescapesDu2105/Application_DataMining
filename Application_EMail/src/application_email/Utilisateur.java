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
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author Philippe
 */
public class Utilisateur
{
    private Session mailSession;
    private Store mailStore;
    private Properties mailProperties;
    private String adresseMail;
    private Folder folder;
    private List<Message> messages;

    
    public Utilisateur()
    {
        this.mailProperties = new Properties();
        LoadProperties();        
    }
    
    
    public void LoadProperties()
    {
        //String pathProperties = server + "_prop.txt";
        String pathProperties = System.getProperty("user.dir").split("\\\\dist")[0] + System.getProperty("file.separator")+ "src" + System.getProperty("file.separator") + this.getClass().getPackage().getName()+ System.getProperty("file.separator") + "config.properties";

        try
        {
            FileInputStream Oread = new FileInputStream(pathProperties);
            getMailProperties().load(Oread);
        }
        catch(FileNotFoundException ex)
        {
            try 
            {
                FileOutputStream fos = new FileOutputStream(pathProperties);
                
        
                /*switch(server)
                {
                    case "U2" :*/
                        getMailProperties().put("mail.pop3.host", "10.59.26.134");
                        getMailProperties().put("mail.pop3.port", "110");
                        getMailProperties().put("mail.smtp.host", "10.59.26.134");
                        getMailProperties().put("mail.smtp.port", "25");
                        getMailProperties().put("mail.disable.top", "true");
                        getMailProperties().put("store", "pop3");
                        
                    //break;
                    //case "Gmail":
                        /*properties.put("mail.pop3.host", "pop.gmail.com");
                        properties.put("mail.pop3.port", "995");
                        properties.put("mail.pop3.socketFactory.port", "995");
                        properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        properties.put("mail.pop3s.ssl.trust", "pop.gmail.com");
                        
                        properties.put("mail.smtp.host", "smtp.gmail.com");
                        properties.put("mail.smtp.auth", "true");
                        properties.put("mail.smtp.port", "587");
                        properties.put("mail.smtp.socketFactory.port", "465");
                        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        
                        properties.put("store", "pop3s");*/
                        /*break;
                }*/
                //paramCo.setProperty("MAIL_HOST", "10.59.26.134");
                try 
                {
                    getMailProperties().store(fos, null);
                }
                catch (IOException ex1) 
                {
                    System.exit(0);
                }
            } 
            catch (FileNotFoundException ex1) 
            {
                System.exit(0);
            }
        }
        catch(IOException ex)
        {
            System.exit(0);
        }
    }
    
    public void ChargerEmails() throws MessagingException, IOException
    {
        folder = mailStore.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);        
        messages = Arrays.asList(folder.getMessages());
        //folder.close(true);
        
        System.out.println("Nombre de messages : " + folder.getMessageCount());
        System.out.println("Nombre de nouveaux messages : " + folder.getNewMessageCount());
        
        Enumeration headers = messages.get(1).getAllHeaders();
        while (headers.hasMoreElements()) 
        {
            Header h = (Header) headers.nextElement();
            if(h.getName().equals("Return-Path"))
            {
                //System.out.println("Expéditeur = " + h.getValue());   
            }
        }
        System.out.println("Sujet = " + messages.get(0).getSubject());
        System.out.println("Texte : " + (String)messages.get(0).getContent());
    }
    

    public Folder getFolder()
    {
        return folder;
    }

    public void setFolder(Folder folder)
    {
        this.folder = folder;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
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

    public String getAdresseMail()
    {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail)
    {
        this.adresseMail = adresseMail;
    }    
}
