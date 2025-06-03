/**
 * Clase Usuario - Representa a un operador del sistema de robot modular
 * 
 * Esta clase modela a los usuarios que interactúan con el sistema de robot
 * modular.
 * Proporciona funcionalidades para la comunicación con el robot, control de
 * encendido/apagado
 * y gestión de la identidad del usuario dentro del sistema.
 * 
 * Los usuarios pueden enviar comandos al robot a través del sistema de
 * comunicación
 * y recibir retroalimentación sobre el estado de las operaciones.
 * 
 */
public class Usuario {

    // ========================================
    // ATRIBUTOS DE IDENTIDAD DEL USUARIO
    // ========================================

    /** Identificador único del usuario en el sistema */
    private int id;

    /** Nombre o alias del usuario para identificación humana */
    private String alias;

    /**
     * Tipo de usuario que define sus privilegios y roles
     * Ejemplos: "OPERADOR", "ADMINISTRADOR", "TECNICO", "INVITADO"
     */
    private String tipo;

    // ========================================
    // CONSTRUCTOR
    // ========================================

    /**
     * Constructor principal que inicializa un usuario con todos sus atributos.
     * Ejemplo de uso:
     * 
     * <pre>
     * Usuario operador = new Usuario(1, "Juan", "OPERADOR");
     * Usuario admin = new Usuario(2, "Maria", "ADMINISTRADOR");
     * </pre>
     */
    public Usuario(int id, String alias, String tipo) {
        this.id = id;
        this.alias = alias;
        this.tipo = tipo;
    }

    // ========================================
    // MÉTODOS GETTER (ACCESORES)
    // ========================================

    /**
     * Obtiene el identificador único del usuario.
     */
    public int get_id() {
        return id;
    }

    /**
     * Obtiene el alias o nombre del usuario.
     */
    public String get_alias() {
        return alias;
    }

    /**
     * Obtiene el tipo de usuario que define sus privilegios.
     */
    public String get_tipo() {
        return tipo;
    }

    // ========================================
    // MÉTODOS SETTER (MUTADORES)
    // ========================================

    /**
     * Establece un nuevo ID para el usuario.
     */
    public void set_id(int id) {
        this.id = id;
    }

    /**
     * Establece un nuevo alias para el usuario.
     */
    public void set_alias(String alias) {
        this.alias = alias;
    }

    /**
     * Establece un nuevo tipo de usuario.
     */
    public void set_tipo(String tipo) {
        this.tipo = tipo;
    }

    // ========================================
    // MÉTODOS DE COMUNICACIÓN
    // ========================================

    /**
     * Envía un mensaje al sistema de robot modular.
     * 
     * Este método permite al usuario comunicarse con el robot enviando comandos
     * o solicitudes a través del sistema de comunicación global.
     * 
     * Casos de uso típicos:
     * - Comandos de movimiento: "MOVER_EXTENSION:5"
     * - Comandos de rotación: "ROTAR:90"
     * - Solicitudes de estado: "OBTENER_POSICION"
     * 
     * Ejemplo:
     * 
     * <pre>
     * usuario.enviar_mensaje("MOVER_EXTENSION:3");
     * usuario.enviar_mensaje("ROTAR:90");
     * </pre>
     */
    public void enviar_mensaje(String mensaje) {
        // TODO: Implementar envío a través de Global.red
        // Global.red.enviar_mensaje(this, Global.robot, mensaje);
    }

    /**
     * Recibe un mensaje del sistema de robot modular.
     * 
     * Este método procesa las respuestas y notificaciones enviadas por el robot
     * o el sistema de comunicación, como confirmaciones de comandos ejecutados
     * o reportes de errores.
     * 
     * @param mensaje Respuesta o notificación recibida del sistema
     * 
     *                Ejemplos de mensajes recibidos:
     *                - "MOVIMIENTO_COMPLETADO"
     *                - "OBSTACULO_DETECTADO:MASCOTA"
     *                - "ERROR:MODULO_NO_DISPONIBLE"
     * 
     *                Ejemplo:
     * 
     *                <pre>
     *                // El sistema llama automáticamente este método
     *                usuario.recibir_mensaje("MOVIMIENTO_COMPLETADO");
     *                </pre>
     */
    public void recibir_mensaje(String mensaje) {
        // TODO: Implementar procesamiento de mensajes recibidos
        // System.out.println("[" + alias + "] Mensaje recibido: " + mensaje);
    }

    // ========================================
    // MÉTODOS DE CONTROL DEL ROBOT
    // ========================================

    /**
     * Enciende el robot especificado.
     * 
     * Este método activa todos los sistemas del robot, incluyendo módulos
     * de movimiento, sensores y actuadores. Solo usuarios con privilegios
     * apropiados deberían poder ejecutar esta acción.
     * 
     * Precondiciones:
     * - El robot debe estar apagado
     * - El usuario debe tener permisos suficientes
     * 
     * Postcondiciones:
     * - El robot queda en estado encendido
     * - Todos los módulos se inicializan
     * 
     * Ejemplo:
     * 
     * <pre>
     * Usuario admin = new Usuario(1, "Admin", "ADMINISTRADOR");
     * admin.encender_robot(Global.robot);
     * </pre>
     */
    public void encender_robot(Robot robot) {
        robot.encender(); // TODO: Verificar si método existe en Robot
    }

    /**
     * Apaga el robot especificado.
     * 
     * Este método desactiva todos los sistemas del robot de forma segura,
     * asegurando que se completen las operaciones en curso antes del apagado.
     * 
     * @param robot Instancia del robot a apagar
     * 
     *              Precondiciones:
     *              - El robot debe estar encendido
     *              - No debe haber operaciones críticas en curso
     * 
     *              Postcondiciones:
     *              - El robot queda en estado apagado
     *              - Todos los módulos se desactivan
     * 
     *              Ejemplo:
     * 
     *              <pre>
     *              usuario.apagar_robot(Global.robot);
     *              </pre>
     */
    public void apagar_robot(Robot robot) {
        robot.apagar(); // TODO: Verificar si método existe en Robot
    }

    // ========================================
    // MÉTODOS SOBRESCRITOS
    // ========================================

    /**
     * Genera una representación en texto del usuario.
     * 
     * Proporciona una descripción legible del usuario incluyendo
     * su ID, alias y tipo. Útil para logging, debugging y
     * visualización en interfaces de usuario.
     * 
     * @return String con la información del usuario formateada
     * 
     *         Formato de salida: "ID: [id], Alias: [alias], Tipo: [tipo]"
     * 
     *         Ejemplo:
     * 
     *         <pre>
     *         Usuario user = new Usuario(1, "Juan", "OPERADOR");
     *         System.out.println(user.toString());
     *         // Salida: "ID: 1, Alias: Juan, Tipo: OPERADOR"
     *         </pre>
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Alias: " + alias + ", Tipo: " + tipo;
    }
}
