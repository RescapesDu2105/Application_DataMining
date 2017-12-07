/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_email;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Philippe
 */
public class Mail
{
    private String Expediteur;
    private String Objet;
    private String Message;
    private ArrayList<File> PiecesJointes;

    public Mail()
    {
        this.PiecesJointes = new ArrayList<>();
    }
    
    public Mail(String Expediteur, String Objet, String Message, ArrayList<File> PiecesJointes)
    {
        this.Expediteur = Expediteur;
        this.Objet = Objet;
        this.Message = Message;
        this.PiecesJointes = PiecesJointes;
    }

    
    public String getExpediteur()
    {
        return Expediteur;
    }

    public void setExpediteur(String Expediteur)
    {
        this.Expediteur = Expediteur;
    }

    public String getObjet()
    {
        return Objet;
    }

    public void setObjet(String Objet)
    {
        this.Objet = Objet;
    }

    public String getMessage()
    {
        return Message;
    }

    public void setMessage(String Message)
    {
        this.Message = Message;
    }

    public ArrayList<File> getPiecesJointes()
    {
        return PiecesJointes;
    }

    public void setPiecesJointes(ArrayList<File> PiecesJointes)
    {
        this.PiecesJointes = PiecesJointes;
    }
    
    
}
