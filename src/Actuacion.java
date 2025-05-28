public abstract class Actuacion extends ModuloEstatico {
    private int n_actuadores;

    public Actuacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_actuadores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
        this.n_actuadores = n_actuadores;
    }

    // Getters y Setters
    public int getN_actuadores() { return n_actuadores; }
    public void setN_actuadores(int n_actuadores) { this.n_actuadores = n_actuadores; }

    public abstract int realizar_accion();

}
