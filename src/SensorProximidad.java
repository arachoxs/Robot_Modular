import java.util.ArrayList;
import java.util.List;


public class SensorProximidad extends Percepcion{
    private List<Sensor> sensores;

    public SensorProximidad(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_sensores){
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_sensores);
        this.sensores = new ArrayList<>();
    }

    // Getters y Setters
    public List<Sensor> get_sensores() {
        return sensores;
    }

    public void agregar_sensor(Sensor sensor){
        this.sensores.add(sensor);
        //aumentar numero sensores
        this.set_n_sensores(this.get_n_sensores() + 1);
    }

    public int procesar_datos(int datos, String instruccion) {
        if(datos == -1){
            return 0;
        }

        if(datos == 0) {
            this.get_sistema_comunicacion().enviar_mensaje(Global.EXTENSION, "MOVER FIJO");
        }else if(datos == 2){
            this.get_sistema_comunicacion().enviar_mensaje(Global.ALTAVOZ_PRINCIPAL, "EMITIR SONIDO");
        }else{
            if(instruccion.equals("VERIFICAR IZQUIERDA")){
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA_PRINCIPAL,"IZQUIERDA FALLIDO");
            }else if(instruccion.equals("VERIFICAR DERECHA")){
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA_PRINCIPAL,"DERECHA FALLIDO");
            }else if(instruccion.equals("VERIFICAR")){
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA_PRINCIPAL,"RECONOCER OBJETO");
            }else if(instruccion.equals("VERIFICAR HELICOIDAL")){
                this.get_sistema_control().enviar_respuesta_accion(Global.CAMARA_PRINCIPAL,"RECONOCER OBJETO HELICOIDAL");
            }
        }

        return 1;
    }

    @Override
    public int captar_informacion() {
        for (Sensor sensor : sensores) {
            if (sensor.get_id() == Global.SENSOR_PRINCIPAL) {
                return sensor.captar_informacion();
            }
        }
        return -1; // Retorna -1 si no se encuentra el sensor principal
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        int resultado_accion = 0;
        String instruccion_normalizada = mensaje.trim().toUpperCase();

        switch (instruccion_normalizada) {
            case "OBSERVAR":{
                int objeto = this.captar_informacion();
                if (objeto != 0) {
                    System.out.println("Se detectó un objeto en frente.");
                    resultado_accion = 1;
                }
                else {
                    System.out.println("No se detectó ningún objeto en frente.");
                    resultado_accion = 1;
                }
                break;
            }
            case "VERIFICAR":
            case "VERIFICAR IZQUIERDA":
            case "VERIFICAR DERECHA":
            case "VERIFICAR HELICOIDAL":
                resultado_accion = this.procesar_datos(captar_informacion(), instruccion_normalizada);
                break;
            default:
                System.out.println("Instrucción no reconocida: " + mensaje);
                break;
        }

        this.enviar_respuesta_accion(resultado_accion == 1);
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
            if (Global.log) System.out.println("Medicion de proximidad ejecutada sin problemas.");
        }
        else{
            if (Global.log) System.out.println("Error en medicion de proximidad detectada, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }
}
