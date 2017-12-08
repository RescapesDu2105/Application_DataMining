/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_email;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Philippe
 */
public class ThreadNotification extends Thread
{
    private final JLabel label;
    private final String notificationText;

    public ThreadNotification(JLabel label, String notificationText)
    {
        this.label = label;
        this.notificationText = notificationText;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("notificationText = " + notificationText);
            getLabel().setText(getNotificationText());
            //label.repaint();
            sleep(5000);
            getLabel().setText(null);            
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(ThreadNotification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String getNotificationText()
    {
        return notificationText;
    } 
    
    public JLabel getLabel()
    {
        return label;
    } 
}
