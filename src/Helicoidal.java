import java.util.List;

public class Helicoidal extends ModuloDinamico {

    //Constructor
    public Helicoidal(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    //Methods
    @Override
    public boolean moverse(int n_pasos, int grados, int pasos_giro) {
        // Lógica específica para movimiento helicoidal (gira y se traslada)
        if (Global.log == true) System.out.println("Movimiento helicoidal: " + n_pasos + " unidades");
        return true;
    }

    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log) System.out.println("Módulo Helicoidal encendido");
    }

    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log) System.out.println("Módulo Helicoidal apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if(respuesta){
            System.out.println("Movimiento Helicoidal del robot ejecutada sin problemas.");
        }
        else{
            System.out.println("Error en movimiento helicoidal detectada, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }

    @Override
    public void interpretar_mensaje(String mensaje) {

    }
}
