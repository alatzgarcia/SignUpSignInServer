/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninuidesktop.model;

/**
 *
 * @author Alatz
 */
public class Message {
    private String message;
    private Object data;

    public Message(String message, Object data) {
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
