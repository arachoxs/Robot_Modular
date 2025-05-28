import java.util.ArrayList;
import java.util.List;

public class Altavoz extends Actuacion {
    private List<Actuador> actuadores;

    public Altavoz(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_actuadores){
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_actuadores);
        this.actuadores = new ArrayList<>();
    }

    @Override
    public int realizar_accion() {
        int retorno;

        for(Actuador actuador : actuadores){
            retorno = actuador.realizar_accion();
        }

        return 1;
    }
}
