import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Actuador {
    private int id;
    private String tipo;
    private String descripcion;

    public Actuador(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }
    public int realizar_accion() {
        if (Global.mapa == null) return -1;

        int[] posicion_actual = Global.robot.get_pos();
        List<int[]> posiciones = new ArrayList<>();
        int[][] direcciones = {
                { -1, -1 }, { -1, 0 }, { -1, 1 },
                {  0, -1 },           {  0, 1 },
                {  1, -1 }, {  1, 0 }, {  1, 1 }
        };

        for (int[] dir : direcciones) {
            int nuevaFila = posicion_actual[1] + dir[1];
            int nuevaCol = posicion_actual[0] + dir[0];
            if (Global.mapa.es_valida(nuevaFila, nuevaCol)) {
                posiciones.add(new int[] { nuevaFila, nuevaCol });
            }
        }

        boolean mascota_encontrada = false;

        for (int[] pos : posiciones) {
            int fila = pos[0];
            int columna = pos[1];
            int valor = Global.mapa.get_celda(fila, columna);

            if (Global.log)
                System.out.println("Revisando posición [" + fila + ", " + columna + "], valor: " + valor);

            if (valor != Mapa.MASCOTA) continue;

            mascota_encontrada = true;

            if (Global.log)
                System.out.println("Mascota encontrada en la posición: " + fila + ", " + columna);

            int[] nueva_pos = encontrar_posicion_aleatoria_vecindario(fila, columna);
            if (nueva_pos != null) {
                Global.mapa.set_celda(fila, columna, Mapa.AIRE);
                Global.mapa.set_celda(nueva_pos[0], nueva_pos[1], Mapa.MASCOTA);
                if (Global.log)
                    System.out.println("Mascota movida a: [" + nueva_pos[0] + ", " + nueva_pos[1] + "]");
            } else {
                if (Global.log)
                    System.out.println("No hay lugar para mover la mascota desde: [" + fila + ", " + columna + "]");
            }
        }
        if (!mascota_encontrada && Global.log)
            System.out.println("No se encontró ninguna mascota en las posiciones adyacentes.");

        return mascota_encontrada ? 1 : 0;
    }

    private int[] encontrar_posicion_aleatoria_vecindario(int filaActual, int columnaActual) {
        // Definir las 25 posiciones del vecindario
        int[][] desplazamientos = {
                { -2, -2 }, { -2, -1 }, { -2, 0 }, { -2, 1 }, { -2, 2 }, // Fila -2
                { -1, -2 }, { -1, -1 }, { -1, 0 }, { -1, 1 }, { -1, 2 }, // Fila -1
                {  0, -2 }, {  0, -1 }, {  0, 0 }, {  0, 1 }, {  0, 2 }, // Fila 0
                {  1, -2 }, {  1, -1 }, {  1, 0 }, {  1, 1 }, {  1, 2 }, // Fila 1
                {  2, -2 }, {  2, -1 }, {  2, 0 }, {  2, 1 }, {  2, 2 }  // Fila 2

                /*{ -1, -1 }, { -1, 0 }, { -1, 1 }, // Fila -1
                {  0, -1 }, {  0, 0 }, {  0, 1 }, // Fila 0
                {  1, -1 }, {  1, 0 }, {  1, 1 }   // Fila +1*/
        };

        // Crear lista de posiciones válidas (vacías)
        java.util.List<int[]> posiciones_validas = new java.util.ArrayList<>();

        for (int[] desplazamiento : desplazamientos) {
            int nueva_fila = filaActual + desplazamiento[0];
            int nueva_columna = columnaActual + desplazamiento[1];

            // Verificar si la posición es válida y está vacía (AIRE = 0)
            if (Global.mapa.es_valida(nueva_fila, nueva_columna) &&
                    Global.mapa.get_celda(nueva_fila, nueva_columna) == Mapa.AIRE) {
                posiciones_validas.add(new int[] { nueva_fila, nueva_columna});
            }
        }

        // Si hay posiciones válidas, seleccionar una aleatoriamente
        if (!posiciones_validas.isEmpty()) {
            java.util.Random random = new java.util.Random();
            int indiceAleatorio = random.nextInt(posiciones_validas.size());
            return posiciones_validas.get(indiceAleatorio);
        }

        // Si no hay posiciones válidas, retornar null
        return null;
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
