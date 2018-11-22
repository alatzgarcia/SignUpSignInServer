/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsigninserver.exceptions;

/**
 *
 * @author 2dam
 */
public class ConfigurationParameterNotFoundException extends Exception {
    private static final String MESSAGE = "Error. No se han encontrado"
            + "los parámetros de configuración necesarios"
            + "para el funcionamiento de la aplicación.";
    /**
     * Method to get the message of the exception
     * @return returns the error message
     */
    public String getMessage(){
        return MESSAGE;
    }
}
