/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import signupsigninserver.exceptions.NotAvailableConnectionsException;
import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that manages connections with the database
 * @author Iker Manzano, Nerea Jimenez
 */
public class ConnectionPool  {
    private static final Logger LOGGER=Logger.getLogger("signupsigninserver.databaseAccess.ConnectionPool");
    private static MysqlConnectionPoolDataSource dataSource = null;
    //public final static int MAX_CONNECTIONS=20;
    public static int connections=0;

    private static String dbHost;
    private static String dbName;
    private static String dbUser;
    private static String dbPassword;
    private static String maxConnections;
    

    /**
     * Method that gets connections up to a maximum of n
     * @return the connection
     * @throws SQLException
     * @throws NotAvailableConnectionsException 
     * @throws java.io.IOException 
     */
    public static Connection getConnection() throws SQLException, NotAvailableConnectionsException, IOException {
       
        //gets the parameters from the config file
        if (dbHost == null) { 
	Properties config = new Properties();
	FileInputStream input = null;
	try {
            input = new FileInputStream("src/signupsigninserver/config/connection.properties");
            config.load(input); // carga los datos en la variable config
            dbHost = config.getProperty("ip");
            dbName = config.getProperty("dbname");
            dbUser = config.getProperty("username");
            dbPassword = config.getProperty("password");
            maxConnections= config.getProperty("max_connections");
           
                
         } catch (FileNotFoundException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null)
		try {
                    input.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
    }
       
        
    
        LOGGER.info("Connection Pool");
        Connection conn = null;
        if(connections<Integer.parseInt(maxConnections)){
             if (dataSource == null) {
                dataSource = new MysqlConnectionPoolDataSource();
                dataSource.setUser(dbUser);
                dataSource.setPassword(dbPassword);
                dataSource.setURL(dbHost + dbName);
                dataSource.setServerTimezone("UTC");
             }
            conn = dataSource.getConnection();
            connections++;
            
            return conn;
            
        }else{
            throw new NotAvailableConnectionsException();
        }
        
    }
    /**
     * Method that releases the connection 
     * @param conn
     * @throws SQLException 
     */
    public void releaseConnection(Connection conn) throws SQLException{
        conn.close();
        connections--;
    }
    
    
}
