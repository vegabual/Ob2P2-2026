//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Modelo;

import Utils.Utils;
import java.util.ArrayList;

public class Envio {
    private Funcionario funcionario; //PARA EL METODO DE ENVIO
    private int numeroEnvio;
    private String fechaEnvio;
    private ArrayList<Paquete> paquetes;
    private String estado;
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

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public ArrayList<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(ArrayList<Paquete> paquetes) {
        this.paquetes = paquetes;
    }
    
    public void agregarPaquete(Paquete paquete){
        this.getPaquetes().add(paquete);
    }

    public String getEstado() {
        return estado;
    }

    private void setEstado(String estado) {
        this.estado = estado;
    }
    //</editor-fold>

    public Envio(Funcionario funcionario, int numeroEnvio, String fechaEnvio) {
        this.funcionario = funcionario;
        this.numeroEnvio = numeroEnvio;
        this.fechaEnvio = fechaEnvio;
        this.estado = "CREADO";
        this.paquetes = new ArrayList<Paquete>();
    }
    
    private void eliminarPaquete(String id){
        Paquete paquete = Utils.encontrarPaquete(this.getPaquetes(), id);
        if(paquete != null){
            this.getPaquetes().remove(paquete);
        }
    }
}
