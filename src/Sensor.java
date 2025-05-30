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
        int[] direccionActual = Global.robot.getDireccion();
        int[] posicionActuaal = Global.robot.getPos();
        // Lógica específica según el tipo de sensor
        if(direccionActual[0]==0){
            return matriz[posicionActuaal[0]][posicionActuaal[1]+1];
        }

        return matriz[posicionActuaal[0]+1][posicionActuaal[1]];
    }

    // Getters y Setters
    public int get_id() { return id; }
    public void set_id(int id) { this.id = id; }
    public String get_iipo() { return tipo; }
    public void set_tipo(String tipo) { this.tipo = tipo; }
    public String get_descripcion() { return descripcion; }
    public void set_descripcion(String descripcion) { this.descripcion = descripcion; }
}
