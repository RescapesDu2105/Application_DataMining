/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_email;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Philippe
 */
public class PieceAttachee
{
    private MimeBodyPart pieceAttachee;
    private File file;
    private byte[] digest;
    
    public PieceAttachee(File file) throws MessagingException
    {
        pieceAttachee = new MimeBodyPart();   
        DataSource so = new FileDataSource (file);
        pieceAttachee.setDataHandler (new DataHandler(so));
        pieceAttachee.setFileName(file.getName());
        this.file = file;
        
        //setDigest(file.getPath());
    }

    public PieceAttachee()
    {
        
    }
    
    public MimeBodyPart getPieceAttachee()
    {
        return pieceAttachee;
    }
    
    public void setPieceAttachee(Part pieceAttachee)
    {
        this.pieceAttachee = (MimeBodyPart) pieceAttachee;
    }

    public byte[] getDigest()
    {
        return digest;
    }

    public void setDigest(String path)
    {        
        try  
        {
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md =  MessageDigest.getInstance("SHA-256", "BC");
            InputStream is = Files.newInputStream(Paths.get(path));            
            DigestInputStream dis = new DigestInputStream(is, md);
        
            this.digest = md.digest();
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException | IOException ex)
        {
            Logger.getLogger(PieceAttachee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getFile()
    {
        return file;
    }
    
    
    @Override
    public String toString()
    {
        try        
        {
            return getPieceAttachee() != null ? getPieceAttachee().getFileName() : null;
        }
        catch (MessagingException ex)
        {
            return null;
        }
    }
}
