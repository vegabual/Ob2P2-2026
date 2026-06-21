//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Modelo;

public class Funcionario implements Comparable<Funcionario> {
    
    private String nombre;
    private String celular;
    private int numero;
    private int anio;
    
    //getters
    public String getNombre(){
        return nombre;
    }
    
    public String getCelular(){
        return celular;
    }
    
    public int getNumero(){
        return numero;
    }  
    
    public int getAnio(){
        return anio;
    }   
    
    //setters
    public void setNombre(String unNombre){
        nombre = unNombre;
    }
    
    public void setCelular(String unCelular){
        celular = unCelular;
    }
    
    public void setNumero(int unNumero){
        numero = unNumero;
    }
    
    public void setAnio(int unAnio){
        if(unAnio < 27){
            unAnio += 2000; //Asumo que se ingresaron los ultimos 2 digitos de un año del 2000
        } else if(unAnio < 100){
            unAnio += 1900; // Asumo que se ingresaron los ultimos 2 digitos de un año del 1900
        }
        anio = unAnio;
    }
    
    //Constructor
    public Funcionario(String unNombre, String unCelular, int unNumero, int unAnio){
        this.setNombre(unNombre);
        this.setCelular(unCelular);
        this.setNumero(unNumero);
        this.setAnio(unAnio);
    }
    
    @Override
    public String toString(){ 
        return (this.getNombre() + "--" + this.getCelular() + "--" + this.getNumero() + "--" + this.getAnio());
    }

    @Override
    public int compareTo(Funcionario f) {
        int resultado;
        if(this.getAnio() == f.getAnio()){
            resultado = this.getNombre().compareTo(f.getNombre());
        } else{
            resultado = f.getAnio() - this.getAnio();
        }
        return resultado;
    }
    
}//Class