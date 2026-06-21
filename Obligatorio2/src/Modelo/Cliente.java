//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Modelo;

public class Cliente implements Comparable<Cliente>{
    
    private String nombre; //DEBE SER UNICO -- VERIFICACION
    private String celular; //ESTE NO NECESITA VERIFICACION
    private String correo; //ESTE NO NECESITA VERIFICACION
    
    //getters
    public String getNombre(){
        return nombre;
    }
    
    public String getCelular(){
        return celular;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    //setters
    public void setNombre(String unNombre){
        nombre = unNombre;
    }
    
    public void setCelular(String unCelular){
        celular = unCelular;
    }
    
    public void setCorreo(String unCorreo){
        correo = unCorreo;
    }
    
    //Constructor
    public Cliente(String unNombre, String unCelular, String unCorreo){
        this.setNombre(unNombre);
        this.setCelular(unCelular);
        this.setCorreo(unCorreo);
    }
    
    @Override
    public String toString(){
        return (this.getNombre() + "--" + this.getCelular() + "--" + this.getCorreo());
    }

    @Override
    public int compareTo(Cliente c) {
        return this.getNombre().compareTo(c.getNombre());
    }
       
}//Class