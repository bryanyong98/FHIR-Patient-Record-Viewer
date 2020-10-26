/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.ServerDatas;
import java.util.ArrayList;
import SafeHeartApplication.Users.Clinician;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author yogeshganesh
 * ClinicianData
 * 
 * Responsible for querying the server for clinician data. 
 * 
 */
public class ClinicianData extends ServerData{
    private static ArrayList<Clinician> practitioners = new ArrayList<>();
    
    public static ArrayList<Clinician> getClinicianData(){
            return practitioners;
}
    
    
/**
 *
 * @author yogeshganesh
 * 
 * queries the server for clinician data. 
 * @return    : the array lists of clinicians. 
 * 
 */     
    public static ArrayList<Clinician> queryPractitioners(){
        /* Getting the data from the diagnostic report --> get the practitioner ID, that's all you need. */ 
            String command = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3/DiagnosticReport" ;           

            JSONObject reportJsonObject = callServer(command);
            System.out.println(reportJsonObject) ; 
            
            // to store the encounter URL for the ones obtained from diagnostic report. 
            ArrayList<String> temp = new ArrayList<>() ;
            ArrayList<String> listOfRef = new ArrayList<>() ;
                       
            System.out.println("ONE") ; 
            
            for(int i=0;i<reportJsonObject.getJSONArray("entry").length();i++)
            {
               JSONObject practitioner = reportJsonObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource");
               JSONObject context = practitioner.getJSONObject("context") ;
               String encounterRef = context.getString("reference") ;
               temp.add(encounterRef) ; 
            }
            
            // to get encounter URLS of the clinicians responsible for blood pressure monitoring. 
            String command_getBP = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3/Observation?code=http://loinc.org%7C55284-4" ;  
            JSONObject bpJsonObject = callServer(command_getBP);
            
            for(int i=0 ; i<bpJsonObject.getJSONArray("entry").length(); i++)
            {
               JSONObject practitionerBP = bpJsonObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource");
               JSONObject context = practitionerBP.getJSONObject("context") ;
               String encounterRef = context.getString("reference") ;
               temp.add(encounterRef) ; 
            } 
            
            // to get encounter URLS of the clinicians responsible for blood pressure monitoring. 
            String command_getTobacco = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3/Observation?code=http://loinc.org%7C72166-2" ;  
            JSONObject tobaccoJsonObject = callServer(command_getTobacco);
                        
            for(int i=0 ; i<tobaccoJsonObject.getJSONArray("entry").length(); i++)
            {
               JSONObject practitionerBP = tobaccoJsonObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource");
               JSONObject context = practitionerBP.getJSONObject("context") ;
               String encounterRef = context.getString("reference") ;
               temp.add(encounterRef) ; 
            }
            
            
            // Perform filtering.   
            for (int j=0 ; j < temp.size() ; j++ )
            {
                if (!(listOfRef.contains(temp.get(j))))
                {
                    listOfRef.add(temp.get(j)) ; 
                }          
            }
                           
                           
            // to store the encounter URLs. 
            ArrayList<String> temp2 = new ArrayList<String>() ;
            ArrayList<String> clinicianRef = new ArrayList<String>() ;                
                               
            
            // Attempt to curl and get the practitioner's ID reference. 
            for (int k=0 ; k < listOfRef.size() ; k++){
                String command_2 = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3/" + listOfRef.get(k) ; 
                
                JSONObject encounterJsonObject = callServer(command_2);
                JSONArray participantArray = reportJsonObject.getJSONArray("entry") ; 
                
                
              try
               {     
                participantArray = encounterJsonObject.getJSONArray("participant") ;                 
                
                String urlRef = participantArray.getJSONObject(0).getJSONObject("individual").getString("reference") ;                 
                temp2.add(urlRef) ; // put the clinician URL                   
               }
               
               catch(org.json.JSONException e)
               {
                participantArray = reportJsonObject.getJSONArray("entry") ; 
               }                
                
            }
            
            
                
            // Perform filtering for the redundant practitioners.   
            for (int m=0 ; m < temp2.size() ; m++ )
            {
                if (!(clinicianRef.contains(temp2.get(m))))
                {
                    clinicianRef.add(temp2.get(m)) ; 
                }          
            }            
                        
            /* Now, attempt to query and retrieve the clinician info*/
            
            for (int i=0; i < clinicianRef.size() ; i++)
            {
                
                // curl and get the clinicians again. 
                String command_getPractitioners = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3/" + clinicianRef.get(i) ;           
                JSONObject clinicianJsonObject = callServer(command_getPractitioners);    
                
               
                String id = clinicianJsonObject.getString("id");
                String givenName = clinicianJsonObject.getJSONArray("name").getJSONObject(0).getJSONArray("given").getString(0);
                String familyName = clinicianJsonObject.getJSONArray("name").getJSONObject(0).getString("family");
                String prefix = "";
                String gender = "";
                 
               try
               {                
                prefix = clinicianJsonObject.getJSONArray("name").getJSONObject(0).getJSONArray("prefix").getString(0);
                gender = clinicianJsonObject.getString("gender");
      
               }
               
               catch(org.json.JSONException e)
               {
                    prefix = "";
                    gender = "";
               }
               
               // Creating new clinicians to be put into the arraylist for 
               // future purposes and usage. 
               Clinician clinician = new Clinician(id,givenName,familyName,prefix,gender);
               
               // Adding the clinician instance into the array lists. 
               practitioners.add(clinician);
               

           }
            return practitioners;
        }

    /**
     * @param aPractitioners the practitioners to set
     */
    public static void setPractitioners(ArrayList<Clinician> aPractitioners) {
        practitioners = aPractitioners;
    }
        
    }

