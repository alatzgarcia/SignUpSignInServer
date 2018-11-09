/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import signupsigninserver.model.Message;

/**
 *
 * @author Diego
 */
public class ILogicImplementation {
    private final int PUERTO=1/*Meter el puerto*/;
    private static final Logger LOGGER=Logger
        .getLogger("signupsigninserver.logic.ILogicImplementation");
    
    /**
     * 
     */
    public void init(){
        ServerSocket servidor=null;
        Socket cliente=null;
        ObjectOutputStream oos=null;
        ObjectInputStream ois=null;
        try{
            servidor= new ServerSocket(PUERTO);
            while(true){
                // wait for a client to try to connect
                LOGGER.info("Esperand conexiones de cliente...");
                // allows the client to be connected
                cliente=servidor.accept();
                LOGGER.info("Cliente conectado");
                // 
                oos=new ObjectOutputStream(cliente.getOutputStream());
                ois=new ObjectInputStream(cliente.getInputStream());
                Message message=(Message)ois.readObject();
            }
        }catch(IOException ioe){
            LOGGER.info(ioe.getMessage());
        }catch(Exception e){
            LOGGER.info(e.getMessage());
        }
    }
    
}
