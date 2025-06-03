import java.util.List;

/**
 * Clase Robot - Representa un robot modular con capacidades de movimiento y percepción
 * 
 * Esta clase es el núcleo del sistema de robot modular. Gestiona todos los módulos
 * (dinámicos y estáticos), mantiene el estado del robot (posición, dirección, encendido)
 * y proporciona una interfaz unificada para interactuar con el robot.
 * 
 * El robot puede tener módulos de movimiento (Extension, Rotacion, Helicoidal),
 * módulos de percepción (SensorProximidad, Camara) y módulos de actuación (Altavoz).
 * 
 * @author Edwin
 * @version 1.0
 * @since 2025
 */
public class Robot {
    
    // ========================================
    // ATRIBUTOS DE IDENTIDAD
    // ========================================
    
    /** Número de serie único del robot */
    private String serie;
    
    /** Nombre o alias del robot para identificación */
    private String alias;
    
    /** Descripción detallada del robot y sus capacidades */
    private String descripcion;
    
    // ========================================
    // ATRIBUTOS DE CONFIGURACIÓN MODULAR
    // ========================================
    
    /** Lista de todos los módulos instalados en el robot */
    private List<Modulo> modulos;
    
    // ========================================
    // ATRIBUTOS DE ESTADO FÍSICO
    // ========================================
    
    /** 
     * Posición actual del robot en el mapa [fila, columna]
     * Público para acceso directo desde otros módulos del sistema
     */
    public int[] pos;
    
    /** 
     * Dirección hacia donde está mirando el robot [deltaFila, deltaColumna]
     * Valores posibles: {0,1}=derecha, {1,0}=abajo, {0,-1}=izquierda, {-1,0}=arriba
     * Público para acceso directo desde otros módulos del sistema
     */
    public int[] direccion;
    
    /** Estado de encendido del robot */
    private boolean encendido;

    // ========================================
    // CONSTRUCTOR
    // ========================================
    
    /**
     * Constructor principal que inicializa un robot con posición específica.
     * 
     * @param Serie Número de serie único del robot
     * @param Alias Nombre identificativo del robot
     * @param Descripcion Descripción de las capacidades del robot
     * @param pos Array con la posición inicial [fila, columna]
     * 
     * Postcondiciones:
     * - El robot se inicializa apagado
     * - No tiene módulos instalados por defecto
     * - Mira hacia el este (dirección {1,0})
     * - La posición se establece según el parámetro pos
     * 
     * Ejemplo:
     * <pre>
     * int[] posInicial = {5, 10};
     * Robot robot = new Robot("R001", "Explorer", "Robot explorador", posInicial);
     * </pre>
     */
    public Robot(String Serie, String Alias, String Descripcion, int[] pos) {
        this.serie = Serie;
        this.alias = Alias;
        this.descripcion = Descripcion;
        this.modulos = new java.util.ArrayList<>();
        this.encendido = false;

        this.pos = new int[2];
        this.direccion = new int[2];

        // Inicializar posicion y direccion
        this.pos[0] = pos[0]; // fila
        this.pos[1] = pos[1]; // columna

        // Se inicializa mirando hacia el este
        this.direccion[0] = 1; // x
        this.direccion[1] = 0; // y

        // no se agregan módulos por defecto - todos se agregarán desde agregar_modulo
    }

    // ========================================
    // MÉTODOS GETTER Y SETTER
    // ========================================
    
    /**
     * Obtiene el número de serie del robot.
     * @return Número de serie único
     */
    public String get_serie() {
        return serie;
    }

    /**
     * Establece un nuevo número de serie para el robot.
     * @param serie Nuevo número de serie
     */
    public void set_serie(String serie) {
        this.serie = serie;
    }

    /**
     * Obtiene el alias del robot.
     * @return Nombre o alias del robot
     */
    public String get_alias() {
        return alias;
    }

    /**
     * Establece un nuevo alias para el robot.
     * @param alias Nuevo nombre o alias
     */
    public void set_alias(String alias) {
        this.alias = alias;
    }

    /**
     * Obtiene la descripción del robot.
     * @return Descripción de capacidades del robot
     */
    public String get_descripcion() {
        return descripcion;
    }

