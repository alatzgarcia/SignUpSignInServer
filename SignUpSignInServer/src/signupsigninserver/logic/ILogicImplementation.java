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
import signupsignin.Message;
import signupsignin.User;
import signupsigninserver.databaseAccess.DAO;
import signupsigninserver.databaseAccess.IDAO;
import signupsigninserver.databaseAccess.IDAOFactory;


/**
 *
 * @author Nerea Jimenez
 */
public class ILogicImplementation {
    private static final Logger LOGGER = Logger.getLogger("signupsigninserver.ILogicImplementation");
    private static int PORT = 5001;
    
    public void start(){
                LOGGER.info("Inicio");
                ServerSocket server = null; 
		Socket client = null; 
		
                
                IDAO dao = IDAOFactory.getDAO();
		try{ 
                    
                    server = new ServerSocket(PORT);
                    while(true){
                        
                         LOGGER.info("Awaiting for client connection on port: " + PORT);
                        client = server.accept();
                        LOGGER.info("Client connected to the application.");
                        Thread thread = new Thread(new LogicThread(client, (DAO) dao));
                        thread.start();
			
                        /*LOGGER.info("Esperando conexiones");
			//server waits for connections
			client = server.accept(); 
			//a client connects 
                        LOGGER.info("Conectado");
			ois = new ObjectInputStream(client.getInputStream()); 
			oos = new ObjectOutputStream(client.getOutputStream());
			Message message = (Message) ois.readObject(); 
                        //String message = (String)ois.readObject();
                        LOGGER.info("Mensaje recibido");
			LOGGER.info(message.getMessage());*/
                        
                        
                                       
                       //oos.writeObject("ok");                          
                                                
                       
                        
                        
                    }
		} catch (Exception e) {
			
		}  finally{
                    try{
                        
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
        
	

    public static void main(String[] args) {
        ILogicImplementation server = new ILogicImplementation();
        server.start();
    }

    
}
