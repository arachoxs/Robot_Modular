public class Usuario {
    private int id;
    private String alias;
    private String tipo;

    //Constructor de clase con parámetros
    public Usuario(int id, String alias, String tipo) {
        this.id = id;
        this.alias = alias;
        this.tipo = tipo;
    }

    //Getters
    public int get_id() {
        return id;
    }
    public String get_alias() {
        return alias;
    }
    public String get_tipo() {
        return tipo;
    }

    //Setters
    public void set_id(int id) {
        this.id = id;
    }
    public void set_alias(String alias) {
        this.alias = alias;
    }
    public void set_tipo(String tipo) {
        this.tipo = tipo;
    }

    public void enviar_mensaje(String mensaje) {
        // Implementación para enviar mensaje
    }

    public void recibir_mensaje(String mensaje) {
        // Implementación para recibir mensaje
    }

    public void encender_robot(Robot robot) {
        robot.encender();
    }

    public void apagar_robot(Robot robot) {
        robot.apagar();
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Alias: " + alias + ", Tipo: " + tipo;
    }
}
