public class Actuador {
    private int id;
    private String tipo;
    private String descripcion;
    private static Mapa mapaGlobal; // Referencia al mapa global

    public Actuador(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int realizar_accion() {
        int[] direccionActual = Global.robot.get_direccion();
        int[] posicionActuaal = Global.robot.get_pos();
        //if(!Global.mapaGlobal.emitir_sonido(direccionActual[0],direccionActual[1])){
        //    return 0;
        //}
        return 1; // Código de éxito
    }

    // Getters
    public int get_id() { return id; }
    public String get_tipo() { return tipo; }
    public String get_descripcion() { return descripcion; }

    // Setters
    public void set_id(int id) { this.id = id; }
    public void set_tipo(String tipo) { this.tipo = tipo; }
    public void set_descripcion(String descripcion) { this.descripcion = descripcion; }
}
