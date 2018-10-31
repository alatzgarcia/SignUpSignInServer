/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import signupsignin.User;
import signupsigninserver.exceptions.NotAvailableConnectionException;

/**
 *
 * @author Alatz
 */
public class DAO implements IDAO {
    private static final Logger LOGGER = Logger.getLogger("signupsigninserver.ILogicImplementation");
    private static ConnectionPool connPool;
    @Override
    public synchronized User login(User user) {
                
        try {
            Connection conn;
            conn = connPool.getConnection();
            LOGGER.info("conexión");
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotAvailableConnectionException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        user = new User("loginName1", "email@gmail.com", "Nombre", "password1");
        LOGGER.info(user.getFullName());
        return user;
    }

    @Override
    public User register(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

   

    
    
    
}