/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Iker y Diego
 */
public class DBConnection {
    public final static String DB__DRIVER_CLASS =""; 
    public final static String DB_URL="";
    public final static String DB_USERNAME="";
    public final static String DB_PASSWORD="";
    
    public static Connection getConnetion()throws ClassNotFoundException, SQLException{
        
        Connection con = null;
        
        //load the Driver class
        Class.forName(DB__DRIVER_CLASS);
        
        //create the connection now 
        con=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        
        return con;
        
    }
                       
}
