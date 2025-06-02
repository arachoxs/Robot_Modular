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
        if (Global.log) {
            if (n_pasos == 1) System.out.println("Moviéndose en línea recta: " + n_pasos + " metro");
            else System.out.println("Moviéndose en línea recta: " + n_pasos + " metros");
        }
        for(int i = 0; i < n_pasos; i++){
            this.get_sistema_control().enviar_respuesta_accion(Global.SENSOR_PROXIMIDAD_PRINCIPAL,"VERIFICAR"); //manda mensaje para que verifique alfrente a proximidad
        }
        return true;
    }

    // Sobrecarga para movimiento en línea recta (grados y pasos_giro en 0)
    public boolean moverse(int n_pasos) {
        return moverse(n_pasos, 0, 0);
    }

    public boolean mover_un_paso(){
        int[] direccionActual = Global.robot.get_direccion();
        int[] posicionActual = Global.robot.get_pos();

        // Calcular nueva posición
        int nuevoX = posicionActual[0] + direccionActual[0];
        int nuevoY = posicionActual[1] + direccionActual[1]*-1;

        // Mover el robot
        Global.robot.set_pos(nuevoX, nuevoY);

        Global.mapa.actualizar_posicion_robot();
        if (Global.log) Global.mapa.imprimir_mapa();
        if (Global.log) Global.pausa();

        return true;
    }

    public boolean mover_reversa(){
        Global.robot.get_modulo_id(Global.ROTACION).get_sistema_comunicacion().recibir_mensaje("ROTAR 180");
        Global.robot.get_modulo_id(Global.EXTENSION).get_sistema_comunicacion().recibir_mensaje("MOVER 1");
        Global.robot.get_modulo_id(Global.ROTACION).get_sistema_comunicacion().recibir_mensaje("ROTAR 180");
        return true;
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        boolean resultado_accion = false;

        try {
            mensaje = mensaje.trim().toUpperCase(); // Normaliza el mensaje

            if (mensaje.equals("MOVER FIJO")) {
                resultado_accion = this.mover_un_paso();

            } else if (mensaje.startsWith("MOVER")) {
                int pasos = extraer_pasos(mensaje);
                resultado_accion = this.moverse(pasos);

            } else if(mensaje.equals("REVERSA")){
                resultado_accion = this.mover_reversa();
            }
            else {
                System.out.println("Mensaje no reconocido: " + mensaje);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: número de pasos inválido.");
        } catch (Exception e) {
            System.out.println("Error general al interpretar mensaje: " + e.getMessage());
        }

        this.enviar_respuesta_accion(resultado_accion);
    }

    private int extraer_pasos(String mensaje) throws NumberFormatException {
        String numeroStr = mensaje.substring("MOVER".length()).trim();
        return Integer.parseInt(numeroStr);
    }


    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log) System.out.println("Módulo de Extensión encendido");
    }

    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log) System.out.println("Módulo de Extensión apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if(respuesta){
            if (Global.log) System.out.println("Movimiento del robot ejecutado sin problemas.");
        }
        else{
            if (Global.log) System.out.println("Error en movimiento detectado, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }
}
