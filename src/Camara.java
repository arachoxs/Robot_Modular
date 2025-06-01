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
    public List<Sensor> get_sensores() { return sensores; }

    @Override
    public void interpretar_mensaje(String mensaje) {
        int resultado_accion = 0;
        String instruccion_normalizada = mensaje.trim().toUpperCase();

        switch (instruccion_normalizada) {
            case "RECONOCER OBJETO":
            case "IZQUIERDA FALLIDO":
            case "DERECHA FALLIDO":
            case "RECONOCER OBJETO HELICOIDAL":
                resultado_accion = this.procesar_datos(captar_informacion(), instruccion_normalizada);
                break;
            default:
                System.out.println("Mensaje no reconocido: " + mensaje);
                break;
        }

        this.enviar_respuesta_accion(resultado_accion == 1);

    }

    @Override
    public int procesar_datos(int datos, String instruccion) {
        if(datos==-1){
            return 0;
        }

        if(datos==1){ //bloque
            if(instruccion.equals("RECONOCER OBJETO")){
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION,"ROTACION IZQUIERDA");
            }else if(instruccion.equals("IZQUIERDA FALLIDO")){
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION,"ROTACION IZQUIERDA FALLIDA");
            }else if(instruccion.equals("DERECHA FALLIDO")){
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION,"ROTACION IZQUIERDA FIJA");
                this.get_sistema_control().enviar_respuesta_accion(Global.EXTENSION,"REVERSA");
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION,"ROTACION IZQUIERDA");
            }else if(instruccion.equals("RECONOCER OBJETO HELICOIDAL")){
                this.get_sistema_control().enviar_respuesta_accion(Global.HELICOIDAL,"MOVIMIENTO HELICOIDAL FALLIDO");
            }
        }else{ //mascota
            this.get_sistema_control().enviar_respuesta_accion(Global.ALTAVOZ,"ESPANTAR");
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
        this.set_encendido(true);
        if (Global.log) System.out.println("Cámara encendida");
    }

    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log) System.out.println("Cámara apagada");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {

    }

}
