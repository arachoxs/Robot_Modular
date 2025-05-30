import java.util.ArrayList;
import java.util.List;

public class Camara extends Percepcion{
    private List<Sensor> sensores;

    public Camara(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_sensores){
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_sensores);
        this.sensores = new ArrayList<>();
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
    public int captar_informacion() {
        int cap=-1;
        for(Sensor sensor : sensores){
            cap = sensor.captar_informacion();
        }

        if(cap==-1){
            return 0; //hay un error en la toma de la informacion
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
