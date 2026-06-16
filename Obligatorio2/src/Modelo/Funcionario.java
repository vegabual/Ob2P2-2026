//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Modelo;

public class Funcionario {
    
    private String nombre;
    private String celular;
    private String numero;
    private String anio;
    
    //getters
    public String getNombre(){
        return nombre;
    }
    
    public String getCelular(){
        return celular;
    }
    
    public String getNumero(){
        return numero;
    }  
    
    public String getAnio(){
        return anio;
    }   
    
    //setters
    public void setNombre(String unNombre){
        nombre = unNombre;
    }
    
    public void setCelular(String unCelular){
        celular = unCelular;
    }
    
    public void setNumero(String unNumero){
        numero = unNumero;
    }
    
    public void setAnio(String unAnio){
        anio = unAnio;
    }
    
    //Constructor
    public Funcionario(String unNombre, String unCelular, String unNumero, String unAnio){
        this.setNombre(unNombre);
        this.setCelular(unCelular);
        this.setNumero(unNumero);
        this.setAnio(unAnio);
    }
    
    @Override
    public String toString(){ 
        return (this.getNombre() + "--" + this.getCelular() + "--" + this.getNumero() + "--" + this.getAnio());
    }
    
}//Class