/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import signupsigninserver.databaseAccess.IDAO;
import signupsigninserver.exceptions.EmailExistsException;
import signupsigninserver.exceptions.IncorrectLoginException;
import signupsigninserver.exceptions.IncorrectPasswordException;
import signupsigninserver.exceptions.LoginEmailExistException;
import signupsigninserver.exceptions.LoginExistsException;
import signupsigninserver.exceptions.RegisterFailedException;
import signupsigninutilities.model.Message;
import signupsigninutilities.model.User;

/**
 * Class that allows multiple clients to connect at the same time. Managing the
 * client accepted by the ServerSocket.
 * @author Diego e Iker
 */
public class LogicThread implements Runnable{
    private static final Logger LOGGER=Logger.
            getLogger("signupsigninserver.logic.LogicThread");
    private Socket socket;  
    private IDAO dao;
    
    /**
     * Constructor for the LogicThread class, receives the ClientSocket, sent by the
     * ServerSocket after accepting it, and the thread will manage it.
     * @param socket Socket: The ClientSocket 
     * @param dao IDAO
     */
    public LogicThread(Socket socket, IDAO dao) {
        this.socket = socket;
        this.dao = dao;
    }
    
    /**
     * Method that runs when the thread it's started.
     * When the Thread runs gets the Input and Output stream and reads the object
     * Message sent by the client, depending of the message, the thread does a register,
     * login or an error method and writes a message "Ok" (Output) for the client.
     */
    @Override
    public void run(){
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        User dbUser;
        try{
            //Gets the streams (Output and Input
            LOGGER.info("Getting the client input stream...");
            ois = new ObjectInputStream(socket.getInputStream());
            LOGGER.info("Getting the client output stream...");
            oos = new ObjectOutputStream(socket.getOutputStream());
            LOGGER.info("Reading the client message...");
            Message msg = (Message)ois.readObject();
            LOGGER.info("Received message: " + msg.getMessage());
            
            //Depending of the message received from the client socket...
            switch (msg.getMessage()) {
                case "login":
                    //...executes the login calling the dao and then sends "ok" with the user(with additional data from DB)
                    LOGGER.info("Login.");
                    dbUser = dao.login(msg.getData());
                    oos.writeObject(new Message("ok", dbUser));
                    break;
                case "register":
                    //...executes the register calling the dao and then sends "ok" with the user(with additional data from DB)
                    LOGGER.info("Register.");
                    dbUser = dao.register(msg.getData());
                    oos.writeObject(new Message("ok", dbUser));
                    break;
                default:
                    //..does`t receive any of the methods so sends exception by object message
                    LOGGER.info("Error.");
                    oos.writeObject(new Message("invalidOperation", null));
            }
            // Catches the exceptions which can be thrown and writes a Message
            // and if there is an error throws another one
        } catch(LoginExistsException lee){ //--TOFIX
            try {
                oos.writeObject(new Message("loginExists", null));
            } catch (IOException ex) {
                Logger.getLogger(LogicThread.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        
        } catch (EmailExistsException ex) {
            try {
                oos.writeObject(new Message("emailExists", null));
            } catch (IOException ex1) {
                LOGGER.info("Error on returning error message.");
            }
        } catch(LoginEmailExistException ex){
            LOGGER.info("Error. Incorrect login and email introduced.");
            try {
                oos.writeObject(new Message("loginEmailExist", null));
            } catch (IOException ex1) {
                LOGGER.info("Error on returning error message.");
            }
        } catch (IncorrectLoginException ex) {
            LOGGER.info("Error. Incorrect login introduced.");
            try {
                oos.writeObject(new Message("incorrectLogin", null));
            } catch (IOException ex1) {
                LOGGER.info("Error on returning error message.");
            }
        } catch (IncorrectPasswordException ex) {
            try {
                oos.writeObject(new Message("incorrectPassword", null));
            } catch (IOException ex1) {
                LOGGER.info("Error on returning error message.");
            }
        } catch(RegisterFailedException ex){
            LOGGER.info("Error. Register operation failed.");
            try {
                oos.writeObject(new Message("registerFailed", null));
            } catch (IOException ex1) {
                LOGGER.info("Error on returning error message.");
            }
        } catch (ClassNotFoundException ex) {
            try {
                oos.writeObject(new Message("incorrectLogin", null));
            } catch (IOException ex1) {
                LOGGER.info("Error on returning error message.");
            }
        } catch(Exception ex){
            LOGGER.info("Error.");
            try {
                oos.writeObject(new Message("error", null));
            } catch (IOException ex1) {
                LOGGER.info("Error on returning error message.");
            }
        }
    }  
}