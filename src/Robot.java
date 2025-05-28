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
