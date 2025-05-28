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
    public int getId() {
        return id;
    }
    public String getAlias() {
        return alias;
    }
    public String getTipo() {
        return tipo;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void enviarMensaje(String mensaje) {
        // Implementación para enviar mensaje
    }

    public void recibirMensaje(String mensaje) {
        // Implementación para recibir mensaje
    }

    public void encenderRobot(Robot robot) {
        robot.encender();
    }

    public void apagarRobot(Robot robot) {
        robot.apagar();
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Alias: " + alias + ", Tipo: " + tipo;
    }
}
