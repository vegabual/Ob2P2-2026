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
import Excepciones.ErrorCargaArchivoMalformado;
import Excepciones.ErrorFechaNoValidaException;
import Modelo.Fecha;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

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
    private static final String ARCHIVO_LOGS = "Transacciones.log";
    
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
    
    public void inicializar(boolean ext) throws ErrorCargaArchivoMalformado{
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

            cargarTarifas(ext);
            
            if(ext){ //Usa datos guardados
                this.cargarClientes(); //El this es del sistema seleccionado
                this.cargarFuncionarios();
                this.cargarPaquetes();
                this.cargarEnvios();
            }
            
        } catch(Exception e){
            ErrorCargaArchivoMalformado exCarga = new ErrorCargaArchivoMalformado();
            exCarga.setStackTrace(e.getStackTrace()); //repite el stacktrace de la excepcion original para poder debuggear
            exCarga.addSuppressed(e); //Se guarda referencia al error que sucedio asi se puede debuggear
            throw exCarga;
        }
        finally{
            archClientes.cerrar(); 
            archFuncionarios.cerrar();
            archPaquetes.cerrar();
            archEnvios.cerrar();
            archTarifas.cerrar();
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
    
    //Metodo para cargar las tarifas en el sistema
    public void cargarTarifas(boolean usarGuardado){
        ArchivoLectura datosACargar = new ArchivoLectura(ARCHIVO_TARIFAS);
        
        if(!usarGuardado){
            datosACargar = new ArchivoLectura(ARCHIVO_TARIFAS_DEFECTO);
        }
        
        while(datosACargar.hayMasLineas()){
            Tarifa tarifa = archivoATarifa(datosACargar.linea());
            if(tarifa != null){
                this.getTarifas().add(tarifa);
                if(!usarGuardado){
                    guardarTarifaEnArchivo(tarifa);
                }
            }
        }
    }
    
    //Metodo para cargar los funcionarios en el sistema
    public void cargarPaquetes(){
        ArchivoLectura arch = new ArchivoLectura(ARCHIVO_PAQUETES);

        while(arch.hayMasLineas()){
            String linea = arch.linea();
            String[] datos = linea.split("--");
            Paquete paquete = archivoAPaquete(linea);
            if(paquete != null){
                this.getPaquetes().add(paquete);
            }
        }
        arch.cerrar();
    }
    
    public void cargarEnvios(){
        ArchivoLectura arch = new ArchivoLectura(ARCHIVO_ENVIOS);

        while(arch.hayMasLineas()){
            String linea = arch.linea();
            String[] datos = linea.split("--");
            Envio envio = archivoAEnvio(linea);
            if(envio != null){
                this.getEnvios().add(envio);
            }
        }
        arch.cerrar();
    }
    //</editor-fold>
    
    //<editor-fold desc="Archivo a objeto">
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
        if(items.length == 10){
            String id = items[0];
            Cliente cliente = Utils.encontrarCliente(this.getClientes(), items[1]);
            Fecha fechaIngreso = Fecha.parseFecha(items[2]);
            String destinatario = items[3];
            String direccion = items[4];
            String destino = items[5];
            int peso = Integer.parseInt(items[6]);
            double precio = Double.parseDouble(items[7]);
            String estado = items[8];
            String zona = items[9];
            
            paquete = new Paquete(id, cliente, fechaIngreso, destinatario, direccion, destino, peso, precio, zona, estado);
        }
        return paquete;
    }
    
    public Envio archivoAEnvio(String lineaArchivo) throws ErrorFechaNoValidaException {
        String[] items = lineaArchivo.split("--");
        Envio envio = null;
        if(items.length == 6){
            int numeroEnvio = Integer.parseInt(items[0]);
            Funcionario funcionario = Utils.encontrarFuncionario(this.getFuncionarios(), items[1]);
            Fecha fecha = Fecha.parseFecha(items[2]);
            String zona = items[3];
            String estado = items[4];
            ArrayList<Paquete> paquetes = listaIdAPaquetes(items[5]);

            envio = new Envio(numeroEnvio, funcionario, fecha, zona, estado, paquetes);
        }
        return envio;
    }
    
    public ArrayList<Paquete> listaIdAPaquetes(String idsPaquetes) {
        ArrayList<Paquete> paquetes = new ArrayList<Paquete>();
        StringTokenizer st = new StringTokenizer(idsPaquetes, ",");
        while(st.hasMoreTokens()){
            String id = st.nextToken();
            Paquete paquete = Utils.encontrarPaquete(this.getPaquetes(), id);
            paquetes.add(paquete);
        }
        return paquetes;
    }
    //</editor-fold>
    
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
    
    //<editor-fold desc="Guardar en archivo">
    //Metodo para guardar clientes en archivo
    public void guardarNuevoClienteEnArchivo(Cliente cliente){
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_CLIENTES, true);
        arch.grabarLinea(cliente.toString());

        arch.cerrar();
    }
    
    //Metodo para guardar todos los clientes de nuevo
    public void guardarModificacionClienteEnArchivo(){
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_CLIENTES, false);
        for(Cliente cliente : this.getClientes()){
            arch.grabarLinea(cliente.toString());
        }

        arch.cerrar();
    }
    
    //Metodo para guardar funcionarios en archivo
    public void guardarNuevoFuncionarioEnArchivo(Funcionario funcionario){  
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_FUNCIONARIOS, true);
        arch.grabarLinea(funcionario.toString());
        
        arch.cerrar();
    }
    
    //Metodo para guardar todos los funcionarios de nuevo
    public void guardarModificacionFuncionarioEnArchivo(){
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_FUNCIONARIOS, false);
        for(Funcionario funcionario : this.getFuncionarios()){
            arch.grabarLinea(funcionario.toString());
        }

        arch.cerrar();
    }
    
    //Metodo para guardar funcionarios en archivo
    public void guardarTarifaEnArchivo(Tarifa tarifa){  
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_TARIFAS, true);
        arch.grabarLinea(tarifa.toString());
        arch.cerrar();
    }
    
    //Metodo para guardar paquete en archivo
    public void guardarPaqueteEnArchivo(Paquete paquete){
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_PAQUETES, true);
        arch.grabarLinea(paquete.aGuardar());
        arch.cerrar();
    }
    
    //Metodo para guardar todos los paquetes de nuevo
    public void guardarModificacionPaqueteEnArchivo(){
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_PAQUETES, false);
        
        for(Paquete paquete : this.getPaquetes()){
            arch.grabarLinea(paquete.aGuardar());
        }

        arch.cerrar();
    }
    
    //Metodo para guardar envios en archivo
    public void guardarEnvioEnArchivo(Envio envio){  
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_ENVIOS, true);
        arch.grabarLinea(envio.aGuardar());
        arch.cerrar();
    }
    
    //Metodo para guardar todos los envios de nuevo
    public void guardarModificacionEnvioEnArchivo(){
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_ENVIOS, false);
        
        for(Envio envio : this.getEnvios()){
            arch.grabarLinea(envio.aGuardar());
        }

        arch.cerrar();
    }
    //Metodo que se usa para modificar las tarifas segun el porcentaje
    public boolean modificarTarifas(String accion, double porcentaje){

        boolean seGuardoOk = false;
        double aux = 0; //Auxiliar para hacer las operaciones
        
        if(accion.equals("Aumentar")){ //Para aumento de tarifas
            aux = 1 + (porcentaje/100); //Sumo 1 (seria el precio actual) mas el porcentaje calculado (lo que le aumento al precio)
        }
        else if(accion.equals("Disminuir")){ //Para disminuir tarifas
            aux = 1 - (porcentaje/100); //Idem al aumento pero restando el porcentaje al precio original
        }
        
        //Saco de la lista las tarifas
        for(int i = 0; i < this.getTarifas().size(); i++){
            Tarifa tarifa = this.getTarifas().get(i);
            //Saco el precio actual, lo modifico y lo guardo
            tarifa.setPrecioCat1(Math.round(tarifa.getPrecioCat1() * aux));
            tarifa.setPrecioCat2(Math.round(tarifa.getPrecioCat2() * aux));
            tarifa.setPrecioCat3(Math.round(tarifa.getPrecioCat3() * aux));
            tarifa.setPrecioCat4(Math.round(tarifa.getPrecioCat4() * aux));
            seGuardoOk = true;
        }
        guardarTarifasModificadas(); //Llamo al metodo que las guarda en la lista de nuevo
        return seGuardoOk;
    }
        
    //Metodo para guardar las tarfias modificadas en el archivo
    public void guardarTarifasModificadas(){

        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_TARIFAS, false);

        for(int i = 0; i < this.getTarifas().size(); i++){
            Tarifa tarifa = this.getTarifas().get(i);
            String linea = tarifa.getNombre() + "#" + tarifa.getPrecioCat1() + "," + tarifa.getPrecioCat2() + ","
                         + tarifa.getPrecioCat3() + "," + tarifa.getPrecioCat4();
            arch.grabarLinea(linea);   
        }
        arch.cerrar();
    }
       
    //</editor-fold>
    
    //<editor-fold desc="Logs">
    public void generarLog(String accion){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String log = formatter.format(LocalDateTime.now()) + " - " + accion;
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_LOGS, true);
        arch.grabarLinea(log);
        arch.cerrar();
    }
    
    public void limpiarLogs(){
        ArchivoGrabacion arch = new ArchivoGrabacion(ARCHIVO_LOGS, false);
        arch.cerrar();
    }
    
    public ArrayList<String> obtenerLogs(){
        ArchivoLectura arch =  new ArchivoLectura(ARCHIVO_LOGS);
        ArrayList<String> logs = new ArrayList<String>();
        
        while(arch.hayMasLineas()){
            String linea = arch.linea();
            logs.add(linea);
        }

        arch.cerrar();
        return logs;
    }
    //</editor-fold>
}