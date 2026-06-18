/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author windows
 */
public class ErrorCargaArchivoMalformado extends IllegalArgumentException {
    
    public ErrorCargaArchivoMalformado(String mensaje){
        super(mensaje);
    }
    
    public ErrorCargaArchivoMalformado(){
        super("El formato de uno de los archivos de guardado es inválido.");
    }
}