public abstract class Modulo_Dinamico extends Modulo{
    private int N_Motores;

    //Constructor
    public Modulo_Dinamico(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int N_Motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
        this.N_Motores = N_Motores;
    }

    //Getters
    public int getN_Motores() { return N_Motores; }

    //Setters
    public void setN_Motores(int N_Motores) { this.N_Motores = N_Motores; }

    //Methods

    public abstract boolean moverse(int n_pasos, int direccion);
}
