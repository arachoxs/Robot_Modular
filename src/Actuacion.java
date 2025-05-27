public abstract class Actuacion extends Modulo_Estatico{
    private int N_Actuadores;

    public Actuacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int N_Sensores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
        this.N_Actuadores = N_Actuadores;
    }

    public abstract int realizar_accion();
}
