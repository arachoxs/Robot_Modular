public class Extension extends ModuloDinamico {

    //Constructor
    public Extension(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    //Methods
    @Override
    public boolean moverse(int n_pasos, int direccion) {
        return true;
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        System.out.println("Movimiento");
    }

    @Override
    public void encender() {
        System.out.println("Módulo de Extensión encendido");
    }

    @Override
    public void apagar() {
        System.out.println("Módulo de Extensión apagado");
    }
}
