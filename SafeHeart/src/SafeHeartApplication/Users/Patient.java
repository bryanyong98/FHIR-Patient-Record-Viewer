/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.Users;
import SafeHeartApplication.Users.*;
import java.util.ArrayList;


/**
 *
 * @author User
 * A patient is to be treated and monitored by the clinicians. 
 * 
 */
public class Patient extends User
{
    private String id;
    private String givenName;
    private String familyName;
    private String prefix;
    private String gender;
      
/**
 *
 * @author Bryan
 * 
 * Patient: Constructor for Patient for object instantiation purposes. 
     * @param id  : the patient id string
     * @param givenName : the given name of patient.
     * @param gender  : the gender of patient. 
     * @param prefix  : patient's addressing. 
     * @param familyName : family name of patient. 
 */
    public Patient(String id, String givenName, String familyName, String prefix, String gender)
    {
        
        super(id, givenName, familyName) ;  
        setPrefix(prefix) ; 
        setGender(gender) ; 
        
    }


    /**
     * 
     * getId
     * @return the id
     */

    public String getId() {
        return id;
    }

    /**
     * @param id the id to set and mutate. 
     */
    
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @param givenName the givenName to set
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }  
}
