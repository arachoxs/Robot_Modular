public class Rotacion extends Modulo_Dinamico {

    //Consrtuctor
    public Rotacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int N_Motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, N_Motores);
    }

    //Methods
    @Override
    public boolean moverse(int n_pasos, int direccion) {
        return true;
    }
}
