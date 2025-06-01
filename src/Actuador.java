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
        int[] direccionRobot = Global.robot.get_direccion();
        int[] posicionRobot = Global.robot.get_pos();

        // Posición Actual de la mascota
        int ColumnaActualMascota = posicionRobot[0] + direccionRobot[0];
        int FilaActualMascota = posicionRobot[1] + direccionRobot[1] * -1;

        // Encontrar nueva posición aleatoria para la mascota
        int[] nuevaPosicion = encontrarPosicionAleatoriaVecindario(FilaActualMascota, ColumnaActualMascota);

        if (nuevaPosicion != null) {
            // Limpiar posición actual de la mascota
            Global.mapa.set_celda(FilaActualMascota, ColumnaActualMascota, Mapa.AIRE);
            // Colocar mascota en nueva posición
            Global.mapa.set_celda(nuevaPosicion[0], nuevaPosicion[1], Mapa.MASCOTA);
            if (Global.log) System.out.println("Mascota movida a: [" + nuevaPosicion[0] + "," + nuevaPosicion[1] + "]");
            return 1; // Código de éxito
        } else {
            if (Global.log) System.out.println("No se encontró posición válida para mover la mascota");
            return 0; // Código de fallo
        }
    }

    private int[] encontrarPosicionAleatoriaVecindario(int filaActual, int columnaActual) {
        // Definir las 9 posiciones del vecindario de Moore (incluyendo la posición
        // actual)
        int[][] desplazamientos = {
                { -1, -1 }, { -1, 0 }, { -1, 1 }, // Fila superior
                { 0, -1 }, { 0, 0 }, { 0, 1 }, // Fila actual
                { 1, -1 }, { 1, 0 }, { 1, 1 } // Fila inferior
        };

        // Crear lista de posiciones válidas (vacías)
        java.util.List<int[]> posicionesValidas = new java.util.ArrayList<>();

        for (int[] desplazamiento : desplazamientos) {
            int nuevaFila = filaActual + desplazamiento[0];
            int nuevaColumna = columnaActual + desplazamiento[1];

            // Verificar si la posición es válida y está vacía (AIRE = 0)
            if (Global.mapa.es_valida(nuevaFila, nuevaColumna) &&
                    Global.mapa.get_celda(nuevaFila, nuevaColumna) == Mapa.AIRE) {
                posicionesValidas.add(new int[] { nuevaFila, nuevaColumna });
            }
        }

        // Si hay posiciones válidas, seleccionar una aleatoriamente
        if (!posicionesValidas.isEmpty()) {
            java.util.Random random = new java.util.Random();
            int indiceAleatorio = random.nextInt(posicionesValidas.size());
            return posicionesValidas.get(indiceAleatorio);
        }

        // Si no hay posiciones válidas, retornar null
        return null;
    }

    // Getters
    public int get_id() {
        return id;
    }

    public String get_tipo() {
        return tipo;
    }

    public String get_descripcion() {
        return descripcion;
    }

    // Setters
    public void set_id(int id) {
        this.id = id;
    }

    public void set_tipo(String tipo) {
        this.tipo = tipo;
    }

    public void set_descripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
