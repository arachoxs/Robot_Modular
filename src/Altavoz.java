import java.util.ArrayList;
import java.util.List;

/**
 * Clase Altavoz - Módulo de actuación para emisión de sonidos
 * 
 * Este módulo proporciona capacidades de actuación sonora al robot para
 * ahuyentar
 * mascotas del entorno. Extiende Actuacion y coordina múltiples actuadores para
 * ejecutar acciones de emisión de sonido con respuestas adaptativas según el
 * resultado.
 */
public class Altavoz extends Actuacion {

    /** Lista de actuadores controlados por este módulo */
    private final List<Actuador> actuadores;

    /**
     * Constructor del módulo altavoz.
     * 
     * @param id           Identificador único del módulo
     * @param referencia   Referencia comercial del módulo
     * @param descripcion  Descripción de capacidades
     * @param largo        Dimensión largo en milímetros
     * @param ancho        Dimensión ancho en milímetros
     * @param profundidad  Dimensión profundidad en milímetros
     * @param encendido    Estado inicial de encendido
     * @param n_actuadores Número inicial de actuadores
     */
    public Altavoz(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido, int n_actuadores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_actuadores);
        this.actuadores = new ArrayList<>();
    }

    /**
     * Agrega un actuador al módulo e incrementa el contador.
     * 
     * @param actuador Actuador a agregar al módulo
     */
    public void agregar_actuador(Actuador actuador) {
        this.actuadores.add(actuador);
        this.set_n_actuadores(this.get_n_actuadores() + 1);
    }

    public List<Actuador> get_actuadores() {
        return actuadores;
    }

    /**
     * Ejecuta la acción de emisión de sonido utilizando el actuador principal.
     * Busca el actuador principal en la lista y ejecuta su acción.
     * 
     * @return 1 si la acción fue exitosa, 0 si falló
     */
    @Override
    public int realizar_accion() {
        int retorno = 0;

        for (Actuador actuador : actuadores) {
            if (actuador.get_id() == Global.ACTUADOR_PRINCIPAL)
                retorno = actuador.realizar_accion();
        }

        if (retorno == 0) {
            return 0;
        } else {
            if (Global.log)
                System.out.println("Acción completada con éxito");
        }

        return 1;
    }

    /**
     * Enciende el módulo altavoz y notifica el cambio de estado.
     * Actualiza el estado de encendido y emite un mensaje de encendido si el log
     * está habilitado.
     */
    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log)
            System.out.println("Altavoz encendido");
    }

    /**
     * Apaga el módulo altavoz y notifica el cambio de estado.
     *
     * Actualiza el estado de encendido y emite un mensaje de apagado si el log
     * está habilitado.
     */
    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log)
            System.out.println("Altavoz apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {

    }

    /**
     * Interpreta mensajes de comando y ejecuta acciones correspondientes.
     * 
     * "EMITIR SONIDO": Emite sonido y toma acción según el resultado
     * - Si exitoso: ordena movimiento hacia adelante
     * - Si falla: ordena rotación a la izquierda
     * 
     * "EMITIR": Ejecuta emisión simple sin acciones adicionales
     * 
     * @param mensaje Comando a interpretar
     */
    @Override
    public void interpretar_mensaje(String mensaje) {
        if (mensaje.equals("EMITIR SONIDO")) {
            if (this.realizar_accion() == 1)
                this.get_sistema_control().enviar_respuesta_accion(Global.EXTENSION, "MOVER FIJO");
            else if (realizar_accion() == 0) {
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION, "ROTACION IZQUIERDA");
            }
        } else if (mensaje.equals("EMITIR")) {
            this.realizar_accion();
        }
    }
}
