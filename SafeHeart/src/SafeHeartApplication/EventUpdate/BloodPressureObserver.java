/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.EventUpdate;

import SafeHeartApplication.UserInterfaces.Monitor;
import SafeHeartApplication.ServerDatas.BloodPressureData;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Bryan 
 * 
 * BloodPressureObserver is responsible to detect any changes in the subject
 * then update the data it holds accordingly and relay the message to 
 * the monitor.  
 * 
 */
public class BloodPressureObserver implements Observer {
    
  TreeMap<String, Double[]>  bpMap ; 
  Monitor monitor ;    // this is for invoking purposes. 
  String patientid ;   // this is to call the fhir purposes.   
      
 /**
 *
 * @author Bryan
 * 
 * Constructor of the blood pressure observer, 
 * which takes in a monitor and patient id string as a parameter. 
 * @param monitor: The actual Monitor code that has been passed to the observer.
 * @param patientid: Patient id string. 
 * 
 * 
 */
   public BloodPressureObserver(Monitor monitor, String patientid){
       
       this.monitor = monitor ; 
       this.patientid = patientid ;        
   }
   
  
 /**
 *
 * @author Bryan
 * 
 * update: update any data regarding to the change in fhir server.
 *         get the data from the FHIR regarding blood pressure data again, 
 *         and display to the monitor. 
 * 
 */
   @Override
   public void update() {
       
       // make a call to the fhir server after 1 hour (sign to update!)
       bpMap = BloodPressureData.queryBloodPressureData(patientid); 
       monitor.updateBloodPressureMonitor(bpMap);
        
   }    
    
}
