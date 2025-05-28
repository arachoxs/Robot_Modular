import java.util.ArrayList;
import java.util.List;


public class SensorProximidad extends Percepcion{
    private List<Sensor> sensores;

    public SensorProximidad(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_sensores){
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_sensores);
        this.sensores = new ArrayList<>();
    }


    public void agregar_sensor(Sensor sensor){
        this.sensores.add(sensor);
    }

    @Override
    public int procesar_datos(Object datos) {
        return 0;
    }

    @Override
    public Object captar_informacion() {
        for (Sensor sensor : sensores){
            return sensor.captar_informacion();
        }

        return null;
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
    }

    @Override
    public void encender() {
        System.out.println("Sensor de Proximidad encendido");
    }

    @Override
    public void apagar() {
        System.out.println("Sensor de Proximidad apagado");
    }
}
