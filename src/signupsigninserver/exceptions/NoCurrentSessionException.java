/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.exceptions;

/**
 * Exception class for no current session error on program exit
 * @author Alatz
 */
public class NoCurrentSessionException extends Exception{
    private static final String MESSAGE = "Error. No se ha detectado ninguna"
            + " sesión iniciada que permita el cierre de la misma.";
    /**
     * Method to get the message of the exception
     * @return returns the error message
     */
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
