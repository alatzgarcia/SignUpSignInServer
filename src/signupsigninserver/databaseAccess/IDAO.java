/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import signupsigninserver.exceptions.EmailExistsException;
import signupsigninserver.exceptions.IncorrectLoginException;
import signupsigninserver.exceptions.IncorrectPasswordException;
import signupsigninserver.exceptions.LoginEmailExistException;
import signupsigninserver.exceptions.LoginExistsException;
import signupsigninserver.exceptions.RegisterFailedException;
import signupsigninutilities.model.User;

/**
 *
 * @author Iker
 */
public interface IDAO {
    /**
     * Calls the login method
     * @param user
     * @return
     * @throws IncorrectLoginException
     * @throws IncorrectPasswordException 
     */
    public User login(User user) throws IncorrectLoginException,
            IncorrectPasswordException, Exception;
    
    /**
     * Calls the register method
     * @param user
     * @return
     * @throws LoginExistsException
     * @throws EmailExistsException 
     */
    public User register(User user) throws LoginExistsException, 
        EmailExistsException, LoginEmailExistException, RegisterFailedException,
        Exception;
}
