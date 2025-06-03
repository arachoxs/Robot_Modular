/**
 * Clase SistemaComunicacion - Sistema de comunicación para módulos del robot
 * 
 * Esta clase gestiona la comunicación entre módulos a través de la red central.
 * Cada módulo posee una instancia que le permite enviar y recibir mensajes,
 * registrándose automáticamente en la red de comunicación al ser creado.
 * 
 * Actúa como interfaz entre el módulo y la red global, delegando el procesamiento
 * de mensajes al sistema de control del módulo propietario.
 * 
 * @author Edwin
 * @version 1.0
 * @since 2025
 */
public class SistemaComunicacion {
    
    /** Módulo que posee este sistema de comunicación */
    private Modulo modulo_propietario;
    
    /** Referencia a la red de comunicación global */
    private RedComunicacion red;

    /**
     * Constructor que inicializa el sistema y lo registra en la red.
     * 
     * @param modulo Módulo propietario de este sistema
     * @param red Red de comunicación global donde se registrará
     */
    public SistemaComunicacion(Modulo modulo, RedComunicacion red) {
        this.modulo_propietario = modulo;
        this.red = red;
        red.registrar_sistema(modulo.get_id(), this);
    }

    /**
     * Obtiene el módulo propietario de este sistema.
     * @return Módulo asociado a este sistema de comunicación
     */
    public Modulo get_modulo_propietario() {
        return modulo_propietario;
    }

    /**
     * Establece un nuevo módulo propietario.
     * @param modulo_propietario Nuevo módulo a asociar
     */
    public void set_modulo_propietario(Modulo modulo_propietario) {
        this.modulo_propietario = modulo_propietario;
    }

    /**
     * Recibe un mensaje desde la red y lo delega al sistema de control.
     * Este método es llamado por la RedComunicacion cuando llega un mensaje.
     * 
     * @param mensaje Contenido del mensaje recibido
     */
    public void recibir_mensaje(String mensaje) { // recibe un mensaje de otro modulo
        if (Global.log)
            System.out.println("Mensaje recibido en módulo " + modulo_propietario.get_referencia() + ": " + mensaje);
        this.modulo_propietario.get_sistema_control().interpretar(mensaje);
    }

    /**
     * Envía un mensaje a otro módulo a través de la red de comunicación.
     * 
     * @param receptor ID del módulo destinatario
     * @param mensaje Contenido del mensaje a enviar
     */
    public void enviar_mensaje(int receptor, String mensaje) {
        this.red.enviar_mensaje(receptor, mensaje);
    }
}
