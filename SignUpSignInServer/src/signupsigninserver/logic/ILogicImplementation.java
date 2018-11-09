//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import signupsigninserver.databaseAccess.ConnectionPool;
import signupsigninserver.databaseAccess.DAO;
import signupsigninserver.databaseAccess.IDAO;
import signupsigninserver.databaseAccess.IDAOFactory;
import signupsigninserver.exceptions.NotAvailableConnectionsException;


/**
 * Class for the server logic implementation and the server socket 
 * @author Nerea Jimenez
 */
public class ILogicImplementation {
    private static final Logger LOGGER = 
            Logger.getLogger("signupsigninserver.ILogicImplementation");
    private static int maxThreads;
    private static int port;
    private static int threadsnum;
    private static int connections;
    
    /**
     * Method that creates a ServerSocket and waits for connections
     * It creates a thread for each client
     */
    public void start() throws NotAvailableConnectionsException{
                               
                ServerSocket server = null; 
                Socket client = null; 
		
                //Asks the IDAOFactory for a DAO object
                IDAO dao = IDAOFactory.getDAO();
		try{ 
                    getData();
                    server = new ServerSocket(port);
                    //the server keeps waiting for connections 
                    while(true){
                        
                        LOGGER.info("Awaiting for client connection on port: " + port);
                        client = server.accept();
                        LOGGER.info("Client connected to the application.");
                       
                        //when a client connects, a thread is created and it 
                        //takes charge of the execution
                        if(connections<maxThreads){
                            Thread thread = new Thread(
                                    new LogicThread(client, (DAO) dao));
                            threadsnum++;
                            thread.start();
                            threadsnum--;
                        }else
                            throw new NotAvailableConnectionsException();
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
        
	
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) throws NotAvailableConnectionsException {
        ILogicImplementation server = new ILogicImplementation();
        server.start();
    }
    /**
     * Method that takes the parameters from the config file
     */
    private void getData() {
        Properties config = new Properties();
	FileInputStream input = null;
	try {
            input = new FileInputStream("src/signupsigninserver/config/connection.properties");
            config.load(input);
            port=Integer.parseInt(config.getProperty("port"));
            maxThreads=Integer.parseInt(config.getProperty("max_threads"));
           
         } catch (FileNotFoundException ex) {
            LOGGER.info("No encuentra archivo");
        } catch (IOException ex) {
            Logger.getLogger(ILogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null)
		try {
                    input.close();
            } catch (IOException ex) {
                Logger.getLogger(ILogicImplementation.class.getName()).log(Level.SEVERE, null, ex);
            } 
	}
    }

    
}
