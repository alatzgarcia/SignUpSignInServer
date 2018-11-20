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
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import signupsigninserver.exceptions.GenericException;
import signupsigninserver.exceptions.ServerNotAvailableException;

/**
 * Class that manages connections with the database
 * @author Iker Manzano, Nerea Jimenez
 */
public class ConnectionPool  {
    private static final Logger LOGGER=Logger.getLogger("signupsigninserver.databaseAccess.ConnectionPool");
    private static MysqlConnectionPoolDataSource dataSource = null;
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
     * @throws signupsigninserver.exceptions.ServerNotAvailableException 
     * @throws signupsigninserver.exceptions.GenericException 
     */
     public static Connection getConnection() throws SQLException, 
             NotAvailableConnectionsException, IOException, 
             ServerNotAvailableException, GenericException {
	FileInputStream input = null;
	try {
            if (dbHost == null) {
                Properties config = new Properties();
                input = new FileInputStream("src/signupsigninserver/config/connection.properties");
                config.load(input); // carga los datos en la variable config
                dbHost = config.getProperty("ip");
                dbName = config.getProperty("dbname");
                dbUser = config.getProperty("username");
                dbPassword = config.getProperty("password");
                maxConnections= config.getProperty("max_connections");
                
                LOGGER.info("Connection Pool data received");
            }
            
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
            } else {
                throw new NotAvailableConnectionsException();
            }   
        } catch(CommunicationsException ce){
            throw new ServerNotAvailableException();
        } catch(NotAvailableConnectionsException ce){
            throw new ServerNotAvailableException();
        } catch (FileNotFoundException fne) {
            LOGGER.severe(fne.getMessage());
            throw new GenericException();
        } catch (IOException ioex) {
            LOGGER.severe(ioex.getMessage());
            throw new GenericException();
        } catch(Exception ex){
            LOGGER.severe(ex.getMessage());
            throw new GenericException();
        } finally {
            if (input != null){
		try {
                    input.close();
                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                }
            }
	}
    }
    /**
     * Method that releases the connection 
     * @param conn
     * @throws SQLException 
     */
    public void releaseConnection(Connection conn) throws SQLException{
        try{
            conn.close();
            connections--;
        } catch(Exception ex){
            LOGGER.severe("Error on release of connection: " + ex.getMessage());
        }
    }
}