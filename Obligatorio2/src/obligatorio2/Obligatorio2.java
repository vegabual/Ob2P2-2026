//Obligatorio 2 - Programación 2 - Primer Semestre 2026
//(212712) Veronica Busiello - (354398) Lourdes Ayala

package obligatorio2;

import Interfaz.VentanaBienvenida;

public class Obligatorio2 {

    public static void main(String[] args) {
        
        //Inicio el programa en este main para configurar solo una vez que las ventanas se vean como windows
        //Inicio de codigo de chatgpt --> Para que todas las ventanas se vean como windows
        //Tambien aparece en el main de las clases de ventanas cuando se crean (lo elimino)
        try{            
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
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