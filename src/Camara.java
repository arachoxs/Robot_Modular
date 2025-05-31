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
        if(mensaje.equals("RECONOCER OBJETO")){
            this.captar_informacion();
        }
        else if(mensaje.equals("VERIFICAR IZQUIERDA")){
            this.procesar_datos(captar_informacion(),"VERIFICAR IZQUIERDA");
        }
    }

    @Override
    public int procesar_datos(int datos,String instruccion) {
        if(datos==-1){
            return 0;
        }

        if(datos==1){ //bloque
            if(instruccion.equals("VERIFICAR IZQUIERDA")){
                this.get_sistema_control().enviar_respuesta_accion(2,"ROTACION IZQUIERDA");
            }else if(instruccion.equals("IZQUIERDA FALLIDO")){
                this.get_sistema_control().enviar_respuesta_accion(2,"ROTACION IZQUIERDA FALLIDA");
            }
            this.get_sistema_comunicacion().enviar_mensaje(1,"MOVER FIJO");
        }else{ //mascota
            if(instruccion.equals("VERIFICAR IZQUIERDA") && datos==1){
                if(instruccion.equals("VERIFICAR IZQUIERDA")){
                    this.get_sistema_control().enviar_respuesta_accion(4,"IZQUIERDA FALLIDO");
                }

            }
            else{
                this.get_sistema_control().enviar_respuesta_accion(4,"RECONOCER OBJETO");
            }
        }

        return 1;
    }

    @Override
    public int captar_informacion() {
        int scan=-1;

        for (Sensor sensor : sensores){
            scan = sensor.captar_informacion();
        }

        return scan;
    }

    @Override
    public void encender() {
        System.out.println("Cámara encendida");
    }

    @Override
    public void apagar() {
        System.out.println("Cámara apagada");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {

    }

}