    /**
     * Establece una nueva descripción para el robot.
     * @param descripcion Nueva descripción
     */
    public void set_descripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la lista de módulos instalados.
     * @return Lista de todos los módulos del robot
     */
    public List<Modulo> get_modulos() {
        return modulos;
    }

    /**
     * Establece una nueva lista de módulos.
     * @param modulos Nueva lista de módulos
     */
    public void set_modulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    /**
     * Obtiene una copia de la posición actual del robot.
     * @return Array con [fila, columna] de la posición actual
     */
    public int[] get_pos() {
        return pos.clone();
    }

    /**
     * Establece una nueva posición para el robot.
     * @param fila Nueva fila en el mapa
     * @param columna Nueva columna en el mapa
     */
    public void set_pos(int fila, int columna) {
        this.pos[0] = fila;
        this.pos[1] = columna;
    }

    /**
     * Obtiene una copia de la dirección actual del robot.
     * @return Array con [deltaFila, deltaColumna] de la dirección
     */
    public int[] get_direccion() {
        return direccion.clone();
    }

    /**
     * Establece una nueva dirección para el robot.
     * @param direccion Array con nueva dirección [deltaFila, deltaColumna]
     */
    public void set_direccion(int[] direccion) {
        if (direccion != null && direccion.length == 2) {
            this.direccion[0] = direccion[0];
            this.direccion[1] = direccion[1];
        }
    }

    /**
     * Verifica si el robot está encendido.
     * @return true si está encendido, false si está apagado
     */
    public boolean get_encendido() {
        return encendido;
    }

    /**
     * Establece el estado de encendido del robot.
     * @param encendido true para encender, false para apagar
     */
    public void set_encendido(boolean encendido) {
        this.encendido = encendido;
    }

    // ========================================
    // MÉTODOS DE GESTIÓN DE MÓDULOS
    // ========================================
    
    /**
     * Agrega un módulo genérico al robot.
     * 
     * @param modulo Módulo a agregar al robot
     * 
     * Postcondiciones:
     * - El módulo se añade a la lista de módulos
     * - El módulo queda disponible para uso
     */
    public void agregar_modulo(Modulo modulo) {
        this.modulos.add(modulo);
    }

    /**
     * Agrega un módulo de extensión (movimiento lineal) al robot.
     * 
     * @param id Identificador único del módulo
     * @param referencia Referencia comercial del módulo
     * @param descripcion Descripción de las capacidades
     * @param largo Dimensión largo en mm
     * @param ancho Dimensión ancho en mm
     * @param profundidad Dimensión profundidad en mm
     * @param encendido Estado inicial del módulo
     * @param n_motores Número de motores del módulo
     */
    public void agregar_extension(int id, String referencia, String descripcion, 
                                 int largo, int ancho, int profundidad, 
                                 boolean encendido, int n_motores) {
        Extension extension = new Extension(id, referencia, descripcion, 
                                          largo, ancho, profundidad, encendido, n_motores);
        this.agregar_modulo(extension);
    }

    /**
     * Agrega un módulo de rotación (movimiento angular) al robot.
     * 
     * @param id Identificador único del módulo
     * @param referencia Referencia comercial del módulo
     * @param descripcion Descripción de las capacidades
     * @param largo Dimensión largo en mm
     * @param ancho Dimensión ancho en mm
     * @param profundidad Dimensión profundidad en mm
     * @param encendido Estado inicial del módulo
     * @param n_motores Número de motores del módulo
     */
    public void agregar_rotacion(int id, String referencia, String descripcion, 
                                int largo, int ancho, int profundidad, 
                                boolean encendido, int n_motores) {
        Rotacion rotacion = new Rotacion(id, referencia, descripcion, 
                                       largo, ancho, profundidad, encendido, n_motores);
        this.agregar_modulo(rotacion);
    }

    /**
     * Agrega un módulo helicoidal (movimiento combinado) al robot.
     * 
     * @param id Identificador único del módulo
     * @param referencia Referencia comercial del módulo
     * @param descripcion Descripción de las capacidades
     * @param largo Dimensión largo en mm
     * @param ancho Dimensión ancho en mm
     * @param profundidad Dimensión profundidad en mm
     * @param encendido Estado inicial del módulo
     * @param n_motores Número de motores del módulo
     */
    public void agregar_helicoidal(int id, String referencia, String descripcion, 
                                  int largo, int ancho, int profundidad, 
                                  boolean encendido, int n_motores) {
        Helicoidal helicoidal = new Helicoidal(id, referencia, descripcion, 
                                             largo, ancho, profundidad, encendido, n_motores);
        this.agregar_modulo(helicoidal);
    }

