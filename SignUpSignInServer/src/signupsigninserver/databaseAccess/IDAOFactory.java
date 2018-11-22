/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;


/**
 * This class creates creates the IDAO classes.
 * @author Diego
 */
public class IDAOFactory {
    public static IDAO getDAO(){
        return new DAO();
    }
}