import java.util.ArrayList;
import java.util.List;

/**
 * Clase Camara - Módulo de percepción visual del robot
 * 
 * Este módulo proporciona capacidades de detección e identificación de objetos
 * en el entorno. Extiende Percepcion y coordina múltiples sensores para
 * capturar
 * y procesar información visual, ejecutando respuestas adaptativas según los
 * objetos detectados.
 */
public class Camara extends Percepcion {

    /** Lista de sensores controlados por este módulo */
    private List<Sensor> sensores;

    /**
     * Constructor del módulo cámara.
     * 
     * @param id          Identificador único del módulo
     * @param referencia  Referencia comercial del módulo
     * @param descripcion Descripción de capacidades
     * @param largo       Dimensión largo en milímetros
     * @param ancho       Dimensión ancho en milímetros
     * @param profundidad Dimensión profundidad en milímetros
     * @param encendido   Estado inicial de encendido
     * @param n_sensores  Número inicial de sensores
     */
    public Camara(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido, int n_sensores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_sensores);
        this.sensores = new ArrayList<>();
    }

    /**
     * Agrega un sensor al módulo e incrementa el contador.
     * 
     * @param sensor Sensor a agregar al módulo
     */
    public void agregar_sensor(Sensor sensor) {
        this.sensores.add(sensor);
        this.set_n_sensores(this.get_n_sensores() + 1);
    }

    public List<Sensor> get_sensores() {
        return sensores;
    }

    /**
     * Interpreta mensajes de comando y ejecuta acciones correspondientes.
     * 
     * Comandos soportados:
     * - "OBSERVAR OBJETO": Identifica y reporta objetos detectados
     * - "RECONOCER OBJETO": Procesa objeto y ejecuta acción correspondiente
     * - "IZQUIERDA FALLIDO": Maneja fallo de rotación izquierda
     * - "DERECHA FALLIDO": Maneja fallo de rotación derecha
     * - "RECONOCER OBJETO HELICOIDAL": Procesa objeto durante movimiento helicoidal
     * 
     * @param mensaje Comando a interpretar
     */
    @Override
    public void interpretar_mensaje(String mensaje) {
        int resultado_accion = 0;
        String instruccion_normalizada = mensaje.trim().toUpperCase();

        switch (instruccion_normalizada) {
            case "OBSERVAR OBJETO": {
                int objeto = this.captar_informacion();
                switch (objeto) {
                    case 0: {
                        System.out.println("Objeto detectado: Aire");
                        resultado_accion = 1;
                        break;
                    }
                    case 1: {
                        System.out.println("Objeto detectado: Obstáculo");
                        resultado_accion = 1;
                        break;
                    }
                    case 2: {
                        System.out.println("Objeto detectado: Mascota");
                        resultado_accion = 1;
                        break;
                    }
                    case 3: {
                        System.out.println("Objeto detectado: Robot");
                        resultado_accion = 1;
                        break;
                    }
                    default: {
                        System.out.println("Objeto no reconocido: " + objeto);
                        resultado_accion = 0;
                        break;
                    }
                }
                break;
            }
            case "RECONOCER OBJETO":
            case "IZQUIERDA FALLIDO":
            case "DERECHA FALLIDO":
            case "RECONOCER OBJETO HELICOIDAL":
                resultado_accion = this.procesar_datos(captar_informacion(), instruccion_normalizada);
                break;
            default:
                System.out.println("Mensaje no reconocido: " + mensaje);
                break;
        }

        this.enviar_respuesta_accion(resultado_accion == 1);
    }

    /**
     * Procesa datos capturados y ejecuta acciones según el objeto detectado y la
     * instrucción.
     * 
     * Para obstáculos (datos=1):
     * - Ejecuta rotaciones o movimientos de evasión según el contexto
     * 
     * Para mascotas (datos=2):
     * - Activa el altavoz para ahuyentarlas
     * 
     * @param datos       Información del objeto detectado
     * @param instruccion Contexto de la detección
     * @return 1 si el procesamiento fue exitoso, 0 si falló
     */
    @Override
    public int procesar_datos(int datos, String instruccion) {
        if (datos == -1) {
            return 0;
        }
        if (datos == 1) {
            if (instruccion.equals("RECONOCER OBJETO")) {
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION, "ROTACION IZQUIERDA");
            } else if (instruccion.equals("IZQUIERDA FALLIDO")) {
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION, "ROTACION IZQUIERDA FALLIDA");
            } else if (instruccion.equals("DERECHA FALLIDO")) {
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION, "ROTACION IZQUIERDA FIJA");
                this.get_sistema_control().enviar_respuesta_accion(Global.EXTENSION, "REVERSA");
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION, "ROTACION IZQUIERDA");
            } else if (instruccion.equals("RECONOCER OBJETO HELICOIDAL")) {
                this.get_sistema_control().enviar_respuesta_accion(Global.HELICOIDAL, "MOVIMIENTO HELICOIDAL FALLIDO");
            }
        } else {
            this.get_sistema_control().enviar_respuesta_accion(Global.ALTAVOZ_PRINCIPAL, "EMITIR SONIDO");
        }

        return 1;
    }

    /**
     * Captura información del entorno utilizando el sensor principal.
     * 
     * @return Valor de la celda detectada o -1 si no hay sensor principal
     */
    @Override
    public int captar_informacion() {
        for (Sensor sensor : sensores) {
            if (sensor.get_id() == Global.SENSOR_PRINCIPAL) {
                return sensor.captar_informacion();
            }
        }
        return -1;
    }

    /**
     * Enciende el módulo cámara y notifica el cambio de estado.
     * Actualiza el estado de encendido y emite un mensaje de encendido si el log
     * está habilitado.
     */
    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log)
            System.out.println("Cámara encendida");
    }

    /**
     * Apaga el módulo cámara y notifica el cambio de estado.
     *
     * Actualiza el estado de encendido y emite un mensaje de apagado si el log
     * está habilitado.
     */
    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log)
            System.out.println("Cámara apagada");
    }

    /**
     * Envía una respuesta de acción al sistema de control.
     *
     * Este método se invoca para notificar el resultado de una acción ejecutada por
     * el módulo.
     *
     * @param respuesta Resultado de la acción (true=éxito, false=fallo)
     */
    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if (respuesta) {
            if (Global.log)
                System.out.println("Acción de la cámara ejecutada sin problemas.");
        } else {
            if (Global.log)
                System.out.println("Fallo en la acción de la cámara.");
        }
    }
}
