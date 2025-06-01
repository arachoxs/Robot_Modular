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

    public int procesar_datos(int datos, String instruccion) {
        if(datos == -1){
            return 0;
        }

        if(datos == 0){
            this.get_sistema_comunicacion().enviar_mensaje(Global.EXTENSION,"MOVER FIJO");
        }else{
            if(instruccion.equals("VERIFICAR IZQUIERDA")){
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA,"IZQUIERDA FALLIDO");
            }else if(instruccion.equals("VERIFICAR DERECHA")){
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA,"DERECHA FALLIDO");
            }else{
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA,"RECONOCER OBJETO");
            }
        }

        return 1;
    }

    @Override
    public int captar_informacion() {
        int scan = -1;

        for (Sensor sensor : sensores){
            scan = sensor.captar_informacion();
        }

        return scan;
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        int resultadoAccion = 0;
        String instruccionNormalizada = mensaje.trim().toUpperCase();

        switch (instruccionNormalizada) {
            case "VERIFICAR":
            case "VERIFICAR IZQUIERDA":
            case "VERIFICAR DERECHA":
                resultadoAccion = this.procesar_datos(captar_informacion(), instruccionNormalizada);
                break;
            default:
                System.out.println("Instrucción no reconocida: " + mensaje);
                break;
        }

        this.enviar_respuesta_accion(resultadoAccion == 1);
    }

    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log) System.out.println("Sensor de Proximidad encendido");
    }

    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log) System.out.println("Sensor de Proximidad apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if(respuesta){
            System.out.println("Medicion de proximidad ejecutada sin problemas.");
        }
        else{
            System.out.println("Error en medicion de proximidad detectada, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }
}
