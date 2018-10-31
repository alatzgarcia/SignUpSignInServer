/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.logic;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;
import signupsignin.Message;
import signupsignin.User;
import signupsigninserver.databaseAccess.DAO;
import signupsigninserver.databaseAccess.IDAO;
import signupsigninserver.exceptions.InvalidOperationException;


/**
 *
 * @author Diego
 */
public class LogicThread implements Runnable{
    private static final Logger LOGGER=Logger.getLogger("signupsigninserver.logic.LogicThread");
    private Socket client;  
    private DAO dao;
    
    public LogicThread(Socket client, DAO dao) {
        this.client = client;
        this.dao = dao;
    }
    
     public void setDao(DAO dao) {
        this.dao = dao;
    }

    @Override
    public void run() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try{
            LOGGER.info("Getting the client input stream...");
            ois = new ObjectInputStream(client.getInputStream());
            LOGGER.info("Getting the client output stream...");
            oos = new ObjectOutputStream(client.getOutputStream());
            LOGGER.info("Reading the client message...");
            Message msg = (Message)ois.readObject();
          
            LOGGER.info("Mensaje recibido: " + msg.getMessage());
            LOGGER.info("Client message arrived to the server.");
            
            
            switch (msg.getMessage()) {
                case "login":
                     LOGGER.info("Option: login ");
                    //executes the login calling the dao
                    User user1=msg.getUser();
                    LOGGER.info(user1.getLogin());
                    User user2 =dao.login(user1);
                    
                    oos.writeObject(new Message("ok", user2));
                    break;
                case "register":
                    //executes the register calling the dao
                    //dao.register(msg.getData());
                    break;
                default:
                    //Does`t receive any of the methods so sends exception
                    throw new InvalidOperationException();                    
            }
        }catch(InvalidOperationException ioo){
            LOGGER.info(ioo.getMessage());
        }catch(Exception e){
            LOGGER.info(e.getMessage());
        }
    }
    
    
}
