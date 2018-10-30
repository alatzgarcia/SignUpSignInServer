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
import signupsigninserver.exceptions.InvalidOperationException;
import signupsigninserver.exceptions.LoginExistsException;
import signupsigninutilities.model.Message;
import signupsigninutilities.model.User;

/**
 *
 * @author Diego
 */
public class LogicThread implements Runnable{
    private static final Logger LOGGER=Logger.getLogger("signupsigninserver.logic.LogicThread");
    private Socket socket;  
    private IDAO dao;
    
    public LogicThread(Socket socket, IDAO dao) {
        this.socket = socket;
        this.dao = dao;
    }
    
    @Override
    public void run() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try{
            LOGGER.info("Getting the client input stream...");
            ois = new ObjectInputStream(socket.getInputStream());
            LOGGER.info("Getting the client output stream...");
            oos = new ObjectOutputStream(socket.getOutputStream());
            LOGGER.info("Reading the client message...");
            Message msg = (Message)ois.readObject();
            /*LOGGER.info("Mensaje recibido: " + msg.getMessage());
            //Message msg = (Message) ois.readObject();
            LOGGER.info("Client message arrived to the server.");
            oos.writeObject(new Message("ok", new User()));
            */
            switch (msg.getMessage()) {
                case "login":
                    //executes the login calling the dao
                    //dao.login(msg.getData());
                    break;
                case "register":
                    //executes the register calling the dao
                    //dao.register(msg.getData());
                    break;
                default:
                    //Does`t receive any of the methods so sends exception
                    throw new InvalidOperationException();                    
            }
        }catch(Exception lee){ //--TOFIX
            try {
                oos.writeObject(new Message("loginExists", null));
            } catch (IOException ex) {
                Logger.getLogger(LogicThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }  
}
