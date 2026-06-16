//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package Utils;

import java.util.ArrayList;
import java.util.Iterator;
import Modelo.Cliente;
import Modelo.Funcionario;
import Modelo.Paquete;

public class Utils {
    
    public static Cliente encontrarCliente(ArrayList<Cliente> clientes, String nombre){
        
        Cliente clienteEncontrado = null;
        Iterator<Cliente> it = clientes.iterator();
        while(it.hasNext() && clienteEncontrado == null){
            Cliente c = it.next();
            if(c.getNombre().equalsIgnoreCase(nombre)){
                clienteEncontrado = c;
            }
        }
        return clienteEncontrado;
    }
    
    public static Paquete encontrarPaquete(ArrayList<Paquete> paquetes, String id){
        Paquete paqueteEncontrado = null;
        Iterator<Paquete> it = paquetes.iterator();
        while(it.hasNext() && paqueteEncontrado == null){
            Paquete c = it.next();
            if(c.getId().equalsIgnoreCase(id)){
                paqueteEncontrado = c;
            }
        }
        return paqueteEncontrado;
    }
    
    public static Funcionario encontrarFuncionario(ArrayList<Funcionario> funcionarios, String nombre){
        
        Funcionario funcionarioEncontrado = null;
        Iterator<Funcionario> it = funcionarios.iterator();
        while(it.hasNext() && funcionarioEncontrado == null){
            Funcionario c = it.next();
            if(c.getNombre().equalsIgnoreCase(nombre)){
                funcionarioEncontrado = c;
            }
        }
        return funcionarioEncontrado;
    }
    
    /**
     * Method to check if a string is parseable to int without risk of an exception
     * @param str String to convert
     * @return if is parseable or not
     */
    public static boolean stringIsParseableToInt(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    
    public static boolean arrayContieneInt(int[] arr, int numero){
        boolean contiene = false;
        for(int i = 0; i < arr.length && !contiene; i++){
            contiene = arr[i] == numero;
        }
        return contiene;
    }
}