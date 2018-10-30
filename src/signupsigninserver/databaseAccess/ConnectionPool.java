/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import java.sql.Connection;
import java.util.Stack;
import signupsigninserver.exceptions.NotAvailableConnectionsException;

/**
 *
 * @author Alatz
 */
public class ConnectionPool {
    private Stack connectionPool = new Stack();
    private static final int MAX_CONNECTIONS = 20;
    private int createdConnections;
    
    public synchronized Connection getConnection() throws NotAvailableConnectionsException{
        if(connectionPool.isEmpty()){
            if(createdConnections < MAX_CONNECTIONS){
                //Create new connection and give it to the user
                createdConnections++;
                return null;
            } else{
                throw new NotAvailableConnectionsException();
            }
        } else{
            Connection con = (Connection) connectionPool.pop();
            return con;
        }
    }
    
    public synchronized void releaseConnection(Connection con){
        connectionPool.push(con);
    }
}
