/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.ServerDatas;
import SafeHeartApplication.DriverClass.SafeHeartApplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
/**
 *
 * @author yogeshganesh
 * 
 * The server data serves as an abstract class for other concrete
 * implementations. 
 */
public abstract class ServerData {
    
    
    /**
     * Constructor for the PractitionerLogin, initialize the components. 
     * @param command  The curl command to retrieve URL info 
     * @return  JSONObject : An object which contains the relevant info needed. 
     */
    
    public static JSONObject callServer(String command)
    {
           JSONObject JsonObject = null;
          
           try
           {
               
            Process process= Runtime.getRuntime().exec(command) ;        
            process.getInputStream();
            BufferedReader reader = new BufferedReader (new InputStreamReader (process.getInputStream() )) ; 
            StringBuilder builder = new StringBuilder() ;          
            String line = reader.readLine() ; 
            builder.append("{");
            
            while ( (line = reader.readLine()) != null) 
            {
                builder.append(line) ;
            }
          
            String result = builder.toString() ; 
            JsonObject = new JSONObject(result); 
           }
           
           catch (IOException ex) 
           {
            Logger.getLogger(SafeHeartApplication.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           catch(org.json.JSONException ex)
           {
               JsonObject = null;
           }
           
           return JsonObject;
    }
    
}
