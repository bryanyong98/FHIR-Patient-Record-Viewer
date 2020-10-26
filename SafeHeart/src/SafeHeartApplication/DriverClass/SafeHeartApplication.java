/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.DriverClass;

import SafeHeartApplication.UserInterfaces.PractitionerLogin;
import SafeHeartApplication.ServerDatas.ClinicianData;
import SafeHeartApplication.EventUpdate.PatientVitalsData;
import SafeHeartApplication.EventUpdate.Subject;
import SafeHeartApplication.EventUpdate.TimerUpdater;
import SafeHeartApplication.EventUpdate.TobaccoObserver;
import SafeHeartApplication.UserInterfaces.PractitionerForm;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Timer ; 
import java.util.TreeMap;


/**
 *
 * @author Bryan
 * SafeHeartApplication is the main driver class for the application.     
 * 
 */
public class SafeHeartApplication 
{ 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  
    {
        
        // create the subject, and initialise the state. 
        PatientVitalsData sub = new PatientVitalsData() ; 

        // initialise the state to 0. 
        sub.setState(0) ;              
       
       /* getting the practitioner data from the FHIR server to be 
          put into the hashmap purposes. This is for initialisation purposes. */  
                
       ClinicianData.queryPractitioners(); 
           
       // instantiating a Jframe to display all the clinicians IDs in one go. 
       PractitionerLogin loginDisplay = new PractitionerLogin(sub);
       loginDisplay.setVisible(true);
       
       
       // Set a timer to notify the subject after 1 hour has elapsed. 
       Timer time = new Timer() ;
       TimerUpdater updateTask = new TimerUpdater(sub) ; 
       
       /* Perform update every one hour. */
       time.schedule(updateTask, 0, 3600000) ;
       
    }
}
