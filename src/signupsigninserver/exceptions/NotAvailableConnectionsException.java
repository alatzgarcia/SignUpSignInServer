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
public class NotAvailableConnectionsException extends Exception{
    private static final String MESSAGE = "Error. El servidor de la base"
            + "de datos no puede atender su petición en este momento."
            + "Inténtelo más tarde.";
    
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
