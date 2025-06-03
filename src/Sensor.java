/**
 * Clase Sensor - Componente de captura de información del entorno
 * 
 * Esta clase representa un sensor individual que puede ser integrado en módulos
 * de percepción. Proporciona capacidades básicas de detección del entorno
 * analizando la celda inmediatamente frente al robot en su dirección actual.
 */
public class Sensor {

    /** Identificador único del sensor */
    private int id;

    /** Tipo o categoría del sensor */
    private String tipo;

    /** Descripción detallada de las capacidades del sensor */
    private String descripcion;

    /**
     * Constructor del sensor.
     * 
     * @param id          Identificador único del sensor
     * @param tipo        Tipo o categoría del sensor
     * @param descripcion Descripción de capacidades
     */
    public Sensor(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public String get_tipo() {
        return tipo;
    }

    public void set_tipo(String tipo) {
        this.tipo = tipo;
    }

    public String get_descripcion() {
        return descripcion;
    }

    public void set_descripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Captura información de la celda inmediatamente frente al robot.
     * Analiza la posición objetivo basándose en la dirección actual del robot
     * y retorna el contenido de esa celda del mapa.
     * 
     * @return Valor de la celda objetivo (AIRE=0, OBSTACULO=1, MASCOTA=2, ROBOT=3)
     *         o -1 si hay error
     */
    public int captar_informacion() {
        if (Global.mapa == null)
            return -1;

        int[] direccion_actual = Global.robot.get_direccion();
        int[] posicion_actual = Global.robot.get_pos();

        int columna_objetivo = posicion_actual[0] + direccion_actual[0];
        int fila_objetivo = posicion_actual[1] + direccion_actual[1] * -1;

        if (Global.log)
            System.out.println("Información captada en fila " + fila_objetivo + ", columna " + columna_objetivo + "");

        int informacion = Global.mapa.get_celda(fila_objetivo, columna_objetivo);

        if (Global.log)
            System.out.println("Información captada: " + informacion + "");
        return informacion;
    }
}
