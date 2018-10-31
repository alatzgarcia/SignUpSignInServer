/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import signupsigninserver.exceptions.NotAvailableConnectionsException;

/**
 *
 * @author Iker Manzano, Nerea Jimenez
 */
public class ConnectionPool  {
    
    private final static MysqlConnectionPoolDataSource DATA_SOURCE = 
            new MysqlConnectionPoolDataSource();
    public final static String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    public final static String DB_NAME = "";
    public final static String USERNAME = "";
    public final static String PASSWORD = "";
    public final static int MAX_CONNECTIONS=10;
    public static int currentConnections=0;
    
    public Connection getConnection() throws Exception{
        Connection conn;
        if(currentConnections<MAX_CONNECTIONS){
            DATA_SOURCE.setUser(USERNAME);
            DATA_SOURCE.setPassword(PASSWORD);
            DATA_SOURCE.setURL(DATABASE_URL + DB_NAME);
            conn = DATA_SOURCE.getConnection();
            currentConnections++;
            return conn;
        }else{
            throw new NotAvailableConnectionsException();
        }
    }
    
    public void releaseConnection(Connection conn) throws Exception{
        conn.close();
        currentConnections--;
    }
    
    
}

