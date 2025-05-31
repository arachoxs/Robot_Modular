public abstract class Modulo {
    private int id;
    private String referencia;
    private String descripcion;
    private int largo;
    private int ancho;
    private int profundidad;
    private boolean encendido;
    private SistemaControl sistema_control;
    private SistemaComunicacion sistema_comunicacion;

    public Modulo(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido) {
        this.id = id;
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.largo = largo;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.encendido = encendido;
        this.sistema_control = new SistemaControl(this);
        this.sistema_comunicacion = new SistemaComunicacion(this, Global.red);
    }

    //Getters y Setters
    public int get_id(){
        return this.id;
    }
    public String get_referencia(){
        return this.referencia;
    }
    public String get_descripcion(){
        return this.descripcion;
    }
    public int get_largo(){
        return this.largo;
    }
    public int get_ancho(){
        return this.ancho;
    }
    public int get_profundidad(){
        return this.profundidad;
    }
    public boolean get_encendido(){
        return this.encendido;
    }
    public int[] get_pos_robot(){
        return Global.robot.get_pos();
    }
    public int[] get_direccion_robot(){
        return Global.robot.get_direccion();
    }

    public SistemaComunicacion get_sistema_comunicacion(){
        return this.sistema_comunicacion;
    }
    public SistemaControl get_sistema_control(){ return this.sistema_control; }

    public void set_id(int id){ this.id=id;}
    public void set_referencia(String referencia){ this.referencia = referencia;}
    public void set_descripcion(String descripcion){ this.descripcion = descripcion;}
    public void set_largo(int largo){ this.largo = largo;}
    public void set_ancho(int ancho){ this.ancho = ancho;}
    public void set_profundidad(int profundidad){ this.profundidad = profundidad;}
    public void set_encendido(boolean encendido){ this.encendido = encendido;}
    public void set_sistema_comunicacion(SistemaComunicacion sistema_comunicacion){ this.sistema_comunicacion = sistema_comunicacion;}
    public void set_sistema_control(SistemaControl sistema_control){ this.sistema_control = sistema_control;}

    //métodos
    public abstract void encender();
    public abstract void apagar();

    public void recibir_info_accion(String info) {
        // Lógica para recibir información de acción
        if (Global.log == true) System.out.println("Módulo " + id + " recibió info de acción: " + info);
    }

    public abstract void enviar_respuesta_accion(boolean respuesta);

    public String gestionar_solucion() {
        return sistema_control.gestionar_solucion();
    }

    public abstract void interpretar_mensaje(String mensaje); //interpreta lo que el sistema de comunicacion recibe

}