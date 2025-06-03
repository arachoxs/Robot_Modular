/**
 * Clase SistemaControl - Sistema de control interno para módulos del robot
 * 
 * Esta clase gestiona el control y coordinación de acciones dentro de cada
 * módulo.
 * Actúa como intermediario entre el sistema de comunicación y la lógica
 * específica
 * del módulo, facilitando la interpretación de mensajes y la gestión de
 * respuestas.
 */
public class SistemaControl {

    /** Referencia al módulo que posee este sistema de control */
    private Modulo modulo_propietario;

    /**
     * Constructor que asocia el sistema de control con un módulo específico.
     * 
     * @param modulo Módulo propietario de este sistema de control
     */
    public SistemaControl(Modulo modulo) {
        this.modulo_propietario = modulo;
    }

    /**
     * Obtiene el módulo propietario de este sistema de control.
     * 
     * @return Módulo asociado a este sistema
     */
    public Modulo get_modulo_propietario() {
        return modulo_propietario;
    }

    /**
     * Establece un nuevo módulo propietario para este sistema de control.
     * 
     * @param modulo_propietario Nuevo módulo a asociar
     */
    public void set_modulo_propietario(Modulo modulo_propietario) {
        this.modulo_propietario = modulo_propietario;
    }

    /**
     * Envía una respuesta de acción a otro módulo a través del sistema de
     * comunicación.
     * 
     * @param id      Identificador del módulo destinatario
     * @param mensaje Contenido del mensaje de respuesta
     * @return true si el envío fue exitoso
     */
    public boolean enviar_respuesta_accion(int id, String mensaje) {
        if (Global.log)
            System.out.println("Sistema de control del módulo " + modulo_propietario.get_referencia()
                    + " enviando acción a seguir");
        this.modulo_propietario.get_sistema_comunicacion().enviar_mensaje(id, mensaje);
        return true;
    }

    /**
     * Gestiona la búsqueda y aplicación de soluciones cuando se detectan problemas.
     * 
     * @return String descriptivo de la solución aplicada
     */
    public String gestionar_solucion() {
        if (Global.log)
            System.out.println("Gestionando solución en módulo " + modulo_propietario.get_referencia());
        return "Solución gestionada";
    }

    /**
     * Interpreta un mensaje recibido y lo delega al módulo propietario para su
     * procesamiento.
     * Actúa como puente entre la comunicación externa y la lógica interna del
     * módulo.
     * 
     * @param mensaje Mensaje a interpretar y procesar
     */
    public void interpretar(String mensaje) {
        if (Global.log)
            System.out
                    .println("Interpretando mensaje en módulo " + modulo_propietario.get_referencia() + ": " + mensaje);
        this.modulo_propietario.interpretar_mensaje(mensaje);
    }
}
