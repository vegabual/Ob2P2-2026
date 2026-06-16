//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Modelo;

public class Tarifa {
    private String nombre;
    private double precioCat1;
    private double precioCat2;
    private double precioCat3;
    private double precioCat4;
    private String[] departamentos;
    
    //<editor-fold desc="Getters and setters">
    public String getNombre() {
        return nombre;
    }

    public double getPrecioCat1() {
        return precioCat1;
    }

    public double getPrecioCat2() {
        return precioCat2;
    }

    public double getPrecioCat3() {
        return precioCat3;
    }

    public double getPrecioCat4() {
        return precioCat4;
    }

    public String[] getDepartamentos() {
        return departamentos;
    }

    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioCat1(double precioCat1) {
        this.precioCat1 = precioCat1;
    }

    public void setPrecioCat2(double precioCat2) {
        this.precioCat2 = precioCat2;
    }

    public void setPrecioCat3(double precioCat3) {
        this.precioCat3 = precioCat3;
    }

    public void setPrecioCat4(double precioCat4) {
        this.precioCat4 = precioCat4;
    }
    
    private void setDepartamentos(String[] departamentos) {
        this.departamentos = departamentos;
    }

    //</editor-fold>
    
    public Tarifa(String nombre, double precioCat1,double precioCat2, double precioCat3,double precioCat4, String[] departamentos) {
        this.setNombre(nombre);
        this.setPrecioCat1(precioCat1);
        this.setPrecioCat2(precioCat2);
        this.setPrecioCat3(precioCat3);
        this.setPrecioCat4(precioCat4);
        this.setDepartamentos(departamentos);
    }
    
    //Para usar con el metodo cargarTarifas de la clase ManagerDatos
    public Tarifa(String nombre, double precioCat1,double precioCat2, double precioCat3,double precioCat4){
        this.setNombre(nombre);
        this.setPrecioCat1(precioCat1);
        this.setPrecioCat2(precioCat2);
        this.setPrecioCat3(precioCat3);
        this.setPrecioCat4(precioCat4);
    }
    
    /**
     * Sube los precios un porcentaje dado
     * @param porcentaje Porcentaje que suben los precios
     */
    public void subirPrecios(double porcentaje){
        //CALCULAR NUEVO PRECIO PARA CADA UNO DE LAS CATEGORIAS Y GUARDAR EL DATO EN ARCHIVO (y en memoria)
    }
    
    public void bajarPrecios(double porcentaje){
        //CALCULAR NUEVO PRECIO PARA CADA UNO DE LAS CATEGORIAS Y GUARDAR EL DATO EN ARCHIVO (y en memoria)
    }
    
    /**
     * Dado un departamento, determina si pertenece a la zona de la tarifa
     * @param departamento Departamento a definir
     * @return Si pertenece a la zona de la tarifa
     */
    public boolean deptoPerteneceAZona(String departamento){
        boolean pertenece = false;
        for(int i=0; i < this.getDepartamentos().length && !pertenece;i++){
            pertenece = this.getDepartamentos()[i].equalsIgnoreCase(departamento);
        }
        return pertenece;
    }
    
    /**
     * Dado un peso en gramos, devuelve el precio del envio
     * @param peso Peso en gramos
     * @return Precio del envio del paquete
     */
    public double precioEnvio(int peso){
        double precio = 0;
        if(peso < 1000){
            precio = this.getPrecioCat1();
        } else if(peso < 5000){
            precio = this.getPrecioCat2();
        } else if(peso < 10000){
            precio = this.getPrecioCat3();
        } else{
            precio = this.getPrecioCat4();
        }
        return precio;
    }
    
    @Override
    public String toString(){
        return (this.getNombre() + "#" + this.getPrecioCat1() + "," + this.getPrecioCat2() + "," + this.getPrecioCat3() + "," + this.getPrecioCat4());
    }
}//Class