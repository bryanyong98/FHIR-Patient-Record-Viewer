/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.Users;

/**
 *
 * @author Bryan
 * 
 * Clinician is responsible for monitoring any of the patients assigned to them.
 * A clinician may have no patients. 
 * 
 */
public class Clinician extends User {
    private String id;
    private String givenName;
    private String familyName;
    private String prefix;
    private String gender;
    
/**
 *
 * @author Bryan
 * 
 * Clinician: Constructor for Clinician for object instantiation purposes. 
     * @param id : id of clinician
     * @param givenName  : given name of clinician 
     * @param familyName : family name of clinician 
     * @param prefix     : prefix of clinician 
     * @param gender     : gender of clinician. 
 */
    public Clinician(String id , String givenName, String familyName, String prefix, String gender){
        
        super(id,givenName ,familyName) ;         
        setPrefix(prefix) ; 
        setGender(gender) ; 
    }


    /**
     * 
     * getId: getter for the id attribute, which returns a string. 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
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
