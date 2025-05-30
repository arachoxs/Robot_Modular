public abstract class Percepcion extends ModuloEstatico {
    private int n_sensores;

    public Percepcion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_sensores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
        this.n_sensores = n_sensores;
    }

    // Getters y Setters
    public int get_n_sensores() { return n_sensores; }
    public void set_n_sensores(int n_sensores) { this.n_sensores = n_sensores; }

    public abstract int procesar_datos(int datos);
    public abstract int captar_informacion();
}
