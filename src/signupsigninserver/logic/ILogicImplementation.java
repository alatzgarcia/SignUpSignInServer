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
import java.util.logging.Level;
import java.util.logging.Logger;
import signupsigninserver.model.Message;
import signupsigninserver.model.User;

/**
 *
 * @author Alatz
 */
public class ILogicImplementation {
    private static final Logger LOGGER = Logger.getLogger("signupsigninserver.ILogicImplementation");
    private static int PORT = 5001;
    
    public void start(){
        ServerSocket server = null;
        Socket client = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try{
            server = new ServerSocket(PORT);
            while(true){
                LOGGER.info("awaiting for client connection on port: " + PORT);
                client = server.accept();
                LOGGER.info("Client connected to the application.");
                LOGGER.info("Getting the client input stream...");
                ois = new ObjectInputStream(client.getInputStream());
                LOGGER.info("Reading the client message...");
                Message msg = (Message) ois.readObject();
                LOGGER.info("Getting the client output stream...");
                oos = new ObjectOutputStream(client.getOutputStream());
                oos.writeObject(new Message("ok", new User()));
                //LOGGER.info("Creating the thread for this client...");
                //LogicThread lThread = new LogicThread(msg.getMessage(), msg.getData());
                
            }
        }
        catch(Exception e){
            //--TOFIX
        } finally{
            try{
                if(oos!=null){
                    oos.close();
                }
                if(ois!=null){
                    ois.close();
                }
                if(client!=null){
                    client.close();
                }
                if(server!=null){
                    server.close();
                }
            } catch(IOException ex){
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ILogicImplementation server = new ILogicImplementation();
        server.start();
    }
    
}
