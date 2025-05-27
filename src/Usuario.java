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

    public String enviar_mensaje() {
        return null;
    }

    public void recibir_mensaje(String mensaje) {

    }

    @Override
    public String toString() {
        return "ID: " + id + ", Alias: " + alias + ", Tipo: " + tipo;
    }
}
