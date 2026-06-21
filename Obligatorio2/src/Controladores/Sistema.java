//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Controladores;

import Modelo.Funcionario;
import Modelo.Cliente;
import Modelo.Tarifa;
import Modelo.Paquete;
import Controladores.ManagerDatos;
import Excepciones.ErrorCargaArchivoMalformado;
import Modelo.Envio;
import Modelo.Fecha;
import Utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Sistema {
    private ArrayList<Cliente> clientes;
    private ArrayList<Funcionario> funcionarios;
    private ArrayList<Paquete> paquetes;
    private ArrayList<Envio> envios;
    private ArrayList<Tarifa> tarifas;
    
    private ManagerDatos managerDatos;
    
    //<editor-fold desc="Getters and setters">
    //getters
    public ArrayList<Cliente> getClientes(){
        return clientes;
    }
    
    public ArrayList<Funcionario> getFuncionarios(){
        return funcionarios;
    }
    
    public ArrayList<Tarifa> getTarifas(){
        return tarifas;
    }
    
    public ArrayList<Paquete> getPaquetes(){
        return paquetes;
    }  

    public ArrayList<Envio> getEnvios() {
        return envios;
    }
    
    public ManagerDatos getManagerDatos() {
        return managerDatos;
    }
    
    //setters
    public void setClientes(ArrayList<Cliente> listaClientes){
        clientes = listaClientes;
    }
    
    public void setFuncionarios(ArrayList<Funcionario> listaFuncionarios){
        funcionarios = listaFuncionarios;
    }
    
    public void setTarifas(ArrayList<Tarifa> tarifario){
        tarifas = tarifario;
    }
    
    public void setPaquetes(ArrayList<Paquete> listaPaquetes){
        paquetes = listaPaquetes;
    }

    public void setEnvios(ArrayList<Envio> envios) {
        this.envios = envios;
    }

    public void setManagerDatos(ManagerDatos managerDatos) {
        this.managerDatos = managerDatos;
    }
    //</editor-fold>
    
    private static String[] deptosNorte = {"ARTIGAS", "SALTO", "PAYSANDU", "RIVERA", "TACUAREMBO"};
    private static String[] deptosOeste = {"RIO NEGRO", "SORIANO", "COLONIA", "SAN JOSE"};
    private static String[] deptosEste = {"CERRO LARGO", "TREINTA Y TRES", "LAVALLEJA", "ROCHA", "MALDONADO"};
    private static String[] deptosSur = {"DURAZNO", "FLORES", "FLORIDA", "CANELONES", "MONTEVIDEO"};
    
    public Sistema(){ //Creo el objeto sistema en Menu
        this.setClientes(new ArrayList<Cliente>());
        this.setFuncionarios(new ArrayList<Funcionario>());
        this.setPaquetes(new ArrayList<Paquete>());
        this.setTarifas(new ArrayList<Tarifa>());
        this.setEnvios(new ArrayList<Envio>());
        this.setManagerDatos(new ManagerDatos(this.getClientes(), this.getFuncionarios(), this.getPaquetes(), this.getTarifas(), this.getEnvios()));
        //Se le pasa referencias a las listas de datos de clientes, funcionarios, tarifas y paquetes
    }
    
    @Override
    public String toString(){ 
        return ("Cliente: " + this.getClientes() + "\nFuncionario: " + 
                this.getFuncionarios() + "\nPaquete: " + this.getPaquetes()); // No creo lo necesitemos
    }
    
    /**
     * Inicializar un sistema vacio
     */
    public void sistemaNuevo(){
        this.getManagerDatos().inicializar(false); //Debe sobre escribir, no extender, por lo tanto ext debe ser false
        this.getManagerDatos().generarLog("Inicio de sistema nuevo.");
    }
    
    public void sistemaGuardado() throws ErrorCargaArchivoMalformado{
        this.getManagerDatos().inicializar(true);
        this.getManagerDatos().generarLog("Carga de sistema guardado.");
    }
    
    //Metodo para verificar que un nombre sea unico entre clientes y funcionarios
    private boolean verificoNombre(String unNombre){
        
        boolean verifico = false;
        
        if(Utils.encontrarCliente(clientes, unNombre) == null){
            if(Utils.encontrarFuncionario(funcionarios, unNombre) == null){
                verifico = true;  
            }
        }
        return verifico;
    }
    
    public ArrayList<String> getLogs(){
        return this.getManagerDatos().getLogs();
    }
    
    public void limpiarLogs(){
        this.getManagerDatos().limpiarLogs();
        this.getManagerDatos().generarLog("Limpieza de logs disparada por usuario.");
    }
    
    //<editor-fold desc="Cliente">
    //Metodo de guardar cliente 
    public boolean guardarCliente(String unNombre, String unCelular, String unCorreo){
        
        boolean verifico = false;
        
        if(verificoNombre(unNombre)){
           Cliente cliente = new Cliente(unNombre, unCelular, unCorreo);
           this.getManagerDatos().guardarNuevoClienteEnArchivo(cliente);
           this.getClientes().add(cliente);
           verifico = true;
           this.getManagerDatos().generarLog("Ingreso de cliente " + unNombre + ".");
        }
        return verifico;
    }
    
    //Metodo de guardar cliente modificado
    public boolean ModificarCliente(String unNombreActual, String unNombreNuevo, String unCelular, String unCorreo){
        
        boolean verifico = false;
        
        Cliente cliente = Utils.encontrarCliente(clientes, unNombreActual); //Busco el cliente con el nombre actual
        
        if(cliente != null && verificoNombre(unNombreNuevo)){ //Si el nombre no esta vacio verifico si lso datos estan o no vacios
            if(!unNombreNuevo.isEmpty()){
                cliente.setNombre(unNombreNuevo);
                this.getManagerDatos().generarLog("Modificacion de cliente " + unNombreActual + " a " + unNombreNuevo + ".");
            }
            else{
                this.getManagerDatos().generarLog("Modificacion de cliente " + unNombreActual + ".");
            }
            if(!unCelular.isEmpty()){
                cliente.setCelular(unCelular);
            }
            if(!unCorreo.isEmpty()) {
                cliente.setCorreo(unCorreo);
            }
            //Guardo los cambios segun los datos que pasan
            //Si estan vacios los Strings, guarda los datos que ya tenia
            this.getManagerDatos().guardarModificacionClienteEnArchivo();
            if(!unNombreNuevo.equals(unNombreActual) &&  this.getPaquetesPorCliente(unNombreNuevo).size() > 0){
                this.getManagerDatos().guardarModificacionPaqueteEnArchivo();
            }
            verifico = true;
        }
        return verifico;
    }
    
    public Cliente encontrarCliente(String nombre){
        return Utils.encontrarCliente(this.getClientes(), nombre);
    }
    //</editor-fold>
    
    //<editor-fold desc="Funcionario">
    
    //Metodo de guardar funcionario 
    public boolean guardarFuncionario(String unNombre, String unCelular, int unNumero, int unAnio){
        
        boolean verifico = false;
        
        if(verificoNombre(unNombre)){
           Funcionario funcionario = new Funcionario(unNombre, unCelular, unNumero, unAnio);
           this.getManagerDatos().guardarNuevoFuncionarioEnArchivo(funcionario);
           this.getFuncionarios().add(funcionario);
           verifico = true;
           this.getManagerDatos().generarLog("Ingreso de funcionario " + unNombre + ".");
        }
        return verifico;
    }
    
    //Metodo de guardar funcionario modificado
    public boolean ModificarFuncionario(String unNombreActual, String unNombreNuevo, String unCelular, String unNumero, String unAnio){
        
        boolean verifico = false;
        
        Funcionario funcionario = Utils.encontrarFuncionario(funcionarios, unNombreActual); //Busco el cfuncionario con el nombre actual
        
        if(verificoNombre(unNombreNuevo) && funcionario != null){ //Si el nombre no esta vacio verifico si los datos estan o no vacios
            if(!unNombreNuevo.isEmpty()){
                funcionario.setNombre(unNombreNuevo);
                this.getManagerDatos().generarLog("Modificacion de funcionario " + unNombreActual + " a " + unNombreNuevo + ".");
            }
            else{
                this.getManagerDatos().generarLog("Modificacion de funcionario " + unNombreActual + ".");
            }
            if(!unCelular.isEmpty()){
                funcionario.setCelular(unCelular);
            }
            if(!unNumero.isEmpty()) {
                int numFuncionario = Integer.parseInt(unNumero);
                funcionario.setNumero(numFuncionario);
            }
            if(!unAnio.isEmpty()) {
                int anioEntero = Integer.parseInt(unAnio);
                funcionario.setAnio(anioEntero);
            }
            //Guardo los cambios segun los datos que pasan
            //Si estan vacios los Strings, guarda los datos que ya tenia
            this.getManagerDatos().guardarModificacionFuncionarioEnArchivo();
            
            if(!unNombreNuevo.equals(unNombreActual) && this.getEnviosPorFuncionario(unNombreNuevo).size() > 0){
                this.getManagerDatos().guardarModificacionEnvioEnArchivo();
            }
            verifico = true;
        }
        return verifico;
    }
    
    public Funcionario encontrarFuncionario(String nombre){
        return Utils.encontrarFuncionario(this.getFuncionarios(), nombre);
    }
    
    //</editor-fold>
    
    //<editor-fold desc="Tarifa">
    public double precioPaquete(String depto, int peso){
        double precio = 0;
        boolean zonaEncontrada = false;
        Iterator<Tarifa> it = this.getTarifas().iterator();
        while(it.hasNext() && !zonaEncontrada){
            Tarifa t = it.next();
            if(t.deptoPerteneceAZona(depto)){
                precio = t.precioEnvio(peso);
                zonaEncontrada = true;
            }
        }
        return precio;
    }
    
    public String getNombreZona(String depto){
        String nombreZona = "";
        boolean zonaEncontrada = false;
        Iterator<Tarifa> it = this.getTarifas().iterator();
        while(it.hasNext() && nombreZona.isEmpty()){
            Tarifa t = it.next();
            if(t.deptoPerteneceAZona(depto)){
                nombreZona = t.getNombre();
            }
        }
        return nombreZona;
    }
    
    //Metodo que se usa para modificar las tarifas segun el porcentaje
    public boolean modificarTarifas(String accion, double porcentaje){

        boolean seGuardoOk = false;
        double aux = 0; //Auxiliar para hacer las operaciones
        
        if(accion.equalsIgnoreCase("Aumentar")){ //Para aumento de tarifas
            aux = 1 + (porcentaje/100); //Sumo 1 (seria el precio actual) mas el porcentaje calculado (lo que le aumento al precio)
        }
        else if(accion.equalsIgnoreCase("Disminuir")){ //Para disminuir tarifas
            aux = 1 - (porcentaje/100); //Idem al aumento pero restando el porcentaje al precio original
        }
        
        //Saco de la lista las tarifas
        for(int i = 0; i < this.getTarifas().size(); i++){
            Tarifa tarifa = this.getTarifas().get(i);
            //Saco el precio actual, lo modifico y lo guardo
            tarifa.setPrecioCat1((int) Math.round(tarifa.getPrecioCat1() * aux));
            tarifa.setPrecioCat2((int)Math.round(tarifa.getPrecioCat2() * aux));
            tarifa.setPrecioCat3((int)Math.round(tarifa.getPrecioCat3() * aux));
            tarifa.setPrecioCat4((int)Math.round(tarifa.getPrecioCat4() * aux));
            
            seGuardoOk = true;
        }
        this.getManagerDatos().guardarTarifasModificadas(); //Llamo al metodo que las guarda en la lista de nuevo
        this.getManagerDatos().generarLog("Modificacion de tarifas - " + accion + " " + porcentaje + "%." );
        return seGuardoOk;
    }
    //</editor-fold>
    
    //<editor-fold desc="Paquetes">
    public boolean guardarPaquete(String id, Cliente cliente, Fecha fecha,String destinatario, String direccion, String departamento, int peso){
        boolean agregado = false;
        if(Utils.encontrarPaquete(paquetes,id) == null){
            double precio = this.precioPaquete(departamento, peso);
            String nombreZona = this.getNombreZona(departamento);
            Paquete paquete = new Paquete(id, cliente, fecha, destinatario, direccion, departamento, peso, precio, nombreZona);
            this.getManagerDatos().guardarPaqueteEnArchivo(paquete);
            this.getPaquetes().add(paquete);
            agregado = true;
            this.getManagerDatos().generarLog("Ingreso de paquete del cliente " + cliente.getNombre() + ".");
        }
        return agregado;
    }
    
    public ArrayList<Paquete> getPaquetesPorEstado(String estado){
        ArrayList<Paquete> filtrados = new ArrayList<Paquete>();
        for(Paquete p: this.getPaquetes()){
            if(p.getEstado().equalsIgnoreCase(estado)){
                filtrados.add(p);
            }
        }
        Collections.sort(filtrados);
        return filtrados;
    }
    
    public ArrayList<Paquete> getPaquetesPorEstadoYZona(String estado, String nombreZona){
        ArrayList<Paquete> filtrados = new ArrayList<Paquete>();
        for(Paquete p: this.getPaquetes()){
            if(p.getEstado().equalsIgnoreCase(estado) && p.getNombreZona().equalsIgnoreCase(nombreZona)){
                filtrados.add(p);
            }
        }
        Collections.sort(filtrados);
        return filtrados;
    }
    
    public ArrayList<Paquete> getPaquetesPorCliente(String nombreCliente){
        ArrayList<Paquete> filtrados = new ArrayList<Paquete>();
        for(Paquete p: this.getPaquetes()){
            if(p.getCliente().getNombre().equalsIgnoreCase(nombreCliente)){
                filtrados.add(p);
            }
        }
        Collections.sort(filtrados);
        return filtrados;
    }
    
    public ArrayList<Paquete> getPaquetesPorClienteYEstado(String nombreCliente, String estado){
        ArrayList<Paquete> filtrados = new ArrayList<Paquete>();
        for(Paquete p: this.getPaquetes()){
            if(p.getCliente().getNombre().equalsIgnoreCase(nombreCliente) && p.getEstado().equalsIgnoreCase(estado)){
                filtrados.add(p);
            }
        }
        Collections.sort(filtrados);
        return filtrados;
    }
    //</editor-fold>
    
    //<editor-fold desc="Envio">
    public boolean guardarEnvio(Funcionario funcionario, Fecha fecha, String nombreZona, ArrayList<Paquete> paquetes){
        Envio envio = new Envio(funcionario, fecha, nombreZona, paquetes);
        for(Paquete p : paquetes){
            p.aEnviado();
        }
        this.getEnvios().add(envio);
        this.getManagerDatos().guardarEnvioEnArchivo(envio);
        this.getManagerDatos().guardarModificacionPaqueteEnArchivo();
        
        this.getManagerDatos().generarLog("Ingreso de envio " + envio.getNumeroEnvio() + ".");
        return true;
    }
    
    public ArrayList<Envio> getEnviosPorFuncionario(String nombreFuncionario){
        ArrayList<Envio> filtrados = new ArrayList<Envio>();
        for(Envio e: this.getEnvios()){
            if(e.getFuncionario().getNombre().equalsIgnoreCase(nombreFuncionario)){
                filtrados.add(e);
            }
        }
        
        return filtrados;
    }
    
    public void RecibirEnvio(Envio envio, ArrayList<Paquete> paquetesABorrar) {
        for(Paquete paquete : paquetesABorrar){
            envio.removerPaquete(paquete.getId());
        }
        envio.cerrarEnvio();
        
        this.getManagerDatos().guardarModificacionPaqueteEnArchivo();
        this.getManagerDatos().guardarModificacionEnvioEnArchivo();
        
        this.getManagerDatos().generarLog("Recepción del envío " + envio.getNumeroEnvio() + ".");
    }
    //</editor-fold>

    
}//Class