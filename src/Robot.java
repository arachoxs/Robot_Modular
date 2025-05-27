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

        //Se meten los modulos

    }
}
