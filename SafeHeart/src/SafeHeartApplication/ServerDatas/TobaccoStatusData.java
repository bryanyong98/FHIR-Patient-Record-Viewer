/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.ServerDatas;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author yogeshganesh
 * TobaccoData
 * 
 * Responsible for querying the server for a particular patient's vitals
 * 
 */

public class TobaccoStatusData extends ServerData {
    private static ArrayList<String[]> returnList = new ArrayList<>();
    
    
    /**
    *
    * @author yogeshganesh 
    * 
    * Responsible for querying the server for a particular patient's vitals
    * @param Id  : the patient id. 
    * @return    : the tobacco data in tree map. 
    * 
    */      
    public static ArrayList<String[]> queryTobaccoStatusData(String Id){
        // curl and get the contents from the http url using CURL
           String command = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3/Observation?code=http://loinc.org%7C72166-2&subject=" + Id ;
           
           JSONObject tobaccoObject  =callServer(command);
           
           int lenTobaccoObject = tobaccoObject.getJSONArray("entry").length();
           
           // This is to store all the encounters relevant. 
           ArrayList<String> encountersList = new ArrayList() ;            
           
            
           // loop and get each of the blood pressure data of patients. 
           for (int i=0 ; i < lenTobaccoObject ; i++)
           {
               JSONObject tobaccoData = tobaccoObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource") ; 
                                            
               String dateIssued = "" ;   /* Get the date issued.*/
               String status = "" ;  
               
               String reference = ""  ;   /* Get the ID reference of a patient. */  
               
               /* This part is to get the relevant clinician IDs  */ 
               JSONObject context = tobaccoData.getJSONObject("context") ;
               String encounterRef = "" ;  // initialise 
               
             
               try
               {                
                   dateIssued = tobaccoData.getString("issued") ; 
                   status = tobaccoData.getJSONObject("valueCodeableConcept").getString("text");
                   reference = tobaccoData.getJSONObject("subject").getString("reference") ; 
                   encounterRef = context.getString("reference") ;               
               }
               
               catch(org.json.JSONException e)
               {
                   dateIssued = "" ; 
                   status = "" ; 
                   reference = ""  ; 
                   encounterRef = "" ; 
               }
              
               encountersList.add(encounterRef) ;                
               returnList.add(new String[] {reference, dateIssued, status, encounterRef} );
               
           }   
           
           return returnList ; 
    }

    /**
     * @return the returnList for tobacco
     */
    public static ArrayList<String[]> getTobaccoStatusData() {
        return returnList;
    }

    /**
     * @param aReturnList the returnList for tobacco to set
     */
    public static void setTobaccoStatusData(ArrayList<String[]> aReturnList) {
        returnList = aReturnList;
    }
}
