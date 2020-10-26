/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.EventUpdate;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer ; 

/**
 *
 * @author Bryan
 * 
 * Subject is the the class which it receives any timer updates which then 
 * informs the concrete observers. 
 * 
 */
public interface Subject {
    
    public void attach(Observer o); 
    public void detachAll(); 
    public void notifyAllObservers();    
}
