//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package obligatorio2;

import Interfaz.VentanaBienvenida;
//import java.awt.*; //VENTANAS EMERGENTES USAN ESTAS DOS
//import java.awt.event.*;
public class Obligatorio2 {

    public static void main(String[] args) {
        
        //Inicio el programa en este main para configurar solo una vez que las ventnas se vean como windows y para la ventna del F1
        //Inicio de codigo de chatgpt --> Para que todas las ventanas se vean como windows
        //Tambien aparece en el main de las clases de ventanas cuando se crean (lo elimino)
        try{            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        //Pone 4 tipo de excepciones
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex){
            //Usa Logger para no imprimir en consola
            java.util.logging.Logger.getLogger(Obligatorio2.class.getName()).log(java.util.logging.Level.SEVERE,
            "No se pudo aplicar Windows Look & Feel", ex);
        }
        
        //Para llamar a la ventana de bienvenida que luego llama a la ventna principal
        javax.swing.SwingUtilities.invokeLater(() ->{
            new VentanaBienvenida().setVisible(true);
        });
        
    }//Main
    
}//Class

/* EN CASO DE NECESITAR VENTANA EMERGENTE

import Interfaz.DatosAlumno;

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher(){
            @Override
            public boolean dispatchKeyEvent(KeyEvent e){
                if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F1){ //Si se apreta F1 se abre la ventana de datos
                    new NOMBREVENTANA().setVisible(true);
                }
                return false; //Deja que otros eventos se sigan procesando
            }
        });//Fin codigo de chatgpt  

*/