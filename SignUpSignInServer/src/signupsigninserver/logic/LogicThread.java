/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.logic;

import signupsigninuidesktop.model.Message;

/**
 * This class is a thread that allows to have multiple clients.
 * @author Diego
 */
public class LogicThread extends Thread{
    
    private String message;
    private Object data; 
    
    public LogicThread(Message msg) {
        this.start();
        this.message=msg.getMessage();
        this.data=msg.getData();
    }
    
    @Override
    public void run(){
        try{
            
        }catch(InterruptedException ie){
            
        }
    }
}
