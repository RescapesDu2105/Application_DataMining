/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_email;

import java.awt.Component;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Philippe
 */
public class BoxAttachmentsCellRenderer extends DefaultListCellRenderer
{

    public BoxAttachmentsCellRenderer()
    {                   
        
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Part part = (Part) value;
        
        try
        {
            String labelText;
            labelText = part.getFileName();
            setText(labelText);
        }
        catch (MessagingException ex)
        {
            Logger.getLogger(BoxAttachmentsCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }

        return this;       
    }
    
}
