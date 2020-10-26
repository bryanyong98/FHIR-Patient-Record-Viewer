/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.EventUpdate;

import SafeHeartApplication.EventUpdate.Subject;

/**
 *
 * @author User Bryan
 * 
 * Observer: An interface implementation of concrete observers. 
 */
public interface Observer {
    
    
 /**
 * @author Bryan
 * 
 * update: for updating existing data purposes. To be override. 
 * 
 */        
    public void update() ;    // update the data it holds. 
}