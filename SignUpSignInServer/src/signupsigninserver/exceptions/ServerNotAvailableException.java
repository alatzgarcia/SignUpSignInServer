
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.exceptions;

/**
 * Sets the message of an exception an returns it for the display.
 * @author Alatz
 */
public class ServerNotAvailableException extends Exception{
    private static final String MESSAGE = "Error. El servidor no se encuentra"
            + "disponible en estos momentos. Por favor, vuelva a intentarlo "
            + "en otro momento.";
    
      /**
     * Displays the message when the exception throws.
     * @return MESSAGE String: The message that will be shown.
     */
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}