/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import signupsigninserver.exceptions.NotAvailableConnectionException;
import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.util.logging.Logger;

/**
 *
 * @author Iker Manzano, Nerea Jimenez
 */
public class ConnectionPool  {
    private static final Logger LOGGER=Logger.getLogger("signupsigninserver.databaseAccess.ConnectionPool");
    private static MysqlConnectionPoolDataSource dataSource = null;
    public final static String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    public final static String DB_NAME = "Equipo2";
    public final static String USERNAME = "root";
    public final static String PASSWORD = "contraseña";
    public final static int MAX_CONNECTIONS=10;
    public static int connections=0;
    
    public static Connection getConnection() throws SQLException, NotAvailableConnectionException{
        LOGGER.info("Connection Pool");
        Connection conn = null;
        if(connections<MAX_CONNECTIONS){
             if (dataSource == null) {
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setUser(USERNAME);
            dataSource.setPassword(PASSWORD);
            dataSource.setURL(DATABASE_URL + DB_NAME);
             }
            conn = dataSource.getConnection();
            connections++;
            
        }else{
            throw new NotAvailableConnectionException();
        }
        return conn;
    }
    
    public void releaseConnection(Connection conn) throws SQLException{
        conn.close();
        connections--;
    }
    
    
}
