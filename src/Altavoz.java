import java.util.ArrayList;
import java.util.List;

public class Altavoz extends Actuacion {
    private final List<Actuador> actuadores;

    public Altavoz(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_actuadores){
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_actuadores);
        this.actuadores = new ArrayList<>();
    }

    public void agregar_actuador(Actuador actuador){
        this.actuadores.add(actuador);
        //aumentar numero actuadores
        this.set_n_actuadores(this.get_n_actuadores() + 1);
    }


    //getters
    public List<Actuador> get_actuadores() { return actuadores; }

    @Override
    public int realizar_accion() {
        int retorno = 0;

        for(Actuador actuador : actuadores){
            if (actuador.get_id() == Global.ACTUADOR_PRINCIPAL)
                retorno = actuador.realizar_accion();
        }

        if(retorno==0){//acción fallida, hubo errores
            return 0;
        }
        else{
            if (Global.log) System.out.println("Acción completada con éxito");
        }

        return 1; //accion completada con exito
    }

    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log) System.out.println("Altavoz encendido");
    }

    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log) System.out.println("Altavoz apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {

    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        if(mensaje.equals("EMITIR SONIDO")){
            if (this.realizar_accion() == 1)
                this.get_sistema_control().enviar_respuesta_accion(Global.EXTENSION,"MOVER FIJO");
            else if (realizar_accion() == 0) {
                this.get_sistema_control().enviar_respuesta_accion(Global.ROTACION,"ROTACION IZQUIERDA");
            }
        }else if (mensaje.equals("EMITIR")){
            this.realizar_accion();
        }
    }
}
