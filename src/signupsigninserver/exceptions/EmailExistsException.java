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
public class EmailExistsException extends Exception{
    private static final String MESSAGE = "Error. El email introducido "
            + "ya existe. Por favor, introduzca otro email diferente.";
    
    @Override
    public String getMessage(){
        return MESSAGE;
    }
}
