public class Tablero {
    private int[][] matriz;
    private final int SIZE = 100;

    // Constantes para los diferentes tipos de contenido
    public static final int LIBRE = 0;
    public static final int OBSTACULO = 1;
    public static final int MASCOTA = 2;
    public static final int ROBOT = 3;

    public Tablero() {
        matriz = new int[SIZE][SIZE];
        inicializar_tablero();
    }

    private void inicializar_tablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matriz[i][j] = LIBRE;
            }
        }
    }

    public void colocar_obstaculo(int fila, int columna) {
        if (es_valida_posicion(fila, columna)) {
            matriz[fila][columna] = OBSTACULO;
        }
    }

    public void colocar_mascota(int fila, int columna) {
        if (es_valida_posicion(fila, columna)) {
            matriz[fila][columna] = MASCOTA;
        }
    }

    public void colocar_robot(int fila, int columna) {
        if (es_valida_posicion(fila, columna)) {
            matriz[fila][columna] = ROBOT;
        }
    }

    public int obtener_celda(int fila, int columna) {
        if (es_valida_posicion(fila, columna)) {
            return matriz[fila][columna];
        }
        return -1; // Posición inválida
    }

    public boolean es_valida_posicion(int fila, int columna) {
        return fila >= 0 && fila < SIZE && columna >= 0 && columna < SIZE;
    }

    public void limpiar_celda(int fila, int columna) {
        if (es_valida_posicion(fila, columna)) {
            matriz[fila][columna] = LIBRE;
        }
    }

    public boolean es_posicion_libre(int fila, int columna) {
        return es_valida_posicion(fila, columna) && matriz[fila][columna] == LIBRE;
    }

    public boolean hay_obstaculo(int fila, int columna) {
        return es_valida_posicion(fila, columna) && matriz[fila][columna] == OBSTACULO;
    }

    public boolean hay_mascota(int fila, int columna) {
        return es_valida_posicion(fila, columna) && matriz[fila][columna] == MASCOTA;
    }

    public void mostrar_tablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int get_tamaño() {
        return SIZE;
    }

    // Método útil para obtener estadísticas del tablero
    public String obtener_estadisticas() {
        int obstaculos = 0, mascotas = 0, robots = 0, libres = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                switch (matriz[i][j]) {
                    case LIBRE: libres++; break;
                    case OBSTACULO: obstaculos++; break;
                    case MASCOTA: mascotas++; break;
                    case ROBOT: robots++; break;
                }
            }
        }

        return String.format("Libres: %d, Obstáculos: %d, Mascotas: %d, Robots: %d",
                libres, obstaculos, mascotas, robots);
    }
}