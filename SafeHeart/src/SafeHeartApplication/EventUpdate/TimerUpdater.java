/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.EventUpdate;
import java.util.TimerTask ; 
import java.util.Date;



/**
 * @author bryan
 * TimerUpdate is used for timing purposes so that it is able to 
 * send the subject signals to start updating datas from server. 
 * 
 */
public class TimerUpdater extends TimerTask {
    
    PatientVitalsData subject ; 
    
    
/**
 * @author bryan
 * TimerUpdater constructor which accepts a subject as a parameter 
 * for notifying purposes to fetch new data etc. 
 * @param subject: subject of interest to the observers. 
 * 
 * 
 */    
    public TimerUpdater(PatientVitalsData subject)
    {
        this.subject = subject ; 
    }
    

/**
 * @author bryan
 * 
 * run() schedules for the timer updater to run with the help of 
 * the timer library, which then triggers the observers to change
 * state later on. 
 *  
 */ 
    public void run()
    { 
        System.out.println("Change state... Updating data..");
        this.subject.setState(1);
    }  
}
