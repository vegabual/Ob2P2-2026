//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Archivos;

import java.io.*;
import java.util.Formatter;

public class ArchivoGrabacion {
    
    private Formatter out; //Guarda el objeto Formatter usado para escribir en el archivo
    
    public ArchivoGrabacion(String unNombre){ //Recibe el nombre del archivo y crea un Formatter nuevo
        
        try{
            out = new Formatter(unNombre); //Crea el Formatter para el archivo unNombre
        }
        
        catch(FileNotFoundException e){ //Si no se puede crear el archivo se da la excepcion
            System.out.println("No se puede crear."); //Avisa
            System.exit(1); //Sale del programa
        }
        
        catch(SecurityException e){ //Si no deja escribir en ese lugar se da la excepcion
            System.out.println("Sin permisos."); //Avisa
            System.exit(1); //Sale del programa
        }  
    }//ArchivoGrabacion V1
    
    public ArchivoGrabacion(String unNombre, boolean ext){ //Dice si se extiende o se sobrescribe el archivo
        
        //Si el parametro ext es true, se indica que se extiende
        //Si es el parametro ext es false, se sobreescribe
        
        try{
            FileWriter f = new FileWriter(unNombre, ext); //Si ext es true agrega al final, si false sobrescribe
            out = new Formatter(f); //Se crea un Formatter que escribe sobre el FileWriter
        }
        
        catch(IOException e){ //Detecta excepciones de IOExceptions
            System.out.println("No se puede crear/extender."); //Avisa
            System.exit(1); //Sale del programa
        }
    }//ArchivoGrabacion V2
    
    public void grabarLinea(String linea){ //Metodo para grabar una linea en el archivo
        out.format("%s%n", linea); // "%s" → para el string; "%n" → salto de linea
    }//GrabarLinea
    
    public void cerrar(){ //Metodo para cerrar el archivo
        out.close(); //Cierra el Formatter (si no se cierra pueden perderse datos)
    }//Cerrar
    
}//Class
