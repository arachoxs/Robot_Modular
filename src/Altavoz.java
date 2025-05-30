import java.util.ArrayList;
import java.util.List;

public class Altavoz extends Actuacion {
    private List<Actuador> actuadores;

    public Altavoz(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_actuadores){
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_actuadores);
        this.actuadores = new ArrayList<>();
    }

    public void agregar_actuador(Actuador actuador){
        this.actuadores.add(actuador);
        //aumentar numero actuadores
    }


    //getters
    public List<Actuador> getActuadores() { return actuadores; }

    @Override
    public int realizar_accion() {
        int retorno=0;

        for(Actuador actuador : actuadores){
            retorno = actuador.realizar_accion();
        }

        if(retorno==0){//accion fallida hubieron errores
            return 0;
        }
        else{
            System.out.println("Accion completada con exito");
        }

        return 1; //accion completada con exito
    }

    @Override
    public void encender() {
        System.out.println("Altavoz encendido");
    }

    @Override
    public void apagar() {
        System.out.println("Altavoz apagado");
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
    }
}
