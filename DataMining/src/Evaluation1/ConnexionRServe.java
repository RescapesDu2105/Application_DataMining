/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation1;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/**
 *
 * @author Philippe
 */
public class ConnexionRServe {
    private RConnection RConnexion = null; // ou new RConnection();
        
    public void setRConnexion(RConnection Rc) {        
           RConnexion = Rc; 
    }
    public RConnection getRConnexion() {
        return RConnexion;
    }    
    public void Connexion() {
        try {
            setRConnexion(new RConnection());
        }
        catch(RserveException Ex){           
            Logger.getLogger(ConnexionRServe.class.getName()).log(Level.SEVERE, null, Ex);
        }
    }
}
