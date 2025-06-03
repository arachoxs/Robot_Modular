import java.util.Random;

/**
 * Clase Mapa - Representa el entorno 2D donde se mueve el robot
 * 
 * Esta clase gestiona una matriz bidimensional que simula el mundo físico
 * del robot. Contiene obstáculos, mascotas y la posición actual del robot.
 * Proporciona métodos para visualizar el mapa, validar posiciones y
 * actualizar el estado del entorno.
 */
public class Mapa {

    /** Matriz bidimensional que representa el entorno */
    private int[][] matriz;

    /** Generador de números aleatorios para elementos del mapa */
    private Random random;

    /** Posición actual del robot en el mapa [fila, columna] */
    private int[] posicion_robot;

    /** Tamaño del mapa (20x20 celdas) */
    public static final int TAMAÑO = 20;

    /** Celda vacía - espacio libre para movimiento */
    public static final int AIRE = 0;

    /** Celda con obstáculo - bloquea el movimiento */
    public static final int OBSTACULO = 1;

    /** Celda con mascota - debe ser ahuyentada */
    public static final int MASCOTA = 2;

    /** Celda con robot - posición actual del robot */
    public static final int ROBOT = 3;

    /**
     * Constructor que inicializa el mapa con bordes de obstáculos.
     * Crea la matriz, genera elementos aleatorios y posiciona el robot.
     */
    public Mapa() {
        this.matriz = new int[TAMAÑO][TAMAÑO];
        this.random = new Random();
        inicializar_mapa();
    }

    /**
     * Inicializa el mapa con aire, crea bordes de obstáculos y posiciona el robot.
     * Los bordes del mapa siempre contienen obstáculos para delimitar el área.
     */
    private void inicializar_mapa() {
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                matriz[i][j] = AIRE;
            }
        }

        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                if (i == 0 || j == 0 || i == TAMAÑO - 1 || j == TAMAÑO - 1)
                    set_celda(i, j, OBSTACULO);
            }
        }

        set_celda(Global.robot.get_pos()[1], Global.robot.get_pos()[0], ROBOT);
        posicion_robot = Global.robot.get_pos();
    }

    /**
     * Imprime el mapa en consola usando emojis para cada tipo de celda.
     * El robot se muestra con flechas según su dirección actual.
     */
    public void imprimir_mapa() {
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                if (matriz[i][j] == ROBOT) {
                    String sentido = "3";
                    int[] direccion = Global.robot.get_direccion();
                    switch (Main.traducir_direccion(direccion)) {
                        case "Norte":
                            sentido = "⬆️";
                            break;
                        case "Sur":
                            sentido = "⬇️";
                            break;
                        case "Este":
                            sentido = "➡️";
                            break;
                        case "Oeste":
                            sentido = "⬅️";
                            break;
                        default:
                            sentido = "🤖";
                            break;
                    }
                    System.out.print(Global.ANSI_RED + sentido + Global.ANSI_RESET + "\t");
                } else if (matriz[i][j] == MASCOTA) {
                    System.out.print("🐨" + "\t");
                } else if (matriz[i][j] == OBSTACULO) {
                    System.out.print("🧱" + "\t");
                } else {
                    System.out.print(" " + "\t");
                }
            }
            System.out.println();
        }
    }

    /**
     * Obtiene el valor de una celda específica del mapa.
     * 
     * @param fila    Fila de la celda
     * @param columna Columna de la celda
     * @return Valor de la celda (AIRE, OBSTACULO, MASCOTA, ROBOT) o -1 si es
     *         inválida
     */
    public int get_celda(int fila, int columna) {
        if (es_valida(fila, columna)) {
            return this.matriz[fila][columna];
        }
        return -1;
    }

    /**
     * Establece el valor de una celda específica del mapa.
     * 
     * @param fila    Fila de la celda
     * @param columna Columna de la celda
     * @param valor   Nuevo valor para la celda
     */
    public void set_celda(int fila, int columna, int valor) {
        if (es_valida(fila, fila)) {
            this.matriz[fila][columna] = valor;
        }
    }

    /**
     * Actualiza la posición del robot en el mapa.
     * Limpia la posición anterior y marca la nueva posición.
     */
    public void actualizar_posicion_robot() {
        int[] posicion_actual = Global.robot.get_pos();
        if (es_valida(posicion_actual[1], posicion_actual[0])) {
            set_celda(posicion_robot[1], posicion_robot[0], AIRE);
            set_celda(posicion_actual[1], posicion_actual[0], ROBOT);
            posicion_robot = posicion_actual;
        } else {
            set_celda(posicion_robot[1], posicion_robot[0], AIRE);
            posicion_robot = posicion_actual;
        }
    }

    /**
     * Verifica si una posición está dentro de los límites del mapa.
     * 
     * @param fila    Fila a verificar
     * @param columna Columna a verificar
     * @return true si la posición es válida, false en caso contrario
     */
    public boolean es_valida(int fila, int columna) {
        return fila >= 0 && fila < TAMAÑO && columna >= 0 && columna < TAMAÑO;
    }
}