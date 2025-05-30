public class Sensor {
    private int id;
    private String tipo;
    private String descripcion;


    public Sensor(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public static void setMapaGlobal(Mapa mapa) {
        Global.mapaGlobal = mapa;
    }

    public int captar_informacion(){
        if (Global.mapaGlobal == null) return -1;

        int[] direccionActual = Global.robot.getDireccion();
        int[] posicionActual = Global.robot.getPos();

        int filaObjetivo = posicionActual[0] + direccionActual[0];
        int columnaObjetivo = posicionActual[1] + direccionActual[1];



        return Global.matriz.getCelda(filaObjetivo, columnaObjetivo);
    }

    // Getters y Setters
    public int get_id() { return id; }
    public void set_id(int id) { this.id = id; }
    public String get_iipo() { return tipo; }
    public void set_tipo(String tipo) { this.tipo = tipo; }
    public String get_descripcion() { return descripcion; }
    public void set_descripcion(String descripcion) { this.descripcion = descripcion; }
}
