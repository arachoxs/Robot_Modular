import java.util.List;

public abstract class ModuloDinamico extends Modulo{
    private int n_motores;

    //Constructor
    public ModuloDinamico(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
        this.n_motores = n_motores;
    }

    //Getters y Setters
    public int get_n_motores() { return n_motores; }
    public void set_n_motores(int n_motores) { this.n_motores = n_motores; }

    public abstract boolean moverse(int n_pasos, int[] direccion);

    }
