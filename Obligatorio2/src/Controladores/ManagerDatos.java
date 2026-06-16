//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Controladores;

import Archivos.ArchivoGrabacion;
import Archivos.ArchivoLectura;
import Modelo.Envio;
import java.util.ArrayList;
import Modelo.Cliente;
import Modelo.Funcionario;
import Modelo.Paquete;
import Modelo.Tarifa;
import Utils.Utils;
import Controladores.Sistema;
import Excepciones.ErrorFechaNoValidaException;
import Modelo.Fecha;

/**
 * Clase para el guardado de archivos y recuperacion de datos del mismo
 */
public class ManagerDatos {
    
    private static final String ARCHIVO_PAQUETES = "Paquetes.txt";
    private static final String ARCHIVO_CLIENTES = "Clientes.txt";
    private static final String ARCHIVO_FUNCIONARIOS = "Funcionarios.txt";
    private static final String ARCHIVO_ENVIOS = "Envios.txt";
    private static final String ARCHIVO_TARIFAS_DEFECTO = "Tarifas.txt";
    private static final String ARCHIVO_TARIFAS = "Tarifas_Guardadas.txt";
    
    private static String[] deptosNorte = {"ARTIGAS", "SALTO", "PAYSANDU", "RIVERA", "TACUAREMBO"};
    private static String[] deptosOeste = {"RIO NEGRO", "SORIANO", "COLONIA", "SAN JOSE"};
    private static String[] deptosEste = {"CERRO LARGO", "TREINTA Y TRES", "LAVALLEJA", "ROCHA", "MALDONADO"};
    private static String[] deptosSur = {"DURAZNO", "FLORES", "FLORIDA", "CANELONES", "MONTEVIDEO"};
    
    //Guardamos referencias a las listas que se van a necesitar guardar
    private ArrayList<Cliente> clientes;
    private ArrayList<Funcionario> funcionarios;
    private ArrayList<Paquete> paquetes;
    private ArrayList<Tarifa> tarifas;
    private ArrayList<Envio> envios;
    
    private Sistema sistema;

    //<editor-fold desc="Getters and setters">
    private ArrayList<Cliente> getClientes() {
        return clientes;
    }

    private void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    private ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    private void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    private ArrayList<Paquete> getPaquetes() {
        return paquetes;
    }

    private void setPaquetes(ArrayList<Paquete> paquetes) {
        this.paquetes = paquetes;
    }

