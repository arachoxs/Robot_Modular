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

        int[] direccion_actual = Global.robot.get_direccion();
        int[] posicion_actual = Global.robot.get_pos();

        int columna_objetivo = posicion_actual[0] + direccion_actual[0];
        int fila_objetivo = posicion_actual[1] + direccion_actual[1]*-1;

        //System.out.println("Posicion actual: " + posicion_actual[0] + " " + posicion_actual[1] + "");
        if (Global.log) System.out.println("Información captada en fila " + fila_objetivo + ", columna " + columna_objetivo + "");

        int informacion = Global.mapa.get_celda(fila_objetivo, columna_objetivo);

        if (Global.log) System.out.println("Información captada: " + informacion + "");
        return informacion;
    }

    // Getters y Setters
    public int get_id() { return id; }
    public void set_id(int id) { this.id = id; }
    public String get_tipo() { return tipo; }
    public void set_tipo(String tipo) { this.tipo = tipo; }
    public String get_descripcion() { return descripcion; }
    public void set_descripcion(String descripcion) { this.descripcion = descripcion; }
}
