public class Sensor {
    private int id;
    private String tipo;
    private String descripcion;


    public Sensor(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int captar_informacion(){
        if (Global.mapa == null) return -1;

        int[] direccionActual = Global.robot.getDireccion();
        int[] posicionActual = Global.robot.getPos();

        int columnaObjetivo = posicionActual[0] + direccionActual[0];
        int filaObjetivo = posicionActual[1] + direccionActual[1]*-1;

        System.out.println("Posicion actual: " + posicionActual[0] + " " + posicionActual[1] + "");
        System.out.println("Informacion captada en fila: " + filaObjetivo + " columna: " + columnaObjetivo + "");
        System.out.println("Informacion captada: " + Global.mapa.get_celda(filaObjetivo, columnaObjetivo) + "");
        return Global.mapa.get_celda(filaObjetivo, columnaObjetivo);
    }

    // Getters y Setters
    public int get_id() { return id; }
    public void set_id(int id) { this.id = id; }
    public String get_iipo() { return tipo; }
    public void set_tipo(String tipo) { this.tipo = tipo; }
    public String get_descripcion() { return descripcion; }
    public void set_descripcion(String descripcion) { this.descripcion = descripcion; }
}
