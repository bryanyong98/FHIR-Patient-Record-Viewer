/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.EventUpdate;

import SafeHeartApplication.EventUpdate.Subject;
import SafeHeartApplication.EventUpdate.PatientVitalsData;
import SafeHeartApplication.EventUpdate.Observer;
import SafeHeartApplication.UserInterfaces.Monitor;
import SafeHeartApplication.ServerDatas.CholesterolData;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Bryan
 * 
 * CholesterolObserver is responsible to detect any changes in the subject
 * then update the data it holds accordingly and relay the message to 
 * the monitor. 
 * 
 * attributes include monitor and patient id string.  
 * 
 */
public class CholesterolObserver implements Observer{

    private TreeMap<String, String> cholestrolLevels ; 
    private Monitor monitor ;   // this monitor should refers to the existing monitor.  
    private String patientid ; 

    
 /**
 *
 * @author Bryan
 * 
 * Constructor of the cholesterol observer, which takes in a subject object as a parameter. 
 * @param monitor 
 * @param patientid 
 * 
 * 
 */
   public CholesterolObserver(Monitor monitor, String patientid){
       this.monitor = monitor; 
       this.patientid = patientid ; 
   }
   
   
 /**
 *
 * @author Bryan
 * 
 * update: update any data regarding to the change in fhir server.
 *         get the data from the FHIR regarding cholesterol data again, 
 *         then display to the monitor. 
 * 
 */
   @Override
   public void update() {
            
       cholestrolLevels = CholesterolData.queryCholesterolLevels(patientid) ; 
       monitor.updateCholesterolMonitor(cholestrolLevels);
   }
}    