//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Modelo;

import Utils.Utils;
import java.util.ArrayList;

public class Envio implements Comparable<Envio>{
    private Funcionario funcionario; //PARA EL METODO DE ENVIO
    private int numeroEnvio;
    private Fecha fechaEnvio;
    private ArrayList<Paquete> paquetes;
    private String estado;
    private String nombreZona;
    private static int ultimoId = 0;

    //<editor-fold desc="Getter y setter">
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public int getNumeroEnvio() {
        return numeroEnvio;
    }

    public void setNumeroEnvio(int numeroEnvio) {
        this.numeroEnvio = numeroEnvio;
    }

    public Fecha getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Fecha fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public ArrayList<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(ArrayList<Paquete> paquetes) {
        this.paquetes = paquetes;
    }
    
    public String getEstado() {
        return estado;
    }

    private void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public static int getUltimoId() {
        return ultimoId;
    }
    
    //</editor-fold>

    public Envio(Funcionario funcionario, Fecha fechaEnvio, String zona, ArrayList<Paquete> paquetes) {
        this.setFuncionario(funcionario);
        this.setNumeroEnvio(++ultimoId);
        this.setFechaEnvio(fechaEnvio);
        this.setEstado("CREADO");
        this.setNombreZona(zona);
        this.setPaquetes(paquetes);
    }
    
    public Envio(int id, Funcionario funcionario, Fecha fechaEnvio, String zona, String estado, ArrayList<Paquete> paquetes) {
        this.setFuncionario(funcionario);
        this.setNumeroEnvio(id);
        this.setFechaEnvio(fechaEnvio);
        this.setEstado(estado);
        this.setNombreZona(zona);
        this.setPaquetes(paquetes);
        ultimoId++;
    }
    
    public void agregarPaquete(Paquete paquete){
        this.getPaquetes().add(paquete);
    }

    public void agregarPaquetes(ArrayList<Paquete> paquetes){
        for(Paquete p : paquetes){
            this.getPaquetes().add(p);
        }
    }
    
    public void removerPaquete(String id){
        Paquete paquete = Utils.encontrarPaquete(this.getPaquetes(), id);
        if(paquete != null){
            paquete.aPendiente();
            this.getPaquetes().remove(paquete);
        }
    }
    
    public void cerrarEnvio(){
        this.setEstado("CERRADO");
        for(Paquete paquete: this.getPaquetes()){
            paquete.aRecibido();
        }
    }
    
    public String aGuardar(){
        String paquetesString = Utils.listaPaquetesAStringDeID(paquetes, ',');
        return this.getNumeroEnvio() + "--" + this.getFuncionario().getNombre() + "--" + this.getFechaEnvio().toString() + "--"  + this.getNombreZona() + "--" + this.getEstado() + "--" + paquetesString;
    }

    @Override
    public int compareTo(Envio o) {
        return o.getNumeroEnvio() - this.getNumeroEnvio();
    }
    
    @Override
    public String toString(){
        return "Envio " + this.getNumeroEnvio() + " - Fecha: " + this.getFechaEnvio().toString() + " - Zona: " + this.getNombreZona() + " - " + this.getPaquetes().size() + " paquetes.";
    }
}
