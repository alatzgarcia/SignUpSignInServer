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
import signupsigninserver.databaseAccess.IDAO;
import signupsigninserver.databaseAccess.IDAOFactory;
import signupsigninutilities.model.Message;
import signupsigninutilities.model.User;

/**
 *
 * @author Alatz
 */
public class ILogicImplementation {
    private static final Logger LOGGER = Logger.getLogger("signupsigninserver.ILogicImplementation");
    private static int PORT = 5010;
    
    public void start(){
        ServerSocket server = null;
        Socket socket = null;
        try{
            server = new ServerSocket(PORT);
            IDAO dao = IDAOFactory.getDAO(); 
            while(true){
                LOGGER.info("Awaiting for client connection on port: " + PORT);
                socket = server.accept();
                LOGGER.info("Client connected to the application.");
                Thread lThread = new Thread(new LogicThread(socket, dao));
                lThread.start();
                /*LOGGER.info("Client connected to the application.");
                LOGGER.info("Getting the client input stream...");
                ois = new ObjectInputStream(client.getInputStream());
                LOGGER.info("Getting the client output stream...");
                oos = new ObjectOutputStream(client.getOutputStream());
                LOGGER.info("Reading the client message...");
                //String msg = (String)ois.readObject();
                Message msg = (Message)ois.readObject();
                LOGGER.info("Mensaje recibido: " + msg.getMessage());
                //Message msg = (Message) ois.readObject();
                LOGGER.info("Client message arrived to the server.");
                //LOGGER.info(msg.getMessage());
                //oos.writeObject("ok");
                oos.writeObject(new Message("ok", new User()));
                //LOGGER.info("Creating the thread for this client...");
                //LogicThread lThread = new LogicThread(msg.getMessage(), msg.getData());  */
            }
        }
        catch(Exception e){
            LOGGER.info(e.getMessage());
            //--TOFIX
        } finally{
                try{
                if(socket!=null){
                    socket.close();
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
