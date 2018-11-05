/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import signupsigninserver.exceptions.NotAvailableConnectionException;
import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.util.logging.Logger;

/**
 * Class that manages connections with the database
 * @author Iker Manzano, Nerea Jimenez
 */
public class ConnectionPool  {
    private static final Logger LOGGER=Logger.getLogger("signupsigninserver.databaseAccess.ConnectionPool");
    private static MysqlConnectionPoolDataSource dataSource = null;
    public final static String DATABASE_URL = "jdbc:mysql://LAPINF09.tartangaLH.eus:3306/";
    public final static String DB_NAME = "retodatabase";
    public final static String USERNAME = "user";
    public final static String PASSWORD = "";
    public final static int MAX_CONNECTIONS=10;
    public static int connections=0;
    
    /**
     * Method that gets connections up to a maximum of n
     * @return the connection
     * @throws SQLException
     * @throws NotAvailableConnectionException 
     */
    public static Connection getConnection() throws SQLException, NotAvailableConnectionException{
        LOGGER.info("Connection Pool");
        Connection conn = null;
        if(connections<MAX_CONNECTIONS){
             if (dataSource == null) {
                dataSource = new MysqlConnectionPoolDataSource();
                dataSource.setUser(USERNAME);
                dataSource.setPassword(PASSWORD);
                dataSource.setURL(DATABASE_URL + DB_NAME);
                dataSource.setServerTimezone("UTC");
             }
            conn = dataSource.getConnection();
            connections++;
            return conn;
            
        }else{
            throw new NotAvailableConnectionException();
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
