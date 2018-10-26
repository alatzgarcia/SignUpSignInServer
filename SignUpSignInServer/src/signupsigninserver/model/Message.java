/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.model;

/**
 *
 * @author Alatz
 */
public class Message {
    private String message;
    private User data;

    public Message(String message, User data) {
      this.message = message;
      this.data = data;
    }
    
    public String getMessage(){
        return message;
    }
    
    public Object getData(){
        return data;
    }
}
