//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Modelo;

/*
INGRESO
SE PUEDE INGRESAR PAQUETES AL SISTEMA
SE TIENE QUE TENER ACCESO A LA LISTA DE CLIETNES PARA ELEGIR CUAL LO MANDA
SE DEBE INFORMAR EL PRECION EN PANTALLA Y SOLICITAR CONFIRMACION DE DATOS
DEBE HABER UNA TABLA QUE MUESTRE TODOS LOS PAQUETES CON SUS RESPECTIVOS DATOS

ENVIO
UN ENVIO LO ARMA UN FUNCIONARIO Y PUEDEEN VIAR VARIOS PAQUETES DE UNO O MAS CLIENTES
LOS ENVIOS SE NUMERAN DE FORMA AUTOMATICA Y EMPIEZAN EN 1
SE DEBE MOSTRAR EL NUMERO DE ENVIO QUE SE ESTA CREANDO
SE INGRESA LA FECHA DEL ENVIO
SE ELIGE LA ZONA DE UNA LISTA Y SE CARGA LA LISTA DE LOS PAQUETES CON ESTADO "PENDIENTE" DE LA ZONA
SE CARGAN EN OTRA LISTA LOS PAQUETES QUE SE SELECCIONAN DE LA PRIMERA
SE TIENE QUE PODER AGREGAR O QUITAR PAQUETES A LOS SECLECCIONADOS
SE TIENE QUE INFORMAR EN PANTALLA SIEMPRE LA CANTIDAD DE NUMEROS QUE SE ENVIARAN Y EL PESO TOTAL (en kilos y gramos 68kg,300g o 68.3kg)
TAMBIEN EL MONTO TOTAL EN PESOS
CUANDO SE CONFIRMA EL ENVIO, SE CAMBIA EL ESTADO A "ENVIADO"

RECEPCION
SE MUESTRAN LOS ENVIOS EN UNA LISTA ORDENADA DECRECIENTEMENTE  POR EL NUMERO DE ENVIO
LOS PAQUETES ENVIADOS TIENEN FONDO VERDE SI SE REGISTRO EL ENVIO (NO SE MODIFICA)
LOS PAQUETES AMARILLOS NO TIENEN REGISTRO DE QUE SE RECIBIO
SI ELIJO VERDE, MUESTRA LA LISTA DE PAQUETES QUE TIENE ENTREGADOS
AL ELEGIR AMARILLO, TIENEN QUE APARECER UNA LISTA CON LOS PAQUETES QUE TIENE Y SE SELECCIONA CUALES FUERON ENTREGADOS
CUANDO SE CONFIRMA LA ENTREGA, SE LE AGREGAN DATOS DE LA RECEPCION Y SE PASAN A VERDE
LOS QUE NO SE ENTREGARON SE PASAN A ESTADO "PENDIENTE" Y SIGUEN EN AMARILLO
*/
public class Paquete implements Comparable<Paquete>{
    
    private String id; //ALFANUMERICO Y SE DEBE VERIFICAR QUE SEA UNICO
    private Cliente cliente;
    private Fecha fechaIngreso; //formato dd/mm/aaaa?
    private String destinatario;
    private String direccion;
    private String destino;
    private int peso; //en gramos
    private double precio; //debe ser calculado segun la tarifa del departamto los gramos ingresados
    private String estado;
    private String nombreZona;
    
    //<editor-fold desc="Getters and setters">
    //getters
    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Fecha getFechaIngreso() {
        return fechaIngreso;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    public double getPrecio() {
        return precio;
    }

    public String getEstado() {
        return estado;
    }

    public String getNombreZona() {
        return nombreZona;
    }
    
    //Setters
    private void setId(String id) {
        this.id = id;
    }

    private void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    private void setFechaIngreso(Fecha fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    private void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    private void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    private void setDestino(String destino) {
        this.destino = destino;
    }

    private void setPeso(int peso) {
        this.peso = peso;
    }

    private void setPrecio(double precio) {
        this.precio = precio;
    }

    private void setEstado(String estado) {
        this.estado = estado;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }
    //</editor-fold>

    public Paquete(String id, Cliente cliente, Fecha fechaIngreso, String destinatario, String direccion, String destino, int peso, double precio, String nombreZona) {
        this.setId(id);
        this.setCliente(cliente);
        this.setFechaIngreso(fechaIngreso);
        this.setDestinatario(destinatario);
        this.setDireccion(direccion);
        this.setDestino(destino); 
        this.setPeso(peso);
        this.setPrecio(precio);
        this.setNombreZona(nombreZona);
        this.setEstado("PENDIENTE");
    }
    
    public Paquete(String id, Cliente cliente, Fecha fechaIngreso, String destinatario, String direccion, String destino, int peso, double precio, String nombreZona, String estado) {
        this.setId(id);
        this.setCliente(cliente);
        this.setFechaIngreso(fechaIngreso);
        this.setDestinatario(destinatario);
        this.setDireccion(direccion);
        this.setDestino(destino); 
        this.setPeso(peso);
        this.setPrecio(precio);
        this.setNombreZona(nombreZona);
        this.setEstado(estado);
    }
    
    public String aGuardar(){
        return this.id + "--" + this.getCliente().getNombre() + "--" + this.getFechaIngreso() + "--" + this.getDestinatario() + "--" + this.getDireccion() + "--" + this.getDestino() + "--" + 
                this.getPeso() + "--" + this.getPrecio() + "--" + this.getEstado() + "--" + this.getNombreZona();
    }
    
    public void aEnviado(){
        this.setEstado("ENVIADO");
    }
    
    public void aPendiente(){
        this.setEstado("PENDIENTE");
    }
    
    public void aRecibido(){
        this.setEstado("RECIBIDO");
    }
    
    @Override
    public String toString(){
        return "ID: " + this.id + ". De " + this.getCliente().getNombre() + " para " + this.getDestinatario();
    }

    @Override
    public int compareTo(Paquete o) {
        return this.id.compareTo(o.id);
    }
    
}//Class