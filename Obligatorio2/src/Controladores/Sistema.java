//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Controladores;

import Modelo.Funcionario;
import Modelo.Cliente;
import Modelo.Tarifa;
import Modelo.Paquete;
import Controladores.ManagerDatos;
import Modelo.Envio;
import Modelo.Fecha;
import Utils.Utils;
import java.util.ArrayList;
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
    }
    
    
    /**
     * Inicializar un sistema guardado
     */
    /*ORIGINAL
    public void sistemaGuardado(){
        this.getManagerDatos().inicializar(true); //Debe extender el archivo existente, por lo tanto ext debe ser true
        
    }*/
    
    //TODAVIA NO FUNCIONA BIEN
    //////////////////////////////////////////////////////////
    public void sistemaGuardado(){
        
        this.getManagerDatos().inicializar(true);
        
    }
    ///////////////////////////////////////////////////////
    
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
    
    //Metodo de guardar cliente 
    public boolean guardarCliente(String unNombre, String unCelular, String unCorreo){
        
        boolean verifico = false;
        
        if(verificoNombre(unNombre)){
           Cliente cliente = new Cliente(unNombre, unCelular, unCorreo);
           this.getManagerDatos().guardarClienteEnArchivo(cliente);
           this.getClientes().add(cliente);
           verifico = true;
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
            }
            if(!unCelular.isEmpty()){
                cliente.setCelular(unCelular);
            }
            if(!unCorreo.isEmpty()) {
                cliente.setCorreo(unCorreo);
            }
            //Guardo los cambios segun los datos que pasan
            //Si estan vacios los Strings, guarda los datos que ya tenia
            this.getManagerDatos().guardarClienteEnArchivo(cliente);
            verifico = true;
        }
        return verifico;
    }
    
    //Metodo de guardar funcionario 
    public boolean guardarFuncionario(String unNombre, String unCelular, String unNumero, String unAnio){
        
        boolean verifico = false;
        
        if(verificoNombre(unNombre)){
           Funcionario funcionario = new Funcionario(unNombre, unCelular, unNumero, unAnio);
           this.getManagerDatos().guardarFuncionarioEnArchivo(funcionario);
           this.getFuncionarios().add(funcionario);
           verifico = true;
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
            }
            if(!unCelular.isEmpty()){
                funcionario.setCelular(unCelular);
            }
            if(!unNumero.isEmpty()) {
                funcionario.setNumero(unNumero);
            }
            if(!unAnio.isEmpty()) {
                funcionario.setAnio(unAnio);
            }
            //Guardo los cambios segun los datos que pasan
            //Si estan vacios los Strings, guarda los datos que ya tenia
            this.getManagerDatos().guardarFuncionarioEnArchivo(funcionario);
            verifico = true;
        }
        return verifico;
    }
    
    public Cliente encontrarCliente(String nombre){
        return Utils.encontrarCliente(this.getClientes(), nombre);
    }
    
    //<editor-fold desc="Paquetes">
    public boolean agregarPaquete(String id, Cliente cliente, Fecha fecha,String destinatario, String direccion, String departamento, int peso){
        boolean agregado = false;
        if(Utils.encontrarPaquete(paquetes,id) == null){
           Paquete paquete = new Paquete(id, cliente, fecha, destinatario, direccion, departamento, peso);
           paquete.setPrecio(precioPaquete(departamento, peso));
           this.getManagerDatos().guardarPaqueteEnArchivo(paquete);
           this.getPaquetes().add(paquete);
           agregado = true;
        }
        return agregado;
    }
    
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
    
    //</editor-fold>
}//Class