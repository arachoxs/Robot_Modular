import java.util.ArrayList;
import java.util.List;

/**
 * Clase SensorProximidad - Módulo de percepción para detección de proximidad
 * 
 * Este módulo proporciona capacidades de detección de obstáculos y objetos
 * cercanos
 * al robot. Extiende Percepcion y coordina múltiples sensores para verificar el
 * camino antes del movimiento, actuando como sistema de seguridad para la
 * navegación.
 * 
 * Coordina con otros módulos para ejecutar maniobras de evasión cuando detecta
 * obstáculos en la trayectoria del robot.
 */
public class SensorProximidad extends Percepcion {

    /** Lista de sensores controlados por este módulo */
    private List<Sensor> sensores;

    /**
     * Constructor del módulo sensor de proximidad.
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
    public SensorProximidad(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido, int n_sensores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_sensores);
        this.sensores = new ArrayList<>();
    }

    public List<Sensor> get_sensores() {
        return sensores;
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

    /**
     * Procesa datos de proximidad y ejecuta acciones según el contexto.
     * 
     * Si detecta espacio libre (datos=0):
     * - Envía comando de movimiento al módulo de extensión
     * 
     * Si detecta obstáculo (datos!=0):
     * - Coordina con cámara según el contexto de verificación
     * - Maneja fallos de rotación en diferentes direcciones
     * 
     * @param datos       Información del objeto detectado (0=libre,
     *                    otros=obstáculo)
     * @param instruccion Contexto de la verificación
     * @return 1 si el procesamiento fue exitoso, 0 si falló
     */
    public int procesar_datos(int datos, String instruccion) {
        if (datos == -1) {
            return 0;
        }

        if (datos == 0) {
            this.get_sistema_comunicacion().enviar_mensaje(Global.EXTENSION, "MOVER FIJO");
        } else {
            if (instruccion.equals("VERIFICAR IZQUIERDA")) {
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA_PRINCIPAL, "IZQUIERDA FALLIDO");
            } else if (instruccion.equals("VERIFICAR DERECHA")) {
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA_PRINCIPAL, "DERECHA FALLIDO");
            } else if (instruccion.equals("VERIFICAR")) {
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA_PRINCIPAL, "RECONOCER OBJETO");
            } else if (instruccion.equals("VERIFICAR HELICOIDAL")) {
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA_PRINCIPAL,
                        "RECONOCER OBJETO HELICOIDAL");
            }
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
     * Interpreta mensajes de comando y ejecuta verificaciones correspondientes.
     * 
     * Comandos soportados:
     * - "OBSERVAR": Detecta y reporta objetos en frente
     * - "VERIFICAR": Verifica camino para movimiento básico
     * - "VERIFICAR IZQUIERDA": Verifica después de rotación izquierda
     * - "VERIFICAR DERECHA": Verifica después de rotación derecha
     * - "VERIFICAR HELICOIDAL": Verifica durante movimiento helicoidal
     * 
     * @param mensaje Comando a interpretar
     */
    @Override
    public void interpretar_mensaje(String mensaje) {
        int resultado_accion = 0;
        String instruccion_normalizada = mensaje.trim().toUpperCase();

        switch (instruccion_normalizada) {
            case "OBSERVAR": {
                int objeto = this.captar_informacion();
                if (objeto != 0) {
                    System.out.println("Se detectó un objeto en frente.");
                    resultado_accion = 1;
                } else {
                    System.out.println("No se detectó ningún objeto en frente.");
                    resultado_accion = 1;
                }
                break;
            }
            case "VERIFICAR":
            case "VERIFICAR IZQUIERDA":
            case "VERIFICAR DERECHA":
            case "VERIFICAR HELICOIDAL":
                resultado_accion = this.procesar_datos(captar_informacion(), instruccion_normalizada);
                break;
            default:
                System.out.println("Instrucción no reconocida: " + mensaje);
                break;
        }

        this.enviar_respuesta_accion(resultado_accion == 1);
    }

    /** Enciende el módulo sensor de proximidad y notifica el cambio de estado.
     * Actualiza el estado de encendido y emite un mensaje de encendido si el log
     * está habilitado.
     */
    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log)
            System.out.println("Sensor de Proximidad encendido");
    }

    /**
     * Apaga el módulo sensor de proximidad y notifica el cambio de estado.
     * Actualiza el estado de encendido y emite un mensaje de apagado si el log
     * está habilitado.
     */
    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log)
            System.out.println("Sensor de Proximidad apagado");
    }

    /**
     * Envía una respuesta de acción al sistema de control.
     *
     * Si la respuesta es positiva, notifica éxito; si es negativa, gestiona el
     * error.
     *
     * @param respuesta Resultado de la acción ejecutada
     */
    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if (respuesta) {
            if (Global.log)
                System.out.println("Medicion de proximidad ejecutada sin problemas.");
        } else {
            if (Global.log)
                System.out.println("Error en medicion de proximidad detectada, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }
}
