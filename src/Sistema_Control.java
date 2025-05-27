import javax.swing.*;

public class Sistema_Control{

    public boolean enviar_respuesta_accion(){
        return true;
    }

    public String gestionar_solucion(){
        return "Problema solcionado";
    }

    public void interpretar_mensaje(String mensaje){
        System.out.println(mensaje);
    }
}
