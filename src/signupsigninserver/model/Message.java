/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.model;

import java.io.Serializable;

/**
 *
 * @author Alatz
 */
public class Message implements Serializable {
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
