public class Extension extends ModuloDinamico {

    // Constructor
    public Extension(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
                     boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    // Methods
    @Override
    public boolean moverse(int n_pasos, int grados , int pasos_giro) {
        // Lógica específica para movimiento de extensión (línea recta)
        System.out.println("Moviéndose en línea recta: " + n_pasos + " metros");
        for(int i=0; i<n_pasos; i++){
            this.get_sistema_control().enviar_respuesta_accion(5,"VERIFICAR"); //manda mensaje para que verifique alfrente a proximidad
        }

        return true;
    }

    // Sobrecarga para movimiento en línea recta (grados y pasos_giro en 0)
    public boolean moverse(int n_pasos) {
        return moverse(n_pasos, 0, 0);
    }

    public boolean mover_un_paso(){
        int[] direccionActual = Global.robot.getDireccion();
        int[] posicionActual = Global.robot.getPos();

        // Calcular nueva posición
        int nuevoX = posicionActual[0] + direccionActual[0];
        int nuevoY = posicionActual[1] + direccionActual[1]*-1;

        // Mover el robot
        Global.robot.setPos(nuevoX, nuevoY);

        Global.mapa.actualizar_posicion_robot();
        Global.mapa.imprimir_mapa();

        return true;
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        boolean resultadoAccion = false;

        try {
            mensaje = mensaje.trim().toUpperCase(); // Normaliza el mensaje

            if (mensaje.equals("MOVER FIJO")) {
                resultadoAccion = this.mover_un_paso();

            } else if (mensaje.startsWith("MOVER")) {
                int pasos = extraerPasos(mensaje);
                resultadoAccion = this.moverse(pasos);

            } else {
                System.out.println("Mensaje no reconocido: " + mensaje);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: número de pasos inválido.");
        } catch (Exception e) {
            System.out.println("Error general al interpretar mensaje: " + e.getMessage());
        }

        this.enviar_respuesta_accion(resultadoAccion);
    }

    private int extraerPasos(String mensaje) throws NumberFormatException {
        String numeroStr = mensaje.substring("MOVER".length()).trim();
        return Integer.parseInt(numeroStr);
    }


    @Override
    public void encender() {
        System.out.println("Módulo de Extensión encendido");
    }

    @Override
    public void apagar() {
        System.out.println("Módulo de Extensión apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if(!respuesta){
            System.out.println("Movimiento del robot ejecutado sin problemas.");
        }
        else{
            System.out.println("Error en movimiento detectado, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }
}
