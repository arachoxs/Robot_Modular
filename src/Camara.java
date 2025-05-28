import java.util.ArrayList;
import java.util.List;

public class Camara extends Percepcion{
    private List<Sensor> sensores = new ArrayList<>();

    public Camara(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int N_Sensores){
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, N_Sensores);
    }

    @Override
    public int procesar_datos(int[] mapa) {
        return 0;
    }

    public void agregar_sensor(Sensor sensor){
        this.sensores.add(sensor);
    }

    @Override
    public String interpretar_mensaje(String mensaje) {
        return "";
    }
}
