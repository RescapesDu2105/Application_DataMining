/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_email;

import GUIs.Application_EMail;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author Philippe
 */
public class ThreadReception extends Thread
{
    private final Application_EMail mainFrame;

    public ThreadReception(Application_EMail mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    @Override
    public void run()
    {
        while(!isInterrupted())
        {
            try
            {
                //System.out.println("Thread -> Avant");
                mainFrame.ChargementEmailsListe();
                int NbNewMessages = mainFrame.getUser().getFolder().getNewMessageCount();
                /*if(NbNewMessages == 0)
                    mainFrame.setTitle("InPReS Mail - Pas de nouveau message");
                else if (NbNewMessages == 1)
                    mainFrame.setTitle("InPReS Mail - 1 nouveau message");
                else if (NbNewMessages > 1)
                    mainFrame.setTitle("InPReS Mail - " + NbNewMessages + " nouveaux messages");*/
                    
                Thread.sleep(30000);    
                //System.out.println("Thread -> Après");
            }
            catch (InterruptedException ex)
            {
                System.err.println("Sleep interrompu");
            }
            catch (MessagingException ex)
            {
                Logger.getLogger(ThreadReception.class.getName()).log(Level.SEVERE, null, ex);
                interrupt();
            }
        }
    }
    
    public Application_EMail getMainFrame()
    {
        return mainFrame;
    }
}
