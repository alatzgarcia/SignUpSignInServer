/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.exceptions;

/**
 *
 * @author Alatz
 */
public class IncorrectPasswordException extends Exception{
    private static final String MESSAGE = "Error. La contraseña introducida "
            + "es incorrecta.";
    
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}