/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.ServerDatas;
import java.util.ArrayList;
import SafeHeartApplication.Users.Patient;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.JSONObject;


/**
 *
 * @author yogeshganesh
 * PatientData
 * 
 * Responsible for querying the server for a particular patient's vitals
 * 
 */
public class PatientData extends ServerData{
    
    private static HashMap<String, ArrayList<Patient>> patientsOfClinicians = new HashMap<String ,ArrayList<Patient>>();
       
    /**
    *
    * @author yogeshganesh
    * BloodPressureData
    * 
    * Responsible for querying the server for a particular patient data. 
    * @param practitionerId  : the clinician id. 
    * 
    */      
    public static void queryPatientData(String practitionerId)
{
            String command_2 = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3" + "/Encounter" + "?participant=" + practitionerId ;
                
            JSONObject encountersJsonObject = callServer(command_2);  
            
            try
            {
                for (int i=0 ; i<encountersJsonObject.getJSONArray("entry").length() ;i++)
                {
                
                    String patientURL = encountersJsonObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getJSONObject("subject").getString("reference");
                    String command_3 = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3" + "/" + patientURL ;
            
               
                    JSONObject patientsJsonObject = callServer(command_3);
       
                    String prefix = "" ;
                    String familyName = patientsJsonObject.getJSONArray("name").getJSONObject(0).getString("family");
                    String givenName = patientsJsonObject.getJSONArray("name").getJSONObject(0).getJSONArray("given").get(0).toString();     
                    prefix = patientsJsonObject.getJSONArray("name").getJSONObject(0).getJSONArray("prefix").get(0).toString();
                    String id = patientsJsonObject.getString("id");
                    String gender = patientsJsonObject.getString("gender");
           
                    ArrayList<String[]> emptyArray = new ArrayList<String[]>() ;  
          
                    Patient temp = new Patient (id,givenName,familyName,prefix,gender);
            
            
                    if (patientsOfClinicians.containsKey(practitionerId))
                    {
                        if (patientsOfClinicians.get(practitionerId).size()>0)
                        {
                            boolean x = false;
                            for (int k =0 ; k< patientsOfClinicians.get(practitionerId).size();k++)
                            {
                    
                                if (patientsOfClinicians.get(practitionerId).get(k).getId().equals(temp.getId()))
                                {
                                    x = true;
                                    break;
                                }
                            }
                            if (x == false)
                            {
                                patientsOfClinicians.get(practitionerId).add(temp);
                        
                            }
                    
                        }
                        else
                        {
                            patientsOfClinicians.get(practitionerId).add(temp);
                        }
                
            
                    }
            
                    else
                    {
                        ArrayList tempo = new ArrayList();
                        tempo.add(temp);
                        patientsOfClinicians.put(practitionerId, tempo);
                    }
            
               }
            
            }
            
            catch (org.json.JSONException ex)
            {
              JOptionPane.showMessageDialog(new JFrame(), "No Patients to Monitor");

            }

}

    /**
     * @return the patientsOfClinician
     */
    public static HashMap<String, ArrayList<Patient>> getPatients() {
        return patientsOfClinicians;
    }

    /**
     * @param aPatientsOfClinician the patientsOfClinician to set
     * 
     * setter of the clinician's patients list. 
     */
    public static void setPatientsOfClinician(HashMap<String, ArrayList<Patient>> aPatientsOfClinician) {
        patientsOfClinicians = aPatientsOfClinician;
    }
    }


