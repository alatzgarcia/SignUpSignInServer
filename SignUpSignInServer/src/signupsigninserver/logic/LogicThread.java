/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.logic;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import signupsigninserver.dao.IDAO;
import signupsigninserver.exceptions.InvalidOperationException;
import signupsigninuidesktop.model.Message;
import signupsigninuidesktop.model.User;
//
/**
 * This method allows multiple clients to connect.
 * @author Diego e Iker
 */
public class LogicThread implements Runnable{
    private static final Logger LOGGER=Logger.getLogger("signupsigninserver.logic.LogicThread");
    private Socket client;  
    private IDAO dao;
    
    public LogicThread(Socket client) {
        this.client = client;
    }
    
     public void setDao(IDAO dao) {
        this.dao = dao;
    }
    /**
     * The thread when it runs, receives a client because he is doing as the server
     * logic , then, depending the method sends it to the DAO, including some 
     * exceptions.
     */
    @Override
    public void run() {
        //creates the input and output
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try{
            //gets the input
            LOGGER.info("Getting the client input stream...");
            ois = new ObjectInputStream(client.getInputStream());
            //gets the output
            LOGGER.info("Getting the client output stream...");
            oos = new ObjectOutputStream(client.getOutputStream());
            //read the messge sent by the client
            LOGGER.info("Reading the client message...");
            Message msg = (Message)ois.readObject();
            LOGGER.log(Level.INFO, "Mensaje recibido: {0}", msg.getMessage());
            //Message msg = (Message) ois.readObject();
            LOGGER.info("Client message arrived to the server.");
            oos.writeObject(new Message("ok", new User()));
            
            //depending of the message...
            switch (msg.getMessage()) {
                case "login":
                    //...executes the login calling the dao
                    LOGGER.info("Login.");
                    dao.login(msg.getData());
                    break;
                case "register":
                    //...executes the register calling the dao
                    LOGGER.info("Register.");
                    dao.register(msg.getData());
                    break;
                default:
                    //..does`t receive any of the methods so sends exception
                    LOGGER.info("Error.");
                    throw new InvalidOperationException();                    
            }
        }catch(InvalidOperationException ioo){
            LOGGER.info(ioo.getMessage());
        }catch(Exception e){
            LOGGER.info(e.getMessage());
        }
    }
}