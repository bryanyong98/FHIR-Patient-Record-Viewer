/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.EventUpdate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bryan
 * 
 * The subject of interest is the patient's vital data namely 
 * Cholesterol, Blood Pressure, and Tobacco status. Subject class enables 
 * attachments of observers to it. 
 * 
 */
public class PatientVitalsData implements Subject {
    
    // An arraylist of observers to keep track of. 
    private ArrayList <Observer> observers = new ArrayList<Observer>();
    private int state ;
    
/**
 *
 * @author Bryan
 * 
 * getState returns the state of the subject. 
 * @return state: for identification purposes to determine whether a 
 * a change is performed. 
 * 
 */
    public int getState(){
        return state ; 
    }
    
/**
 *
 * @author Bryan
 * 
 * getObservers returns the list of observers.  
 * @return observers: list of the observers that are attached to the subject.  
 * 
 */    
        
    public ArrayList getObservers()
    {
        return this.observers ; 
    }
       
/**
 *
 * @author Bryan
 * 
 * setState: mutate the state of the subject is in. 
 * This method is mainly used to inform and display that the 
 * data is changing and the information will be propagated to the observers.
 * 
 * @param  state : for identification purposes to determine whether a 
 * a change is performed. 
 * 
 */ 
    public void setState(int state){
        this.state = state ; 
        notifyAllObservers();
    }
    
/**
 *
 * @author Bryan
 * 
 * attach: A method enabling the observers to be attached/ register themselves
 * to the subject
 * @param observer : Observer that is responsible to detect changes from 
 *                   subject. 
 * 
 */
    @Override
    public void attach (Observer observer){
        observers.add(observer);
    }
    
 /**
 *
 * @author Bryan
 * 
 * notifyAllObservers: remove all the observers that are attached to the 
 *                     subject in one go due to the RESET button 
 *                     being pressed in the UI. 
 * 
 */
    @Override
    public void detachAll (){
        
        // replaced with an entire new list. 
        observers = new ArrayList<Observer>();   
    }

    
 /**
 *
 * @author Bryan
 * 
 * notifyAllObservers: notify messages to the rest of the observers that are 
 * subscribed to the subject. 
 * 
 */
    
    @Override
    public void notifyAllObservers(){
        for (Observer observer : observers){
            observer.update() ; 
        }
    }    
    
}
