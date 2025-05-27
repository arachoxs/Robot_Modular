public class Extension extends Modulo_Dinamico {

    //Constructor
    public Extension(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int N_Motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, N_Motores);
    }

    //Methods
    @Override
    public boolean moverse(int n_pasos, int direccion) {
        return true;
    }
}
