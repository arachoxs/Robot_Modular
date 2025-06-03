/**
 * Clase Global - Centro de configuración y estado del sistema de robot modular
 * 
 * Esta clase contiene todas las variables globales, constantes y
 * configuraciones
 * del sistema de simulación de robot modular. Actúa como un punto central de
 * acceso a los recursos compartidos entre todos los módulos del sistema.
 */
public class Global {

    // ========================================
    // CONSTANTES DE COLORES ANSI PARA CONSOLA
    // ========================================

    /** Código ANSI para resetear el color de texto a normal */
    public static final String ANSI_RESET = "\u001B[0m";

    /** Código ANSI para texto en color rojo */
    public static final String ANSI_RED = "\u001B[31m";

    /** Código ANSI para texto en color verde */
    public static final String ANSI_GREEN = "\u001B[32m";

    /** Código ANSI para texto en color amarillo */
    public static final String ANSI_YELLOW = "\u001B[33m";

    /** Código ANSI para texto en color azul */
    public static final String ANSI_BLUE = "\u001B[34m";

    /** Código ANSI para texto en color negro */
    public static final String ANSI_BLACK = "\u001B[30m";

    // ========================================
    // OBJETOS GLOBALES DEL SISTEMA
    // ========================================

    /** Red de comunicación global que gestiona mensajes entre módulos */
    public static RedComunicacion red = new RedComunicacion();

    /** Posición inicial del robot en el mapa [fila, columna] */
    public static int[] posicion_inicial = { 1, 1 };

    /** Instancia global del robot principal del sistema */
    public static Robot robot = new Robot("123", "Phi", "Robotcini Banini", posicion_inicial);

    /** Instancia global del mapa donde se mueve el robot */
    public static Mapa mapa = new Mapa();

    // ========================================
    // VARIABLES DE ESTADO DEL SISTEMA
    // ========================================

    /**
     * Flag que controla si se muestran mensajes de depuración en consola
     * true = mostrar logs, false = modo silencioso
     */
    public static boolean log = false;

    /**
     * Flag que indica si el sistema ha sido inicializado completamente
     * Se usa para evitar agregar sensores/actuadores duplicados
     */
    public static boolean inicializado = false;

    /**
     * Flag que indica si el movimiento helicoidal ha fallado
     * Se usa para detener secuencias de movimiento helicoidal
     */
    public static boolean helicoidal_fallido = false;

    // ========================================
    // CONSTANTES DE IDENTIFICADORES DE MÓDULOS
    // ========================================

    /** ID único para el módulo de extensión (movimiento lineal) */
    public static final int EXTENSION = 1;

    /** ID único para el módulo de rotación (movimiento angular) */
    public static final int ROTACION = 2;

    /** ID único para el módulo helicoidal (movimiento combinado) */
    public static final int HELICOIDAL = 3;

    /** ID único para la cámara principal del robot */
    public static final int CAMARA_PRINCIPAL = 4;

    /** ID único para el sensor de proximidad principal */
    public static final int SENSOR_PROXIMIDAD_PRINCIPAL = 5;

    /** ID único para el altavoz principal */
    public static final int ALTAVOZ_PRINCIPAL = 6;

    /** ID único para el sensor principal (usado por cámara y proximidad) */
    public static final int SENSOR_PRINCIPAL = 7;

    /** ID único para el actuador principal (usado por altavoz) */
    public static final int ACTUADOR_PRINCIPAL = 8;

    // ========================================
    // MÉTODOS UTILITARIOS
    // ========================================

    /**
     * Pausa la ejecución del programa hasta que el usuario presione ENTER.
     * Útil para depuración y visualización paso a paso del movimiento del robot.
     * 
     * Uso típico:
     * - Después de cada movimiento del robot para ver el estado del mapa
     * - Para permitir al usuario revisar logs antes de continuar
     * - En modo debug para controlar el flujo de ejecución
     */
    public static void pausa() {
        System.out.println("Presiona ENTER para continuar...");
        new java.util.Scanner(System.in).nextLine();
    }
}
