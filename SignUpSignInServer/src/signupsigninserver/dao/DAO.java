/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import signupsigninserver.exceptions.EmailExistsException;
import signupsigninserver.exceptions.LoginExistsException;
import signupsigninuidesktop.model.User;

/**
 *  
 * @author Diego e Iker
 */
public class DAO {
    public User login (User user) throws ClassNotFoundException,SQLException,SQLTimeoutException{
        String query="SELECT * from usuario WHERE FullName=? and Password=?";
        try (Connection con = DBConnection.getConnetion()) {
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            
            ResultSet rs =ps.executeQuery();
            
            ps.close();
        }
        return user;
    }
    public User register (User user) throws ClassNotFoundException,SQLException, LoginExistsException, EmailExistsException,SQLTimeoutException{
        boolean loginFound=false;
        boolean emailFound=false;
        
        String sqlStatement="SELECT * from usuario WHERE Login=?";
        String sqlStatement2="SELECT * from usuario WHERE Email=?";
        try(Connection con = DBConnection.getConnetion()){
            PreparedStatement ps = con.prepareStatement(sqlStatement);
            
            ps.setString(1, user.getLogin());
            
            loginFound=ps.execute();
            
            ps=con.prepareStatement(sqlStatement2);
            
            ps.setString(1, user.getEmail());
            
            emailFound=ps.execute();
            
            if(emailFound){
                throw new EmailExistsException();
            }
            
            if(loginFound){
                throw new LoginExistsException();
            }
            
            /* 
            *  This method inserts the values to the database if the email or the 
            *  login arent repeated
            */
            sqlStatement="insert into usuario values (?,?,?,?) ";
            ps = con.prepareStatement(sqlStatement);
                
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
                
            ps.execute();
                
            ps.close();
        }catch(EmailExistsException eee){
            
        }catch(LoginExistsException lee){
            
        }catch(Exception e){
            
        }
        return user;
    }
}
