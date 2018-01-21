
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Philippe
 */
public class Mail_Utilities
{       
    
    public static void Envoyer_Mail(Session mailSession, String From, String RecipientTo, String Subject, String TextMessage, ArrayList<File> Files)
    {
        try
        {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(From));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(RecipientTo));
            message.setSubject(Subject);
            Multipart MP = new MimeMultipart();
            MimeBodyPart BP = new MimeBodyPart();
            BP.setText(TextMessage);
            MP.addBodyPart(BP);
            
            for(File file : Files)
            {
                BP = new MimeBodyPart(); 
                DataSource so = new FileDataSource (file);
                BP.setDataHandler(new DataHandler(so));
                BP.setFileName(file.getName());
                MP.addBodyPart(BP);
            }
            message.setContent(MP);
            message.saveChanges();
            
            Transport.send(message);
        }
        catch (AddressException ex)
        {
            Logger.getLogger(Mail_Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (MessagingException ex)
        {
            Logger.getLogger(Mail_Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void Envoyer_Mail(Session mailSession, String From, String RecipientTo, String Subject, String TextMessage)
    {
        Envoyer_Mail(mailSession, From, RecipientTo, Subject, TextMessage, null);
    }
}
