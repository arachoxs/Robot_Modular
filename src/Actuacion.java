/**
 * Clase abstracta Actuacion - Módulos estáticos especializados en ejecución de
 * acciones
 * 
 * Esta clase base define la estructura común para todos los módulos que
 * proporcionan
 * capacidades de actuación sobre el entorno. Extiende ModuloEstatico añadiendo
 * funcionalidades específicas para el control de actuadores y ejecución de
 * acciones.
 * 
 * Los módulos de actuación incluyen altavoces y otros dispositivos que pueden
 * modificar o interactuar con el entorno basándose en las decisiones del robot.
 */
public abstract class Actuacion extends ModuloEstatico {

    /** Número de actuadores que controla este módulo */
    private int n_actuadores;

    /**
     * Constructor para módulos de actuación.
     * 
     * @param id           Identificador único del módulo
     * @param referencia   Referencia comercial del módulo
     * @param descripcion  Descripción de capacidades
     * @param largo        Dimensión largo en milímetros
     * @param ancho        Dimensión ancho en milímetros
     * @param profundidad  Dimensión profundidad en milímetros
     * @param encendido    Estado inicial de encendido
     * @param n_actuadores Número de actuadores del módulo
     */
    public Actuacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido, int n_actuadores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
        this.n_actuadores = n_actuadores;
    }

    /**
     * Obtiene el número de actuadores del módulo.
     * 
     * @return Cantidad de actuadores
     */
    public int get_n_actuadores() {
        return n_actuadores;
    }

    /**
     * Establece el número de actuadores del módulo.
     * 
     * @param n_actuadores Nueva cantidad de actuadores
     */
    public void set_n_actuadores(int n_actuadores) {
        this.n_actuadores = n_actuadores;
    }

    /**
     * Ejecuta una acción específica utilizando los actuadores del módulo.
     * Debe ser implementado por cada tipo específico de módulo de actuación.
     * 
     * @return Código de resultado de la acción ejecutada
     */
    public abstract int realizar_accion();
}
