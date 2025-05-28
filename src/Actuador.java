public class Actuador {
    private int id;
    private String tipo;
    private String descripcion;

    public Actuador(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int realizar_accion() {
        // Lógica específica según el tipo de actuador
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
