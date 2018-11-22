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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import signupsigninserver.exceptions.ConfigurationParameterNotFoundException;
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
     * @throws signupsigninserver.exceptions.ServerNotAvailableException
     * @throws SQLException
     * @throws NotAvailableConnectionsException 
     * @throws java.io.IOException 
     * @throws signupsigninserver.exceptions.GenericException 
     * @throws signupsigninserver.exceptions.ConfigurationParameterNotFoundException 
     */
    public synchronized static Connection getConnection() throws ServerNotAvailableException, 
            SQLException, NotAvailableConnectionsException, IOException, 
            GenericException, ConfigurationParameterNotFoundException {
       
        //gets the parameters from the config file
        FileInputStream input = null;
	try {
            if (dbHost == null) {
                dbHost=ResourceBundle.getBundle("signupsigninserver.config.connection")
                        .getString("ip");
                dbName=ResourceBundle.getBundle("signupsigninserver.config.connection")
                          .getString("dbname");
                dbUser=ResourceBundle.getBundle("signupsigninserver.config.connection")
                          .getString("username");
                dbPassword=ResourceBundle.getBundle("signupsigninserver.config.connection")
                          .getString("password");
                maxConnections=ResourceBundle.getBundle("signupsigninserver.config.connection")
                          .getString("max_connections");
        
                /*Properties config = new Properties();
                
                input = new FileInputStream("src/signupsigninserver/config/connection.properties");
                config.load(input); // carga los datos en la variable config
                dbHost = config.getProperty("ip");
                dbName = config.getProperty("dbname");
                dbUser = config.getProperty("username");
                dbPassword = config.getProperty("password");
                maxConnections= config.getProperty("max_connections");
                
                LOGGER.info("Connection Pool data received");*/
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
        /*} catch (FileNotFoundException ex) {
            LOGGER.severe(ex.getMessage());
            throw new GenericException();
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
            throw new GenericException();*/
        } catch (CommunicationsException ex) {
            LOGGER.severe(ex.getMessage());
            throw new ServerNotAvailableException();
        } catch (SQLException se){
            LOGGER.severe(se.getMessage());
            if(dbHost==null || dbName==null|| dbUser==null){
               throw new ConfigurationParameterNotFoundException(); 
            }else{
               throw new ServerNotAvailableException();
            }           
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
            throw new GenericException();
        } finally {
            if (input != null)
		try {
                    input.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch(Exception e){
            LOGGER.severe("Está dando un errorsito no mas");
        }
    }
    
    
}