    /**
     * Agrega un sensor de proximidad al robot.
     * 
     * Automáticamente incluye un sensor genérico si es la primera inicialización
     * del sistema (controlado por Global.inicializado).
     * 
     * @param id Identificador único del módulo
     * @param referencia Referencia comercial del módulo
     * @param descripcion Descripción de las capacidades
     * @param largo Dimensión largo en mm
     * @param ancho Dimensión ancho en mm
     * @param profundidad Dimensión profundidad en mm
     * @param encendido Estado inicial del módulo
     * @param n_sensores Número de sensores del módulo
     */
    public void agregar_sensor_proximidad(int id, String referencia, String descripcion, 
                                         int largo, int ancho, int profundidad, 
                                         boolean encendido, int n_sensores) {
        SensorProximidad sensor_prox = new SensorProximidad(id, referencia, descripcion, 
                                                           largo, ancho, profundidad, 
                                                           encendido, n_sensores);
        if (!Global.inicializado) {
            Sensor sensor = new Sensor(Global.SENSOR_PRINCIPAL, 
                                     "Sensor de propósito general", 
                                     "Detecta obstáculos cercanos y captura imágenes");
            sensor_prox.agregar_sensor(sensor);
        }
        this.agregar_modulo(sensor_prox);
    }

    /**
     * Agrega una cámara al robot.
     * 
     * Automáticamente incluye un sensor genérico si es la primera inicialización
     * del sistema (controlado por Global.inicializado).
     * 
     * @param id Identificador único del módulo
     * @param referencia Referencia comercial del módulo
     * @param descripcion Descripción de las capacidades
     * @param largo Dimensión largo en mm
     * @param ancho Dimensión ancho en mm
     * @param profundidad Dimensión profundidad en mm
     * @param encendido Estado inicial del módulo
     * @param n_sensores Número de sensores del módulo
     */
    public void agregar_camara(int id, String referencia, String descripcion, 
                              int largo, int ancho, int profundidad, 
                              boolean encendido, int n_sensores) {
        Camara cam = new Camara(id, referencia, descripcion, 
                               largo, ancho, profundidad, encendido, n_sensores);
        if (!Global.inicializado) {
            Sensor sensor = new Sensor(Global.SENSOR_PRINCIPAL, 
                                     "Sensor de propósito general", 
                                     "Detecta obstáculos cercanos y captura imágenes");
            cam.agregar_sensor(sensor);
        }
        this.agregar_modulo(cam);
    }

    /**
     * Agrega un altavoz al robot.
     * 
     * Automáticamente incluye un actuador genérico si es la primera inicialización
     * del sistema (controlado por Global.inicializado).
     * 
     * @param id Identificador único del módulo
     * @param referencia Referencia comercial del módulo
     * @param descripcion Descripción de las capacidades
     * @param largo Dimensión largo en mm
     * @param ancho Dimensión ancho en mm
     * @param profundidad Dimensión profundidad en mm
     * @param encendido Estado inicial del módulo
     * @param n_actuadores Número de actuadores del módulo
     */
    public void agregar_altavoz(int id, String referencia, String descripcion, 
                               int largo, int ancho, int profundidad, 
                               boolean encendido, int n_actuadores) {
        Altavoz altavoz = new Altavoz(id, referencia, descripcion, 
                                    largo, ancho, profundidad, encendido, n_actuadores);
        if (!Global.inicializado) {
            Actuador actuador = new Actuador(Global.ACTUADOR_PRINCIPAL, 
                                            "Sonido", "Emite alerta");
            altavoz.agregar_actuador(actuador);
        }
        this.agregar_modulo(altavoz);
    }

    // ========================================
    // MÉTODOS DE BÚSQUEDA Y VERIFICACIÓN
    // ========================================
    
