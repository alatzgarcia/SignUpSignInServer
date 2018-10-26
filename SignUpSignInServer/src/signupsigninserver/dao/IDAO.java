/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.dao;

import signupsigninserver.exceptions.EmailExistsException;
import signupsigninserver.exceptions.IncorrectLoginException;
import signupsigninserver.exceptions.IncorrectPasswordException;
import signupsigninserver.exceptions.LoginExistsException;
import signupsigninuidesktop.model.User;

/**
 *
 * @author Iker
 */
public interface IDAO  {
    
    public User login(User user) throws IncorrectLoginException, IncorrectPasswordException;
    
    public User register(User user) throws LoginExistsException, EmailExistsException;
    
    public void close() throws Exception;
}
