public class Rotacion extends ModuloDinamico {

    //Consrtuctor
    public Rotacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int N_Motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, N_Motores);
    }

    //Methods
    @Override
    public boolean moverse(int n_pasos, int[] direccion) {
        // Lógica específica para movimiento de rotación (giros)
        System.out.println("Rotando: " + n_pasos + " grados");
        return true;
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
    }
}
