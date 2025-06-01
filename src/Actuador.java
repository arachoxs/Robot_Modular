import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Actuador {
    private int id;
    private String tipo;
    private String descripcion;
    private static Mapa mapaGlobal; // Referencia al mapa global

    public Actuador(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int realizar_accion() {
        if (Global.mapa == null) return -1;

        int[] posicion_actual = Global.robot.get_pos();
        List<int[]> posiciones = new ArrayList<>();
        Random random = new Random();

        int[][] direcciones = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1},  {1, 0},  {1, 1}
        };

        for (int[] dir : direcciones) {
            int nuevaFila = posicion_actual[0] + dir[0];
            int nuevaCol = posicion_actual[1] + dir[1];
            if (nuevaFila >= 0 && nuevaFila < Mapa.TAMAÑO &&
                    nuevaCol >= 0 && nuevaCol < Mapa.TAMAÑO) {
                posiciones.add(new int[]{nuevaFila, nuevaCol});
            }
        }

        for (int[] pos : posiciones) {
            int fila = pos[1];
            int columna = pos[0];
            int valor = Global.mapa.get_celda(fila, columna);

            if (valor != Mapa.MASCOTA) continue;
            if (Global.log) System.out.println("Mascota encontrada en la posición: " + fila + ", " + columna);

            // 1) Reemplaza la celda actual por aire
            Global.mapa.set_celda(fila, columna, Mapa.AIRE);

            // 2) Ubica la mascota en una posición aleatoria con aire
            boolean ubicada = false;
            while (!ubicada) {
                int nuevaFila = random.nextInt(Mapa.TAMAÑO);
                int nuevaCol = random.nextInt(Mapa.TAMAÑO);
                if (Global.mapa.get_celda(nuevaFila, nuevaCol) == Mapa.AIRE) {
                    Global.mapa.set_celda(nuevaFila, nuevaCol, Mapa.MASCOTA);
                    if (Global.log) System.out.println("La mascota se ha reubicado en la posición: " + nuevaFila + ", " + nuevaCol);
                    ubicada = true;
                }
            }
            if (!ubicada){
                if (Global.log) System.out.println("La mascota no encontró a dónde huir, así que se fue desintegrada por el láser del robot");
            }
        }

        return 1; // Código de éxito
    }

    // Getters
    public int get_id() { return id; }
    public String get_tipo() { return tipo; }
    public String get_descripcion() { return descripcion; }

    // Setters
    public void set_id(int id) { this.id = id; }
    public void set_tipo(String tipo) { this.tipo = tipo; }
    public void set_descripcion(String descripcion) { this.descripcion = descripcion; }
}
