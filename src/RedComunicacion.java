import java.util.HashMap;

/**
 * Clase RedComunicacion - Gestor central de comunicación entre módulos del
 * robot
 * 
 * Esta clase implementa un sistema de comunicación centralizado que permite
 * a todos los módulos del robot intercambiar mensajes de forma eficiente y
 * organizada. Actúa como un hub de comunicación que mantiene un registro
 * de todos los sistemas de comunicación de los módulos y facilita el
 * enrutamiento de mensajes entre ellos.
 * 
 * La red utiliza un patrón de registro donde cada módulo debe registrar
 * su sistema de comunicación con un ID único antes de poder participar
 * en el intercambio de mensajes.
 * 
 * Patrones de diseño aplicados:
 * - Mediator: Centraliza la comunicación entre módulos
 * - Registry: Mantiene un registro de sistemas de comunicación
 * 
 * Casos de uso típicos:
 * - Comunicación entre sensores y módulos de control
 * - Envío de comandos desde módulos de control a actuadores
 * - Intercambio de datos de estado entre módulos
 * - Coordinación de acciones complejas que requieren múltiples módulos
 */
public class RedComunicacion {

    /**
     * Mapa que asocia IDs de módulos con sus sistemas de comunicación.
     * 
     * Clave: ID único del módulo (Integer)
     * Valor: Sistema de comunicación del módulo (SistemaComunicacion)
     * 
     * Este HashMap permite acceso O(1) a cualquier sistema de comunicación
     * registrado, optimizando el rendimiento del enrutamiento de mensajes.
     * 
     * Final para evitar reasignación accidental del HashMap.
     */
    private final HashMap<Integer, SistemaComunicacion> sistemas = new HashMap<>();

    /**
     * Registra un sistema de comunicación en la red.
     * 
     * Este método permite que un módulo se una a la red de comunicación
     * asociando su ID único con su sistema de comunicación. Una vez
     * registrado, el módulo podrá recibir mensajes dirigidos a su ID.
     * 
     * @param idModulo Identificador único del módulo en el sistema.
     *                 Debe corresponder a las constantes definidas en Global
     *                 (ej: Global.EXTENSION, Global.SENSOR_PROXIMIDAD_PRINCIPAL)
     * @param sistema  Sistema de comunicación del módulo a registrar.
     *                 No debe ser null.
     * 
     *                 Comportamiento:
     *                 - Si el ID ya existe, sobrescribe el registro anterior
     *                 - No realiza validaciones de duplicidad
     *                 - La operación es atómica (O(1))
     * 
     *                 Precondiciones:
     *                 - El idModulo debe ser único en el sistema (recomendado)
     *                 - El sistema no debe ser null
     * 
     *                 Postcondiciones:
     *                 - El módulo queda registrado y puede recibir mensajes
     *                 - El módulo está disponible para comunicación bidireccional
     * 
     *                 Ejemplos de uso:
     * 
     *                 <pre>
     *                 // Registrar un sensor de proximidad
     *                 SistemaComunicacion sensorComm = new SistemaComunicacion(sensorProximidad);
     *                 red.registrar_sistema(Global.SENSOR_PROXIMIDAD_PRINCIPAL, sensorComm);
     * 
     *                 // Registrar un módulo de extensión
     *                 SistemaComunicacion extensionComm = new SistemaComunicacion(extension);
     *                 red.registrar_sistema(Global.EXTENSION, extensionComm);
     *                 </pre>
     */
    public void registrar_sistema(int idModulo, SistemaComunicacion sistema) {
        sistemas.put(idModulo, sistema);
    }

    /**
     * Envía un mensaje a un módulo específico identificado por su ID.
     * 
     * Este método es el núcleo del sistema de comunicación. Busca el
     * sistema de comunicación del módulo receptor y le entrega el mensaje.
     * Si el receptor no está registrado, el mensaje se pierde y se
     * registra un error en los logs (si están habilitados).
     * 
     * @param receptor ID único del módulo que debe recibir el mensaje.
     *                 Debe corresponder a un módulo previamente registrado.
     * @param mensaje  Contenido del mensaje a enviar. El formato depende
     *                 del tipo de módulo receptor y la operación solicitada.
     * 
     *                 Comportamiento:
     *                 - Si el receptor existe: El mensaje se entrega exitosamente
     *                 vía
     *                 SistemaComunicacion.recibir_mensaje()
     *                 - Si el receptor no existe: Se muestra error en log si
     *                 Global.log = true
     *                 - No hay confirmación de entrega o procesamiento exitoso
     *                 - La búsqueda del receptor es O(1)
     * 
     *                 Formatos típicos de mensajes:
     *                 - Comandos de movimiento: "MOVER_EXTENSION:5", "ROTAR:90"
     *                 - Comandos de verificación: "VERIFICAR"
     *                 - Comandos de sonido: "EMITIR_SONIDO:ALERTA"
     *                 - Respuestas: "MOVIMIENTO_COMPLETADO",
     *                 "OBSTACULO_DETECTADO:MASCOTA"
     *                 - Estados: "ENCENDIDO", "APAGADO", "ERROR:DESCRIPCION"
     * 
     *                 Manejo de errores:
     *                 - Receptor no encontrado: Mensaje de error en log (opcional)
     *                 - Sistema de destino null: Se ignora silenciosamente
     *                 - Mensaje null o vacío: Se envía tal como está (validación en
     *                 destino)
     * 
     *                 Ejemplos de uso:
     * 
     *                 <pre>
     *                 // Enviar comando de movimiento a módulo de extensión
     *                 red.enviar_mensaje(Global.EXTENSION, "MOVER_EXTENSION:3");
     * 
     *                 // Solicitar verificación a sensor de proximidad
     *                 red.enviar_mensaje(Global.SENSOR_PROXIMIDAD_PRINCIPAL, "VERIFICAR");
     * 
     *                 // Comando de rotación
     *                 red.enviar_mensaje(Global.ROTACION, "ROTAR:90");
     * 
     *                 // Comando de sonido a altavoz
     *                 red.enviar_mensaje(Global.ALTAVOZ_PRINCIPAL, "EMITIR_SONIDO:ALERTA");
     *                 </pre>
     * 
     * @see Global#log Flag que controla si se muestran mensajes de error
     * @see SistemaComunicacion#recibir_mensaje(String) Método que procesa el
     *      mensaje en el destino
     */
    public void enviar_mensaje(int receptor, String mensaje) {
        SistemaComunicacion destino = sistemas.get(receptor);
        if (destino != null) {
            destino.recibir_mensaje(mensaje);
        } else {
            if (Global.log)
                System.out.println("Destino [" + receptor + "] no encontrado.");
        }
    }
}