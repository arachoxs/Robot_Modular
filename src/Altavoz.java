public class Altavoz extends Actuacion {

    public Altavoz(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int N_Sensores){
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, N_Sensores);
    }

    @Override
    public int realizar_accion() {
        return 0;
    }
}
