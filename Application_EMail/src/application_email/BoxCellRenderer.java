/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_email;

import java.awt.Color;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Philippe
 */
public class BoxCellRenderer extends DefaultListCellRenderer
{

    public BoxCellRenderer()
    {                   
        //setOpaque(true);

        /*Font styleSimple = (new JLabel("font")).getFont();
        Font styleGras = new Font(styleSimple.getName(), Font.BOLD, styleSimple.getSize());*/
    }

    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Message message = (Message) value;
        String labelText;
        
        try
        {
            String Expediteur;
            if(message.getFrom() != null)
                Expediteur = message.getFrom()[0].toString();
            else
                Expediteur = "Inconnu";
            
            labelText = "<html><strong>" + Expediteur + "</strong><br/>" + message.getSubject();
            setText(labelText);
        }
        catch (MessagingException ex)
        {
            Logger.getLogger(BoxCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }

        return this;       
    }
    
}
