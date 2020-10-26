/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.ServerDatas;
import java.util.TreeMap;
import org.json.JSONObject;


/**
 *
 * @author yogeshganesh
 * CholesterolData
 * 
 * Responsible for querying the server for a particular patient's vitals
 * 
 */
public class CholesterolData extends ServerData{
    private static TreeMap< String, String> cholestrolLevels = new TreeMap<>();
    
    
    
/**
 *
 * @author yogeshganesh
 * 
 * getter for cholesterol
 * @return  cholesterol levels of patient in tree map. 
 * 
 */        
    public static TreeMap< String, String> getCholesterolLevels(){
        return cholestrolLevels;
    }
    
    
/**
 *
 * @author yogeshganesh 
 * 
 * Responsible for querying the server for a particular patient's vitals
 * @param Id  : the patient id. 
 * @return    : the cholesterol data in tree map. 
 * 
 */       
    
    public static TreeMap< String, String> queryCholesterolLevels(String Id){
           String command_4 = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3/DiagnosticReport?code=57698-3" + "&subject=" + Id ;
           JSONObject lipidPanel = callServer(command_4);
           String totalCholestrolLevel = "" ; 
              
           try
            {
                for (int j =0 ; j < lipidPanel.getJSONArray("entry").length(); j++)
                {
                String cholLink = lipidPanel.getJSONArray("entry").getJSONObject(j).getJSONObject("resource").getJSONArray("result").getJSONObject(0).getString("reference");
                String type  = lipidPanel.getJSONArray("entry").getJSONObject(j).getJSONObject("resource").getJSONArray("result").getJSONObject(0).getString("display"); 
                String cholURL = "curl -X GET http://hapi-fhir.erc.monash.edu:8080/baseDstu3" + "/" + cholLink;
                
                JSONObject cholestrolDataObject = callServer(cholURL);                
                  
                                
                totalCholestrolLevel = Integer.toString(cholestrolDataObject.getJSONObject("valueQuantity").getInt("value")) + cholestrolDataObject.getJSONObject("valueQuantity").getString("unit");

                
                String CholDate = cholestrolDataObject.getString("issued");
                cholestrolLevels.put(CholDate, totalCholestrolLevel);
                
                }
            }
           
           catch( org.json.JSONException ex)
           {
               cholestrolLevels = null;
           } 
           
           return cholestrolLevels ;    // return for that patient's whole record of cholesterol levels. 
       }
        
 }

