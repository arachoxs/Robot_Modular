import java.util.ArrayList;
import java.util.List;

public class Robot {
    private String serie;
    private String alias;
    private String descripcion;
    private int[] pos;
    private char direccion;
    private List<Modulo> modulos = new ArrayList<>();

    public Robot(String Serie, String Alias, String Descripcion, int[] Pos, char direccion) {
        this.serie = Serie;
        this.alias = Alias;
        this.descripcion = Descripcion;
        this.pos = Pos;
        this.direccion = direccion;

        //no se agregan modulos por defecto - todos se agregaran desde agregarModulo
    }

    public void agregar_modulo(Modulo modulo){
        this.modulos.add(modulo);
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
