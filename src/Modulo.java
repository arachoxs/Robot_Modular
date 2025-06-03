/**
 * Clase abstracta Modulo - Clase base para todos los módulos del robot modular
 * 
 * Esta clase define la estructura común y el comportamiento básico que deben
 * implementar todos los módulos del robot, tanto dinámicos (movimiento) como
 * estáticos (sensores y actuadores). Proporciona la funcionalidad esencial
 * para la comunicación, control y gestión de estado de cada módulo.
 * 
 * Cada módulo tiene dimensiones físicas, identificación única, sistemas de
 * control y comunicación, y puede ser encendido/apagado independientemente.
 * 
 */
public abstract class Modulo {

    // ========================================
    // ATRIBUTOS DE IDENTIDAD Y ESPECIFICACIONES
    // ========================================

    /** Identificador único del módulo en el sistema */
    private int id;

    /** Referencia comercial o modelo del módulo */
    private String referencia;

    /** Descripción detallada de las capacidades del módulo */
    private String descripcion;

    // ========================================
    // ATRIBUTOS FÍSICOS (DIMENSIONES EN MM)
    // ========================================

    /** Dimensión largo del módulo en milímetros */
    private int largo;

    /** Dimensión ancho del módulo en milímetros */
    private int ancho;

    /** Dimensión profundidad del módulo en milímetros */
    private int profundidad;

    // ========================================
    // ATRIBUTOS DE ESTADO Y SISTEMAS
    // ========================================

    /** Estado de encendido del módulo */
    private boolean encendido;

    /** Sistema de control interno del módulo */
    private SistemaControl sistema_control;

    /** Sistema de comunicación para intercambio de mensajes */
    private SistemaComunicacion sistema_comunicacion;

    /**
     * Constructor base para todos los módulos del robot.
     * 
     * Inicializa todos los atributos básicos del módulo e instancia
     * automáticamente los sistemas de control y comunicación necesarios
     * para su funcionamiento dentro del robot modular.
     * 
     * @param id          Identificador único del módulo
     * @param referencia  Referencia comercial del módulo
     * @param descripcion Descripción de capacidades
     * @param largo       Dimensión largo en milímetros
     * @param ancho       Dimensión ancho en milímetros
     * @param profundidad Dimensión profundidad en milímetros
     * @param encendido   Estado inicial de encendido
     */
    public Modulo(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido) {
        this.id = id;
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.largo = largo;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.encendido = encendido;
        this.sistema_control = new SistemaControl(this);
        this.sistema_comunicacion = new SistemaComunicacion(this, Global.red);
    }

    // ========================================
    // MÉTODOS GETTER
    // ========================================

    public int get_id() {
        return this.id;
    }

    public String get_referencia() {
        return this.referencia;
    }

    public String get_descripcion() {
        return this.descripcion;
    }

    public int get_largo() {
        return this.largo;
    }

    public int get_ancho() {
        return this.ancho;
    }

    public int get_profundidad() {
        return this.profundidad;
    }

    public boolean get_encendido() {
        return this.encendido;
    }

    /**
     * Obtiene la posición actual del robot desde el contexto global.
     * 
     * @return Array con [fila, columna] de la posición del robot
     */
    public int[] get_pos_robot() {
        return Global.robot.get_pos();
    }

    /**
     * Obtiene la dirección actual del robot desde el contexto global.
     * 
     * @return Array con [deltaFila, deltaColumna] de la dirección
     */
    public int[] get_direccion_robot() {
        return Global.robot.get_direccion();
    }

    public SistemaComunicacion get_sistema_comunicacion() {
        return this.sistema_comunicacion;
    }

    public SistemaControl get_sistema_control() {
        return this.sistema_control;
    }

    // ========================================
    // MÉTODOS SETTER
    // ========================================

    public void set_id(int id) {
        this.id = id;
    }

    public void set_referencia(String referencia) {
        this.referencia = referencia;
    }

    public void set_descripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void set_largo(int largo) {
        this.largo = largo;
    }

    public void set_ancho(int ancho) {
        this.ancho = ancho;
    }

    public void set_profundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public void set_encendido(boolean encendido) {
        this.encendido = encendido;
    }

    public void set_sistema_comunicacion(SistemaComunicacion sistema_comunicacion) {
        this.sistema_comunicacion = sistema_comunicacion;
    }

    public void set_sistema_control(SistemaControl sistema_control) {
        this.sistema_control = sistema_control;
    }

    // ========================================
    // MÉTODOS ABSTRACTOS
    // ========================================

    /**
     * Enciende el módulo y activa todos sus sistemas.
     * Debe ser implementado por cada tipo específico de módulo.
     */
    public abstract void encender();

    /**
     * Apaga el módulo y desactiva todos sus sistemas.
     * Debe ser implementado por cada tipo específico de módulo.
     */
    public abstract void apagar();

    /**
     * Envía una respuesta de acción al sistema de control.
     * 
     * @param respuesta Resultado booleano de la acción ejecutada
     */
    public abstract void enviar_respuesta_accion(boolean respuesta);

    /**
     * Interpreta y procesa mensajes recibidos del sistema de comunicación.
     * 
     * @param mensaje Contenido del mensaje a procesar
     */
    public abstract void interpretar_mensaje(String mensaje);

    // ========================================
    // MÉTODOS CONCRETOS
    // ========================================

    /**
     * Recibe información sobre una acción en curso.
     * Utilizado para notificar al módulo sobre operaciones que lo afectan.
     * 
     * @param info Descripción de la acción recibida
     */
    public void recibir_info_accion(String info) {
        if (Global.log)
            System.out.println("Módulo " + id + " recibió info de acción: " + info);
    }

    /**
     * Gestiona la búsqueda de soluciones eficientes para problemas detectados.
     * Método genérico que puede ser sobrescrito por módulos específicos.
     */
    public void gestionar_solucion() {
        if (Global.log)
            System.out.println("Buscando la solucion mas eficiente para el problema");
    }
}