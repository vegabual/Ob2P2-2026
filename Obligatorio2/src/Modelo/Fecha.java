//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Modelo;

import Excepciones.ErrorFechaNoValidaException;
import static Utils.Utils.arrayContieneInt;

public class Fecha {
    private final int[] MESES_CON_30_DIAS = {4,6,9,11}; //meses: abril, junio, setiembre, noviembre
    private int dia;
    private int mes;
    private int anio;
    
    //<editor-fold desc="Getters y setters">
    public int getDia() {
        return dia;
    }

    private void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    private void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    private void setAnio(int anio) {
        if(anio < 50){
            anio += 2000; //Asumo que se ingresaron los ultimos 2 digitos de un año del 2000
        } else if(anio < 100){
            anio += 1900; // Asumo que se ingresaron los ultimos 2 digitos de un año del 1900
        }
        this.anio = anio;
    }
    //</editor-fold>
    
    public Fecha(int dia, int mes, int anio) throws ErrorFechaNoValidaException{
        if(!esValida(dia, mes, anio)){
            throw new ErrorFechaNoValidaException("La fecha ingresada no es valida");
        } else {
            this.setDia(dia);
            this.setMes(mes);
            this.setAnio(anio);
        }
    }

    private boolean esValida(int dia, int mes, int anio){
        boolean esValida = dia < 32 && dia > 0 && mes > 0 && mes < 13 && anioValido(anio); //Entra dentro de los parametros normales de fecha
        esValida &= !(dia == 31 && arrayContieneInt(MESES_CON_30_DIAS, mes)); //No es 31 de un mes que tiene 30 dias
        esValida &= !(dia > 28 && mes == 2); //No es 29-31 de febrero
        return esValida;
    }
    
    public boolean anioValido(int anio){
        boolean es2Cifras = anio > 0 && anio < 100;
        boolean es4CifrasValido = anio > 1000 && anio < 3000;
        return es2Cifras || es4CifrasValido;
    }
    
    public static boolean anioValido(int anio, int maxAnio){
        boolean es2Cifras = anio > 0 && anio < 100;
        boolean es4CifrasValido = anio > 1000 && anio < maxAnio;
        return es2Cifras || es4CifrasValido;
    }
    /**
     * Intenta generar una fecha en base a 3 enteros, dia, mes y año. Si la  fecha no es valida, devuelve si es valida o no
     * @param dia Dia del mes la fecha
     * @param mes Mes de la fecha
     * @param año Año de la fecha
     * @param fecha Donde se alojara en caso de ser una fecha valida
     * @return Si es valida
     */
    public static boolean esParseable(int dia, int mes, int anio){
        boolean parseable = false;
        try { //Si es parseable, se devuelve el valor en fecha
            new Fecha(dia, mes, anio);
            parseable = true;
        } catch(ErrorFechaNoValidaException ex){
            parseable = false;
        } finally {
            return parseable;
        }
    }
    
    public static Fecha parseFecha(String texto) throws ErrorFechaNoValidaException{
        String[] partes = texto.split("/");
        Fecha fecha = null;
        if(partes.length == 3){
            fecha = new Fecha(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
        }
        return fecha;
    }
    
    public static Fecha parseFecha(int dia, int mes, int anio) throws ErrorFechaNoValidaException{
        return new Fecha(dia, mes, anio);
    }
    
    @Override
    public String toString(){
        return this.dia + "/" + this.mes + "/" + this.anio;
    }
    
}
