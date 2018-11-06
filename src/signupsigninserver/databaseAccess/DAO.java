/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.databaseAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import signupsigninserver.exceptions.EmailExistsException;
import signupsigninserver.exceptions.IncorrectLoginException;
import signupsigninserver.exceptions.IncorrectPasswordException;
import signupsigninserver.exceptions.LoginEmailExistException;
import signupsigninserver.exceptions.LoginExistsException;
import signupsigninserver.exceptions.RegisterFailedException;
import signupsigninutilities.model.User;

/**
 * This class is the connection between the DB and the program, it has as task
 * to login and register.
 * @author Diego y Alatz
 */
public class DAO implements IDAO {
    private ConnectionPool connectionPool = new ConnectionPool();
    
    /**
     * This method have to login the users if the user and password were written
     * right. Otherways, will throw an exception.
     * 
     * @param user User: The data of the user that have logged or register
     * @return user will be returned to the UI
     * @throws IncorrectLoginException Exception: If the login doesnt exist or the 
     *  password is incorrect, IncorrectLoginException will be thrown.
     * @throws signupsigninserver.exceptions.IncorrectPasswordException
     * @throws Exception Generic exceptions
     */
    @Override
    public synchronized User login (User user) throws IncorrectLoginException,
            IncorrectPasswordException, Exception{
        try {
            Connection con = connectionPool.getConnection();
            
            String query ="SELECT * from usuario WHERE Login=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getLogin());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                if(rs.getString("Password").equals(user.getPassword())){
                    User dbUser = new User();
                    dbUser.setId(rs.getInt("Id"));
                    dbUser.setLogin(rs.getString("Login"));
                    dbUser.setFullName(rs.getString("FullName"));
                    dbUser.setEmail(rs.getString("Email"));
                    dbUser.setPassword(rs.getString("Password"));
                    
                    ps.close();
                    return dbUser;
                } else{
                    throw new IncorrectPasswordException();
                }               
            } else{
                throw new IncorrectLoginException();
            }
        } catch(Exception e){
            throw new Exception();
        }
    }
    /**
     * 
     * @param user User: The data of the user that have logged or register
     * @return user will be returned to the UI
     * @throws signupsigninserver.exceptions.RegisterFailedException
     * @throw RegisterFailedException Exception: 
     * @throws LoginExistsException Exception: This exception throws if the user 
     *  tries to register but the user is already taken.
     * @throws EmailExistsException Exception: This exception throws if the user
     *  tries to register but the email is already on use.
     * @throws LoginEmailExistException Exception: This exception throws if the user
     *  tries to register but the email and the user exist.
     * @throws Exception Generic exception
     */
    //--TOFIX
    @Override
    public synchronized User register (User user) throws LoginExistsException, 
        EmailExistsException, LoginEmailExistException, RegisterFailedException,
        Exception {
        //Connection con = connectionPool.getConnection();
        
        boolean loginFound=false;
        boolean emailFound=false;
        
        // Selects to validate if the login or email are on use
        String sqlStatement="SELECT * from usuario WHERE Login=?";
        String sqlStatement2="SELECT * from usuario WHERE Email=?";
        try{
            Connection con = connectionPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlStatement);
            
            ps.setString(1, user.getLogin());
            
            loginFound=ps.execute();
            
            ps=con.prepareStatement(sqlStatement2);
            
            ps.setString(1, user.getEmail());
            
            emailFound=ps.execute();
            
            if(emailFound && loginFound){
                /*if the email and user were found in the execute throws 
                *exception
                */
                connectionPool.releaseConnection(con);
                throw new LoginEmailExistException();
            } else if(loginFound){
                //if the login was found in the execute throws exception
                connectionPool.releaseConnection(con);
                throw new LoginExistsException();
            } else if(emailFound){
                //if the email was found in the exception thows exception
                connectionPool.releaseConnection(con);
                throw new EmailExistsException();
            } else{
                /* 
                *  If the the email and the user don't exist proceed 
                *  to the resgister
                *  This method inserts the values to the database if 
                *  the email or the 
                *  login arent repeated
                */                
                sqlStatement="insert into usuario values (?,?,?,?) ";
                ps = con.prepareStatement(sqlStatement, 
                        Statement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getFullName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPassword());
                
                ps.execute();
                int affectedRows = ps.executeUpdate();
                
                //checks if any of the rows in the database were updated
                if(affectedRows == 0){
                    // if not throws an exception
                    ps.close();
                    connectionPool.releaseConnection(con);
                    throw new RegisterFailedException();
                } else{
                    //if it was inserted returns the user, with the id setted
                    user.setId((int) ps.getGeneratedKeys().getLong(1));
                    ps.close();
                    connectionPool.releaseConnection(con);
                    return user;
                }
            }
        } catch(Exception ex){
            throw new Exception();
        }
    }
}
