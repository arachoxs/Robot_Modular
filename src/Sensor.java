public class Sensor {
    private int id;
    private String tipo;
    private String descripcion;

    public Sensor(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Object captar_informacion(){
        // Lógica específica según el tipo de sensor
        return new Object(); // Placeholder
    }

    // Getters y Setters
    public int get_id() { return id; }
    public void set_id(int id) { this.id = id; }
    public String get_iipo() { return tipo; }
    public void set_tipo(String tipo) { this.tipo = tipo; }
    public String get_descripcion() { return descripcion; }
    public void set_descripcion(String descripcion) { this.descripcion = descripcion; }
}
