//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Excepciones;

public class ErrorFechaNoValidaException extends IllegalArgumentException {
    
    public ErrorFechaNoValidaException(String mensaje){
        super(mensaje);
    }
    
    public ErrorFechaNoValidaException(){
        super("Formato de fecha no valido.");
    }
}
