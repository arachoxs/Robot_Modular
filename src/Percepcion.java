/**
 * Clase abstracta Percepcion - Módulos estáticos especializados en captura de
 * información
 * 
 * Esta clase base define la estructura común para todos los módulos que
 * proporcionan
 * capacidades de percepción del entorno. Extiende ModuloEstatico añadiendo
 * funcionalidades
 * específicas para el manejo de sensores y procesamiento de datos sensoriales.
 * 
 * Los módulos de percepción incluyen cámaras y sensores de proximidad que
 * capturan
 * información del entorno y la procesan para la toma de decisiones del robot.
 */
public abstract class Percepcion extends ModuloEstatico {

    /** Número de sensores que controla este módulo de percepción */
    private int n_sensores;

    /**
     * Constructor para módulos de percepción.
     * 
     * @param id          Identificador único del módulo
     * @param referencia  Referencia comercial del módulo
     * @param descripcion Descripción de capacidades
     * @param largo       Dimensión largo en milímetros
     * @param ancho       Dimensión ancho en milímetros
     * @param profundidad Dimensión profundidad en milímetros
     * @param encendido   Estado inicial de encendido
     * @param n_sensores  Número de sensores del módulo
     */
    public Percepcion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido, int n_sensores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
        this.n_sensores = n_sensores;
    }

    /**
     * Obtiene el número de sensores del módulo.
     * 
     * @return Cantidad de sensores
     */
    public int get_n_sensores() {
        return n_sensores;
    }

    /**
     * Establece el número de sensores del módulo.
     * 
     * @param n_sensores Nueva cantidad de sensores
     */
    public void set_n_sensores(int n_sensores) {
        this.n_sensores = n_sensores;
    }

    /**
     * Procesa los datos capturados por los sensores según la instrucción dada.
     * Debe ser implementado por cada tipo específico de módulo de percepción.
     * 
     * @param datos       Información cruda capturada por los sensores
     * @param instruccion Tipo de procesamiento a realizar
     * @return Resultado procesado de los datos
     */
    public abstract int procesar_datos(int datos, String instruccion);

    /**
     * Captura información del entorno utilizando los sensores del módulo.
     * Debe ser implementado por cada tipo específico de módulo de percepción.
     * 
     * @return Datos capturados del entorno
     */
    public abstract int captar_informacion();
}
