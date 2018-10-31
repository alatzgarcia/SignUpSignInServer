/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsignin;

import java.io.Serializable;

/**
 *
 * @author Alatz
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1;
    private String message;
    private User user;

    public Message(String message, User user) {
      this.message = message;
      this.user = user;
    }
    
    public String getMessage(){
        return message;
    }
    
    public User getUser(){
        return user;
    }
}