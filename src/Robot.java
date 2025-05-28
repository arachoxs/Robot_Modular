import java.util.ArrayList;
import java.util.List;

public class Robot {
    private String serie;
    private String alias;
    private String descripcion;
    private List<Modulo> modulos;
    private boolean encendido;

    public Robot(String Serie, String Alias, String Descripcion) {
        this.serie = Serie;
        this.alias = Alias;
        this.descripcion = Descripcion;
        this.modulos = new java.util.ArrayList<>();
        this.encendido = false;

        //no se agregan modulos por defecto - todos se agregaran desde agregarModulo
    }

    public void agregar_modulo(Modulo modulo){
        this.modulos.add(modulo);
    }

    public void agregar_extension(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        Extension extension = new Extension(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
        this.agregar_modulo(extension);
    }

    public void agregar_rotacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        Rotacion rotacion = new Rotacion(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
        this.agregar_modulo(rotacion);
    }

    public void agregar_helicoidal(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        Helicoidal helicoidal = new Helicoidal(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
        this.agregar_modulo(helicoidal);
    }

    public void agregar_sensor_proximidad(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_sensores) {
        SensorProximidad sensorProx = new SensorProximidad(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_sensores);
        Sensor sensor = new Sensor(123, "proximidad", "Sensor que detecta objetos");
        sensorProx.agregar_sensor(sensor); // composición dentro del módulo
        this.agregar_modulo(sensorProx);
    }

    public void agregar_camara(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_sensores) {
        Camara cam = new Camara(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_sensores);
        Sensor sensor = new Sensor(124, "visual", "Sensor que reconoce objetos");
        cam.agregar_sensor(sensor);
        this.agregar_modulo(cam);
    }

    public void agregar_altavoz(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_actuadores) {
        Altavoz altavoz = new Altavoz(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_actuadores);
        Actuador actuador = new Actuador(125, "sonido", "Emite alerta");
        altavoz.agregar_actuador(actuador);
        this.agregar_modulo(altavoz);
    }

    public Modulo get_modulo_Id(int id) {
        for (Modulo modulo : modulos) {
            if (modulo.get_id() == id) {
                return modulo;
            }
        }
        return null; // Si no se encuentra
    }



    public void encender(){
        this.encendido = true;
        System.out.println("Robot " + alias + " encendido");

        // Encender primero los módulos dinámicos
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloDinamico) {
                modulo.encender();
            }
        }

        // Luego encender los módulos estáticos
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloEstatico) {
                modulo.encender();
            }
        }
    }

    public void apagar(){
        this.encendido = false;
        System.out.println("Robot " + alias + " apagado");

        // Apagar primero los módulos dinámicos
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloDinamico) {
                modulo.apagar();
            }
        }

        // Luego apagar los módulos estáticos
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloEstatico) {
                modulo.apagar();
            }
        }
    }
}
