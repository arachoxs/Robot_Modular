/**
 * Clase Extension - Módulo dinámico para movimiento lineal del robot
 * 
 * Este módulo proporciona capacidades de movimiento en línea recta al robot.
 * Extiende ModuloDinamico e implementa movimientos hacia adelante, hacia atrás
 * y coordina con sensores de proximidad para navegación segura.
 */
public class Extension extends ModuloDinamico {

    /**
     * Constructor del módulo de extensión.
     * 
     * @param id          Identificador único del módulo
     * @param referencia  Referencia comercial del módulo
     * @param descripcion Descripción de capacidades
     * @param largo       Dimensión largo en milímetros
     * @param ancho       Dimensión ancho en milímetros
     * @param profundidad Dimensión profundidad en milímetros
     * @param encendido   Estado inicial de encendido
     * @param n_motores   Número de motores del módulo
     */
    public Extension(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    /**
     * Realiza movimiento lineal del robot con verificación de obstáculos.
     * Solicita verificación al sensor de proximidad antes de cada paso.
     * 
     * @param n_pasos    Número de pasos a mover
     * @param grados     No utilizado en extensión (siempre 0)
     * @param pasos_giro No utilizado en extensión (siempre 0)
     * @return true si el movimiento fue exitoso
     */
    @Override
    public boolean moverse(int n_pasos, int grados, int pasos_giro) {
        if (Global.log) {
            if (n_pasos == 1)
                System.out.println("Moviéndose en línea recta: " + n_pasos + " metro");
            else
                System.out.println("Moviéndose en línea recta: " + n_pasos + " metros");
        }
        for (int i = 0; i < n_pasos; i++) {
            this.get_sistema_control().enviar_respuesta_accion(Global.SENSOR_PROXIMIDAD_PRINCIPAL, "VERIFICAR");
        }
        return true;
    }

    /**
     * Sobrecarga para movimiento lineal simple.
     * 
     * @param n_pasos Número de pasos a mover
     * @return true si el movimiento fue exitoso
     */
    public boolean moverse(int n_pasos) {
        return moverse(n_pasos, 0, 0);
    }

    /**
     * Mueve el robot exactamente un paso en la dirección actual.
     * Actualiza la posición en el mapa y muestra el resultado si está en modo log.
     * 
     * @return true si el paso fue exitoso
     */
    public boolean mover_un_paso() {
        int[] direccionActual = Global.robot.get_direccion();
        int[] posicionActual = Global.robot.get_pos();

        int nuevoX = posicionActual[0] + direccionActual[0];
        int nuevoY = posicionActual[1] + direccionActual[1] * -1;

        Global.robot.set_pos(nuevoX, nuevoY);
        Global.mapa.actualizar_posicion_robot();
        if (Global.log)
            Global.mapa.imprimir_mapa();
        if (Global.log)
            Global.pausa();

        return true;
    }

    /**
     * Ejecuta movimiento en reversa coordinando rotación y extensión.
     * Gira 180°, mueve un paso y vuelve a girar 180° para mantener orientación.
     * 
     * @return true si el movimiento en reversa fue exitoso
     */
    public boolean mover_reversa() {
        Global.robot.get_modulo_id(Global.ROTACION).get_sistema_comunicacion().recibir_mensaje("ROTAR 180");
        Global.robot.get_modulo_id(Global.EXTENSION).get_sistema_comunicacion().recibir_mensaje("MOVER 1");
        Global.robot.get_modulo_id(Global.ROTACION).get_sistema_comunicacion().recibir_mensaje("ROTAR 180");
        return true;
    }

    /**
     * Interpreta y procesa mensajes de movimiento.
     * Soporta comandos: "MOVER FIJO", "MOVER [número]", "REVERSA"
     * 
     * @param mensaje Comando de movimiento a interpretar
     */
    @Override
    public void interpretar_mensaje(String mensaje) {
        boolean resultado_accion = false;

        try {
            mensaje = mensaje.trim().toUpperCase();

            if (mensaje.equals("MOVER FIJO")) {
                resultado_accion = this.mover_un_paso();
            } else if (mensaje.startsWith("MOVER")) {
                int pasos = extraer_pasos(mensaje);
                resultado_accion = this.moverse(pasos);
            } else if (mensaje.equals("REVERSA")) {
                resultado_accion = this.mover_reversa();
            } else {
                System.out.println("Mensaje no reconocido: " + mensaje);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: número de pasos inválido.");
        } catch (Exception e) {
            System.out.println("Error general al interpretar mensaje: " + e.getMessage());
        }

        this.enviar_respuesta_accion(resultado_accion);
    }

    /**
     * Extrae el número de pasos del comando MOVER.
     * 
     * @param mensaje Mensaje que contiene el comando
     * @return Número de pasos extraído
     * @throws NumberFormatException Si el número no es válido
     */
    private int extraer_pasos(String mensaje) throws NumberFormatException {
        String numeroStr = mensaje.substring("MOVER".length()).trim();
        return Integer.parseInt(numeroStr);
    }

    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log)
            System.out.println("Módulo de Extensión encendido");
    }

    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log)
            System.out.println("Módulo de Extensión apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if (respuesta) {
            if (Global.log)
                System.out.println("Movimiento del robot ejecutado sin problemas.");
        } else {
            if (Global.log)
                System.out.println("Error en movimiento detectado, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }
}
