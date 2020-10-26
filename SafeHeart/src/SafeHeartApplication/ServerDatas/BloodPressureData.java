/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.ServerDatas;
import java.util.ArrayList;
import java.util.TreeMap;
import org.json.JSONObject;


/**
 *
 * @author yogeshganesh
 * BloodPressureData
 * 
 * Responsible for querying the server for a particular patient's vitals
 * 
 */

public class BloodPressureData extends ServerData{
    private static TreeMap<String, Double[]> bloodPressureData = new TreeMap<>();
    
/**
 *
 * @author yogeshganesh
 * 
 * getter for blood pressure. 
 * @return  blood pressure of patient in tree map. 
 * 
 */    
    public static TreeMap<String, Double[]> getBloodPressureData(){
     return bloodPressureData;
}
    
/**
 *
 * @author yogeshganesh
 * BloodPressureData
 * 
 * Responsible for querying the server for a particular patient's vitals
 * @param Id  : the patient id. 
 * @return    : the blood pressure data in tree map. 
 * 
 */    
    public static TreeMap<String, Double[]> queryBloodPressureData(String Id)
    {
           String command = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3/Observation?code=http://loinc.org%7C55284-4&subject=" + Id ;
           
           JSONObject bloodPressureObject = callServer(command);           
           
           if (bloodPressureObject.has("entry"))    // if and only if a valid patient ID has been typed in. 
           {
               
                int lenBPObject = bloodPressureObject.getJSONArray("entry").length();
                TreeMap< String, Double[] > map = new TreeMap(); 
           
                // This is to store all the encounters relevant. 
                ArrayList<String> encountersList = new ArrayList() ;
                    
                // loop and get each of the blood pressure data of patients. 
                for (int i=0 ; i < lenBPObject ; i++)
                {
                    JSONObject bpData = bloodPressureObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource") ; 
                                            
                    String dateIssued = "" ;   /* Get the date issued.*/
                    Double diastolicP = 0.00 ;   /* Get the diastolic pressure.*/
                    Double systolicP  = 0.00 ;   /* Get the systolic pressure.*/
                    String diastoleStr = "" ; 
                    String systoleStr = "" ; 
                              
                    /* This part is to get the relevant clinician IDs  */ 
                    String encounterRef = "" ;
                    String reference = ""  ;   /* Get the ID reference of a patient. */               
             
                    try
                    {                    
                        dateIssued = bpData.getString("issued") ; 
                        diastolicP = bpData.getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getDouble("value") ; 
                        systolicP = bpData.getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getDouble("value") ;
                        JSONObject context = bpData.getJSONObject("context") ;               
                        reference = bpData.getJSONObject("subject").getString("reference") ;
                        encounterRef = context.getString("reference") ;
                    }
               
                    catch(org.json.JSONException e)
                    {
                        dateIssued = "" ; 
                        diastolicP = 0.00 ; 
                        systolicP = 0.00  ; 
                        reference = ""  ; 
                        encounterRef = "" ; 
                    }    
               
                    diastoleStr = Double.toString(diastolicP) ; 
                    systoleStr = Double.toString(systolicP) ;
               
                    // Storing the encounters for the blood pressure. 
                    encountersList.add(encounterRef) ; 
               
                    Double[] bp = new Double[3] ; 
                    bp[0] = systolicP ; 
                    bp[1] = diastolicP ; 
                             
                    // Storing the date issued as a key in the treemap. 
                    map.put(dateIssued, bp); 
                }        
           
                 // This will be returned to getPractitionerData()
                bloodPressureData = map ; 
           }
         
           else {
                bloodPressureData = null ; 
           }
           
           return bloodPressureData ; 
    }
    
    
}
