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
 * An abstract class of user which enables extension from other concrete 
 * and specific implementation such as patients, doctors. 
 */
public class User {
    
    // Private attributes of the parent class 
    private String id ;
    private String givenName ; 
    private String familyName ; 
    
/**
 *
 * @author Bryan 
 * 
 * A concrete class which has basic information such as id, family name and
 * given name 
     * @param id : the id of the user 
     * @param givenName  : given name of the user. 
     * @param familyName : the family name of the user. 
 */    
    
    public User(String id, String givenName, String familyName)
    {
        setId(id) ; 
        setGivenName(givenName) ; 
        setFamilyName(familyName) ;  
    }    

 /**
 *
 * @author Bryan 
 * 
 * getId : returns the id of user 
 * 
 * @return id
 */
    public String getId(){
        return this.id ; 
    } 
    
/**
 *
 * @author Bryan 
 * 
 * setId : sets the id of user 
 * 
 * @param id: the id to set. 
 */
    public void setId(String id){
        this.id = id ;     
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
}
