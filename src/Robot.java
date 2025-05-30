import java.util.ArrayList;
import java.util.List;

public class Robot {
    private String serie;
    private String alias;
    private String descripcion;
    private List<Modulo> modulos;
    public int[] pos;
    public int[] direccion;
    private boolean encendido;

    public Robot(String Serie, String Alias, String Descripcion) {
        this.serie = Serie;
        this.alias = Alias;
        this.descripcion = Descripcion;
        this.modulos = new java.util.ArrayList<>();
        this.encendido = false;

        this.pos = new int[2];
        this.direccion = new int[2];

        //Inicializar posicion y direccion
        this.pos[0] = 0;//x
        this.pos[1] = 0;//y

        this.direccion[0] = 1;//x
        this.direccion[1] = 0;//y

        //no se agregan modulos por defecto - todos se agregaran desde agregarModulo
    }

// Getters y Setters

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public int[] getPos() {
        return pos.clone();
    }

    public void setPos(int[] pos) {
        if (pos != null && pos.length == 2) {
            //this.pos[0] = pos[0];
            //this.pos[1] = pos[1];
        }
    }

    public void setPos(int fila, int columna) {
        this.pos[0] = fila;
        this.pos[1] = columna;
    }

    public int[] getDireccion() {
        return direccion.clone();
    }

    public void setDireccion(int[] direccion) {
        if (direccion != null && direccion.length == 2) {
            //this.direccion[0] = direccion[0];
            //.direccion[1] = direccion[1];
        }
    }

    public boolean isEncendido() {
        return encendido;
    }

    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
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

    public Modulo get_modulo_id(int id) {
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