    private ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }

    private void setTarifas(ArrayList<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }
    
    public ArrayList<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(ArrayList<Envio> envios) {
        this.envios = envios;
    }
    //</editor-fold>
    
    public ManagerDatos(ArrayList<Cliente> clientes, ArrayList<Funcionario> funcionarios,ArrayList<Paquete> paquetes,ArrayList<Tarifa> tarifas, ArrayList<Envio> envios){
        this.setClientes(clientes);
        this.setFuncionarios(funcionarios);
        this.setPaquetes(paquetes);
        this.setTarifas(tarifas);
        this.setEnvios(envios);
    }
    
    public void inicializar(boolean ext){
        ArchivoGrabacion archClientes = null;
        ArchivoGrabacion archFuncionarios = null;
        ArchivoGrabacion archPaquetes = null;
        ArchivoGrabacion archEnvios = null;
        ArchivoGrabacion archTarifas = null;
        
        try{
            archClientes = new ArchivoGrabacion(ManagerDatos.ARCHIVO_CLIENTES, ext);
            archFuncionarios = new ArchivoGrabacion(ManagerDatos.ARCHIVO_FUNCIONARIOS, ext);
            archPaquetes = new ArchivoGrabacion(ManagerDatos.ARCHIVO_PAQUETES, ext);
            archEnvios = new ArchivoGrabacion(ManagerDatos.ARCHIVO_ENVIOS, ext);
            archTarifas = new ArchivoGrabacion(ARCHIVO_TARIFAS, ext);

            cargarTarifas(archTarifas, ext);
            
            if(ext){ //Usa datos guardados
                this.cargarClientes(); //El this es del sistema seleccionado
                this.cargarFuncionarios();
            }
            
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
        finally{
            archClientes.cerrar(); 
            archFuncionarios.cerrar();
            archPaquetes.cerrar();
        }
    }
    
    //<editor-fold desc="Cargar datos guardados">
    //Metodo para cargar los clientes en el sistema
    public void cargarClientes(){

        ArchivoLectura arch =  new ArchivoLectura(ARCHIVO_CLIENTES);

        while(arch.hayMasLineas()){
            String linea = arch.linea();
            String[] datos = linea.split("--");
            Cliente cliente = new Cliente(datos[0], datos[1], datos[2]);
            this.getClientes().add(cliente);
        }

        arch.cerrar();
    }
    
    //Metodo para cargar los funcionarios en el sistema
    public void cargarFuncionarios(){

        ArchivoLectura arch = new ArchivoLectura(ARCHIVO_FUNCIONARIOS);

        while(arch.hayMasLineas()){
            String linea = arch.linea();
            String[] datos = linea.split("--");
            Funcionario funcionario = new Funcionario(datos[0], datos[1], datos[2], datos[3]);
            this.getFuncionarios().add(funcionario);
        }
        arch.cerrar();
    }
    
    public void cargarTarifas(ArchivoGrabacion arch, boolean usarGuardado){
        ArchivoLectura datosACargar = new ArchivoLectura(ARCHIVO_TARIFAS);
        
        if(!usarGuardado){
            datosACargar = new ArchivoLectura(ARCHIVO_TARIFAS_DEFECTO);
        }
        
        while(datosACargar.hayMasLineas()){
            Tarifa tarifa = archivoATarifa(datosACargar.linea());
            this.getTarifas().add(tarifa);
            if(!usarGuardado){
                guardarTarifaEnArchivo(tarifa);
            }
        }
    }
    //</editor-fold>
    
    public Tarifa archivoATarifa(String lineaArchivo){
        String[] items = lineaArchivo.split("#");
        Tarifa tarifa = null;
        if(items.length == 2){
            String nombre = items[0];
            String[] precios = items[1].split(",");
            if(precios.length == 4){
                double cat1 = Double.parseDouble(precios[0]);
                double cat2 = Double.parseDouble(precios[1]);
                double cat3 = Double.parseDouble(precios[2]);
                double cat4 = Double.parseDouble(precios[3]);
                
                tarifa = new Tarifa(nombre, cat1, cat2, cat3, cat4, deptosPorZona(nombre));
            }
        }
        return tarifa;
    }
    
    public Paquete archivoAPaquete(String lineaArchivo) throws ErrorFechaNoValidaException {
        String[] items = lineaArchivo.split("--");
        Paquete paquete = null;
        if(items.length == 8){
            String id = items[0];
            Cliente cliente = Utils.encontrarCliente(this.getClientes(), items[1]);
            Fecha fechaIngreso = Fecha.parseFecha(items[2]);
            String destinatario = items[3];
            String direccion = items[4];
            String destino = items[5];
            int peso = Integer.parseInt(items[6]);

            paquete = new Paquete(id, cliente, fechaIngreso, destinatario, direccion, destino, peso);
            paquete.setPrecio(Double.parseDouble(items[7]));
        }
        return paquete;
    }
    
    
    private String[] deptosPorZona(String nombreZona){
        String[] deptos = null;
        switch (nombreZona.toUpperCase()) {
            case "ESTE":
                deptos = deptosEste;
                break;
            case "OESTE":
                deptos = deptosOeste;
                break;
            case "SUR":
                deptos = deptosSur;
                break;
            case "NORTE":
                deptos = deptosNorte;
                break;
        }
        return deptos;
    }
    
    public boolean guardarPaqueteEnArchivo(Paquete paquete){
        boolean seGuardo = false;
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_PAQUETES, true);
        arch.grabarLinea(paquete.toString());
        arch.cerrar();
        return seGuardo;
    }
    
    //Metodo para guardar clientes en archivo
     public boolean guardarClienteEnArchivo(Cliente cliente){  
        
        boolean seGuardo = false;
        
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_CLIENTES, true);
        arch.grabarLinea(cliente.toString());
        
        arch.cerrar();
        return seGuardo;
    }
     
    //Metodo para guardar funcionarios en archivo
     public boolean guardarFuncionarioEnArchivo(Funcionario funcionario){  
        
        boolean seGuardo = false;
        
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_FUNCIONARIOS, true);
        arch.grabarLinea(funcionario.toString());
        
        arch.cerrar();
        return seGuardo;
    }
       
    //Metodo para guardar funcionarios en archivo
    public boolean guardarTarifaEnArchivo(Tarifa tarifa){  
        boolean seGuardo = false;
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_TARIFAS, true);
        arch.grabarLinea(tarifa.toString());
        arch.cerrar();
        return seGuardo;
    }
    
    //Metodo para guardar envios en archivo
    public boolean guardarEnvioEnArchivo(Envio envio){  
        boolean seGuardo = false;
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_ENVIOS, true);
        arch.grabarLinea(envio.toString());
        arch.cerrar();
        return seGuardo;
    }
       
}