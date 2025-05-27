import javax.swing.*;

public class Sistema_Control{
    Modulo modulo;
    public Sistema_Control(Modulo modulo){
        this.modulo = modulo;
    }

    public boolean enviar_respuesta_accion(){
        return true;
    }

    public String gestionar_solucion(){
        return "Problema solcionado";
    }

    public void interpretar(String mensaje){
        this.modulo.interpretar_mensaje(mensaje);
    }
}
