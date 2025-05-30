import java.util.Random;

public class Mapa {
    private int[][] matriz;
    private static final int TAMAÑO = 30;
    private Random random;

    // Constantes para los tipos de celdas
    public static final int AIRE = 0;
    public static final int OBSTACULO = 1;
    public static final int MASCOTA = 2;
    public static final int ROBOT = 3;

    public Mapa() {
        this.matriz = new int[TAMAÑO][TAMAÑO];
        this.random = new Random();
        inicializarMapa();
    }

    private void inicializarMapa() {
        // Llenar todo con aire inicialmente
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                matriz[i][j] = AIRE;
            }
        }

        // Generar obstáculos (1/6 de probabilidad)
        int totalCeldas = TAMAÑO * TAMAÑO;
        int numObstaculos = totalCeldas / 6;

        for (int i = 0; i < numObstaculos; i++) {
            int fila, columna;
            do {
                fila = random.nextInt(TAMAÑO);
                columna = random.nextInt(TAMAÑO);
            } while (matriz[fila][columna] != AIRE || (fila == 15 && columna == 15)); // No colocar en centro

            matriz[fila][columna] = OBSTACULO;
        }

        // Generar mascotas (1/30 de probabilidad)
        int numMascotas = totalCeldas / 30;

        for (int i = 0; i < numMascotas; i++) {
            int fila, columna;
            do {
                fila = random.nextInt(TAMAÑO);
                columna = random.nextInt(TAMAÑO);
            } while (matriz[fila][columna] != AIRE || (fila == 15 && columna == 15)); // No colocar en centro

            matriz[fila][columna] = MASCOTA;
        }

        // Colocar robot en el centro
        matriz[15][15] = ROBOT;
    }

    public int getCelda(int fila, int columna) {
        if (esValida(fila, columna)) {
            return matriz[fila][columna];
        }
        return -1; // Posición inválida
    }

    public void setCelda(int fila, int columna, int valor) {
        if (esValida(fila, columna)) {
            matriz[fila][columna] = valor;
        }
    }

    public boolean esValida(int fila, int columna) {
        return fila >= 0 && fila < TAMAÑO && columna >= 0 && columna < TAMAÑO;
    }

    public boolean esCeldaLibre(int fila, int columna) {
        return esValida(fila, columna) && matriz[fila][columna] == AIRE;
    }

    public boolean hayObstaculo(int fila, int columna) {
        return esValida(fila, columna) && matriz[fila][columna] == OBSTACULO;
    }

    public boolean hayMascota(int fila, int columna) {
        return esValida(fila, columna) && matriz[fila][columna] == MASCOTA;
    }

    public void moverRobot(int filaAnterior, int columnaAnterior, int filaNueva, int columnaNueva) {
        if (esValida(filaAnterior, columnaAnterior) && esValida(filaNueva, columnaNueva)) {
            // Limpiar posición anterior (solo si era robot)
            if (matriz[filaAnterior][columnaAnterior] == ROBOT) {
                matriz[filaAnterior][columnaAnterior] = AIRE;
            }
            // Colocar robot en nueva posición
            matriz[filaNueva][columnaNueva] = ROBOT;
        }
    }

    public int getTamaño() {
        return TAMAÑO;
    }

    public int[][] getMatrizCompleta() {
        // Devolver copia para evitar modificaciones externas
        int[][] copia = new int[TAMAÑO][TAMAÑO];
        for (int i = 0; i < TAMAÑO; i++) {
            System.arraycopy(matriz[i], 0, copia[i], 0, TAMAÑO);
        }
        return copia;
    }

    public void regenerarMapa() {
        inicializarMapa();
    }

    public String getEstadisticas() {
        int aire = 0, obstaculos = 0, mascotas = 0, robots = 0;

        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                switch (matriz[i][j]) {
                    case AIRE: aire++; break;
                    case OBSTACULO: obstaculos++; break;
                    case MASCOTA: mascotas++; break;
                    case ROBOT: robots++; break;
                }
            }
        }

        return String.format("Aire: %d, Obstáculos: %d, Mascotas: %d, Robots: %d",
                aire, obstaculos, mascotas, robots);
    }
}