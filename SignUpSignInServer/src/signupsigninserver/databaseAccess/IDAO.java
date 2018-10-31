/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import signupsignin.User;

/**
 *
 * @author Alatz
 */
public interface IDAO {

    public User login(User user);

    public User register(User user);
    
}