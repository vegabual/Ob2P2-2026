//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Archivos;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class ArchivoLectura {
    
    private Scanner sc; //Scanner para leer el archivo
    private String linea; //Guarda la ultima linea que leyo el metodo hayMasLineas()
    
    public ArchivoLectura(String unNombre){ //Recibe el nombre del archivo y crea scanner para leerlo
        
        try{
            sc = new Scanner(Paths.get(unNombre)); //Inicia con el camino para encontrar y abrir el archivo
        }
        
        catch(IOException e){ //Si no encuentra el archivo hace lo siguiente 
            System.err.println("Error"); //Avisa
            System.exit(1); //Sale del programa
        }
    }//ArchivoLectura
    
    public boolean hayMasLineas(){ //Metodo que dice si hay mas lineas en el archivo
        
        boolean hay = false; //Por defecto, asume que no hay mas
        linea = null; //Resetea la linea
        
        if(sc.hasNext()){ //Si hay otra linea
            linea = sc.nextLine(); //Se lee la linea y se guarda
            hay = true; //Se pone en true para indicar que si hay una linea mas
        }
        
        return hay; //Devuelve true si hay linea y false si no hay
    }//HayMasLineas

    public String linea(){ //Metodo que devuelve la ultima leida por el metodo hayMasLineas
        return linea;
    }//Linea
    
    public void cerrar(){ //Cierra el escaner
        sc.close();
    }//Cerrar
    
}//Class