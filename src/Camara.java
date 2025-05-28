import java.util.ArrayList;
import java.util.List;

public class Camara extends Percepcion{
    private List<Sensor> sensores = new ArrayList<>();

    public Camara(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int N_Sensores){
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, N_Sensores);
    }

    public void agregar_sensor(Sensor sensor){
        this.sensores.add(sensor);
        //aumentar numero sensores
    }

    // Getters
    public List<Sensor> getSensores() { return sensores; }

    @Override
    public void interpretar_mensaje(String mensaje) {
    }

    @Override
    public int procesar_datos(Object datos) {
        // Lógica específica para procesar datos de imagen
        System.out.println("Procesando datos de imagen");
        return 1; // Código de éxito
    }

    @Override
    public Object captar_informacion() {
        for(Sensor sensor : sensores){
            return sensor.captar_informacion();
        }

        return 1;
    }

    @Override
    public void encender() {
        System.out.println("Cámara encendida");
    }

    @Override
    public void apagar() {
        System.out.println("Cámara apagada");
    }

}
