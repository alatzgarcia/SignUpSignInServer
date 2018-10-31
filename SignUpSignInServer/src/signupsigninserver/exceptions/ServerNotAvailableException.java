
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninuidesktop.exceptions;

/**
 *
 * @author Alatz
 */
public class ServerNotAvailableException extends Exception{
    private static final String MESSAGE = "Error. El servidor no se encuentra"
            + "disponible en estos momentos. Por favor, vuelva a intentarlo "
            + "en otro momento.";
    
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}