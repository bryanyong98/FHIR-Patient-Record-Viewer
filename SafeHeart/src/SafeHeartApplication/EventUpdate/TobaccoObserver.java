/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.EventUpdate;

import SafeHeartApplication.UserInterfaces.Monitor;
import java.util.ArrayList;
import java.util.TreeMap;
import SafeHeartApplication.ServerDatas.TobaccoStatusData;


/**
 *
 * @author Bryan
 * 
 * TobaccoObserver is responsible to detect any changes in the subject
 * then update the data it holds accordingly and relay the message to 
 * the monitor. 
 */
public class TobaccoObserver implements Observer {

    ArrayList<String[]> tobaccoList ; 
    Monitor monitor ;    // this is for invoking purposes. 
    String patientid ;   // this is to call the fhir purposes. 
   
   
 /**
 *
 * @author Bryan
 * 
 * Constructor of the tobacco observer, which takes in a subject object as a parameter. 
 * @param monitor : The actual Monitor code that has been passed to the observer.
 * @param patientid : Patient id string. 
 * 
 * 
 */
   public TobaccoObserver(Monitor monitor, String patientid){
       this.monitor = monitor ; 
       this.patientid = patientid ; 
   }
     
     /**
 *
 * @author Bryan
 * 
 * update: update any data regarding to the change in fhir server.
 *         get the data from the FHIR regarding tobacco data again.  
 * 
 */
   @Override
   public void update() {
       
       // after getting the data from the fhir server, proceed to invoke the monitor to perform update. 
       tobaccoList = TobaccoStatusData.queryTobaccoStatusData(patientid); 
       monitor.updateTobaccoMonitor(tobaccoList);
           
   }    
    
}
