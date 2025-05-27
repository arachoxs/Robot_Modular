import java.util.List;

public abstract class Modulo {
    private int id;
    private String referencia;
    private String descripcion;
    private int largo;
    private int ancho;
    private int profundidad;
    private boolean encendido;
    private Sistema_Control sistemaControl;
    private Sistema_Comunicacion sistemaComunicacion;

    public Modulo(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido) {
        this.id = id;
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.largo = largo;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.encendido = encendido;
        this.sistemaControl = new Sistema_Control(this);
        this.sistemaComunicacion = new Sistema_Comunicacion(this);
    }

    //Getters
    public int getId(){
        return this.id;
    }
    public String getReferencia(){
        return this.referencia;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public int getLargo(){
        return this.largo;
    }
    public int getAncho(){
        return this.ancho;
    }
    public int getProfundidad(){
        return this.profundidad;
    }
    public boolean getEncendido(){
        return this.encendido;
    }
    public Sistema_Comunicacion getSistemaComunicacion(){
        return this.sistemaComunicacion;
    }
    public Sistema_Control getSistemaControl(){
        return this.sistemaControl;
    }

    //Setters


    //metodos
    public void encender(){
        this.encendido=true;
    }

    public void apagar(){
        this.encendido=false;
    }

    public abstract String interpretar_mensaje(String mensaje); //interpreta lo que el sistema de comunicacion recibe

}