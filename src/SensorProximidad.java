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
    public int captar_informacion() {
        int scan=-1;

        for (Sensor sensor : sensores){
            scan = sensor.captar_informacion();
        }

        if(scan==-1){
            return 0; //hubo un error en la toma de datos
        }
        else if(scan==0){ //hay aire
            this.get_sistema_comunicacion().enviar_mensaje(1,"MOVER FIJO"); //envia mensaje a ext para que se mueva fijo 1.
        }
        else{ //existe algo diferente a aire se tiene que verificar que es
            this.get_sistema_comunicacion().enviar_mensaje(4,"verificar objeto");
        }

        return 1;
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        if(mensaje=="Verificar"){
            this.captar_informacion();
        }
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
