public class Rotacion extends ModuloDinamico {

    //Constructor
    public Rotacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
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

    @Override
    public void encender() {
        System.out.println("Módulo de Rotación encendido");
    }

    @Override
    public void apagar() {
        System.out.println("Módulo de Rotación apagado");
    }
}
