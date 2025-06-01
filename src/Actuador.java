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
        int[] direccion_robot = Global.robot.get_direccion();
        int[] posicion_robot = Global.robot.get_pos();

        // Posición Actual de la mascota
        int columna_actual_mascota = posicion_robot[0] + direccion_robot[0];
        int fila_actual_mascota = posicion_robot[1] + direccion_robot[1] * -1;

        // Encontrar nueva posición aleatoria para la mascota
        int[] nueva_posicion = encontrar_posicion_aleatoria_vecindario(fila_actual_mascota, columna_actual_mascota);

        if (nueva_posicion != null) {
            // Limpiar posición actual de la mascota
            Global.mapa.set_celda(fila_actual_mascota, columna_actual_mascota, Mapa.AIRE);
            // Colocar mascota en nueva posición
            Global.mapa.set_celda(nueva_posicion[0], nueva_posicion[1], Mapa.MASCOTA);
            if (Global.log) System.out.println("Mascota movida a: [" + nueva_posicion[0] + "," + nueva_posicion[1] + "]");
            return 1; // Código de éxito
        } else {
            if (Global.log) System.out.println("La mascota no encontró a dónde huir");
            return 0; // Código de fallo
        }
    }

    private int[] encontrar_posicion_aleatoria_vecindario(int filaActual, int columnaActual) {
        // Definir las 25 posiciones del vecindario
        int[][] desplazamientos = {
                { -2, -2 }, { -2, -1 }, { -2, 0 }, { -2, 1 }, { -2, 2 }, // Fila -2
                { -1, -2 }, { -1, -1 }, { -1, 0 }, { -1, 1 }, { -1, 2 }, // Fila -1
                {  0, -2 }, {  0, -1 }, {  0, 0 }, {  0, 1 }, {  0, 2 }, // Fila 0
                {  1, -2 }, {  1, -1 }, {  1, 0 }, {  1, 1 }, {  1, 2 }, // Fila 1
                {  2, -2 }, {  2, -1 }, {  2, 0 }, {  2, 1 }, {  2, 2 }  // Fila 2
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
