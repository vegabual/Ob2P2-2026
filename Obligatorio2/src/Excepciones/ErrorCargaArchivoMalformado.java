//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Excepciones;

public class ErrorCargaArchivoMalformado extends IllegalArgumentException {
    
    public ErrorCargaArchivoMalformado(String mensaje){
        super(mensaje);
    }
    
    public ErrorCargaArchivoMalformado(){
        super("El formato de uno de los archivos de guardado es inválido.");
    }
}