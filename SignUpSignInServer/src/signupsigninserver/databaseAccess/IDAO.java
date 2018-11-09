/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import signupsigninutilities.model.User;
import signupsigninserver.exceptions.EmailExistsException;
import signupsigninserver.exceptions.LoginEmailExistException;
import signupsigninserver.exceptions.LoginExistsException;
import signupsigninserver.exceptions.RegisterFailedException;

/**
 *
 * @author Iker
 */
public interface IDAO {

    public User login(User user)throws LoginExistsException, 
        EmailExistsException, LoginEmailExistException, RegisterFailedException,
        Exception;

    public User register(User user) throws LoginExistsException, 
        EmailExistsException, LoginEmailExistException, RegisterFailedException,
        Exception;
    
}
