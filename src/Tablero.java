public class Tablero {
    private int[][] matriz;
    private final int SIZE = 100;

    public Tablero() {
        matriz = new int[SIZE][SIZE];
        inicializar_tablero();
    }

    private void inicializar_tablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matriz[i][j] = 0; // 0 = espacio libre
            }
        }
    }

    public void colocar_obstaculo(int fila, int columna) {
        if (es_valida_posicion(fila, columna)) {
            matriz[fila][columna] = 1; // 1 = obstáculo
        }
    }

    public void colocar_mascota(int fila, int columna) {
        if (es_valida_posicion(fila, columna)) {
            matriz[fila][columna] = 2; // 2 = mascota
        }
    }

    public void colocar_robot(int fila, int columna) {
        if (es_valida_posicion(fila, columna)) {
            matriz[fila][columna] = 3; // 3 = robot
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
            matriz[fila][columna] = 0;
        }
    }

    public void mostrar_tablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int get_tamaño() { return SIZE; }
}