    /**
     * Busca un módulo específico por su ID.
     * 
     * @param id Identificador del módulo a buscar
     * @return El módulo encontrado o null si no existe
     * 
     * Ejemplo:
     * <pre>
     * Modulo extension = robot.get_modulo_id(Global.EXTENSION);
     * if (extension != null) {
     *     // Usar el módulo
     * }
     * </pre>
     */
    public Modulo get_modulo_id(int id) {
        for (Modulo modulo : modulos) {
            if (modulo.get_id() == id) {
                return modulo;
            }
        }
        return null; // Si no se encuentra
    }

    /**
     * Verifica si existe un módulo con el ID especificado.
     * 
     * @param id Identificador del módulo a verificar
     * @return true si el módulo existe, false en caso contrario
     */
    public boolean existe_modulo_id(int id) {
        for (Modulo modulo : modulos) {
            if (modulo.get_id() == id) {
                return true;
            }
        }
        return false; // Si no se encuentra
    }

    /**
     * Verifica si existe un sensor específico en algún módulo de percepción.
     * 
     * Busca en módulos SensorProximidad y Camara para encontrar un sensor
     * con el ID especificado.
     * 
     * @param id Identificador del sensor a verificar
     * @return true si el sensor existe, false en caso contrario
     */
    public boolean existe_sensor(int id) {
        for (Modulo modulo : modulos) {
            if (modulo instanceof SensorProximidad) {
                SensorProximidad sensorProx = (SensorProximidad) modulo;
                for (Sensor sensor : sensorProx.get_sensores()) {
                    if (sensor.get_id() == id) {
                        return true;
                    }
                }
            } else if (modulo instanceof Camara) {
                Camara camara = (Camara) modulo;
                for (Sensor sensor : camara.get_sensores()) {
                    if (sensor.get_id() == id) {
                        return true;
                    }
                }
            }
        }
        return false; // Si no se encuentra
    }

    /**
     * Verifica si existe un actuador específico en algún módulo de actuación.
     * 
     * Busca en módulos Altavoz para encontrar un actuador con el ID especificado.
     * 
     * @param id Identificador del actuador a verificar
     * @return true si el actuador existe, false en caso contrario
     */
    public boolean existe_actuador(int id) {
        for (Modulo modulo : modulos) {
            if (modulo instanceof Altavoz) {
                Altavoz altavoz = (Altavoz) modulo;
                for (Actuador actuador : altavoz.get_actuadores()) {
                    if (actuador.get_id() == id) {
                        return true;
                    }
                }
            }
        }
        return false; // Si no se encuentra
    }

    // ========================================
    // MÉTODOS DE CONTROL DE ESTADO
    // ========================================
    
    /**
     * Enciende el robot y todos sus módulos.
     * 
     * Sigue una secuencia específica de encendido:
     * 1. Marca el robot como encendido
     * 2. Enciende primero los módulos dinámicos (movimiento)
     * 3. Luego enciende los módulos estáticos (sensores y actuadores)
     * 
     * Postcondiciones:
     * - El robot queda en estado encendido
     * - Todos los módulos quedan activos y listos para operar
     * - Se registra el evento en logs si Global.log está activo
     */
    public void encender() {
        this.encendido = true;
        if (Global.log) System.out.println("Robot " + alias + " encendido");

        // Encender primero los módulos dinámicos
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloDinamico) {
                modulo.encender();
            }
        }

        // Luego encender los módulos estáticos
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloEstatico) {
                modulo.encender();
            }
        }
    }

    /**
     * Apaga el robot y todos sus módulos de forma segura.
     * 
     * Sigue una secuencia específica de apagado:
     * 1. Marca el robot como apagado
     * 2. Apaga primero los módulos dinámicos (para detener movimientos)
     * 3. Luego apaga los módulos estáticos (sensores y actuadores)
     * 
     * Postcondiciones:
     * - El robot queda en estado apagado
     * - Todos los módulos quedan inactivos
     * - Se registra el evento en logs si Global.log está activo
     * 
     * Precondiciones:
     * - Se recomienda que no haya operaciones críticas en curso
     */
    public void apagar() {
        this.encendido = false;
        if (Global.log) System.out.println("Robot " + alias + " apagado");

        // Apagar primero los módulos dinámicos
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloDinamico) {
                modulo.apagar();
            }
        }

        // Luego apagar los módulos estáticos
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloEstatico) {
                modulo.apagar();
            }
        }
    }
}
