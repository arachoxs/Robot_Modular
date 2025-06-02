import java.util.Random;

public class Mapa {
    private int[][] matriz;
    private Random random;
    private int[] posicion_robot;

    // Constantes para los tipos de celdas
    public static final int TAMAÑO = 20;
    public static final int AIRE = 0;
    public static final int OBSTACULO = 1;
    public static final int MASCOTA = 2;
    public static final int ROBOT = 3;

    public Mapa() {
        this.matriz = new int[TAMAÑO][TAMAÑO];
        this.random = new Random();
        inicializar_mapa();
    }

    private void inicializar_mapa() {
        // Llenar todo con aire inicialmente
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                matriz[i][j] = AIRE;
            }
        }
        for (int i = 0; i < TAMAÑO; i++){
           for (int j = 0; j < TAMAÑO; j++){
               if (i == 0 || j == 0 || i == TAMAÑO - 1  || j == TAMAÑO - 1)
                   set_celda(i, j, OBSTACULO);
           }
        }

        // Generar obstáculos (1/6 de probabilidad)

        // Generar mascotas (1/30 de probabilidad)

        // Inicializar robot
        set_celda(Global.robot.get_pos()[1], Global.robot.get_pos()[0], ROBOT);
        posicion_robot = Global.robot.get_pos();
    }

    public void imprimir_mapa() {
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int get_celda(int fila,int columna) {
        if (es_valida(fila, columna)) {
            return this.matriz[fila][columna];
        }
        return -1; // Posición inválida
    }

    public void set_celda(int fila,int columna, int valor) {
        if (es_valida(fila, fila)) {
            this.matriz[fila][columna] = valor;
        }
    }

    public void actualizar_posicion_robot(){
        int[] posicion_actual = Global.robot.get_pos();
        if (es_valida(posicion_actual[1],posicion_actual[0])) {
            set_celda(posicion_robot[1], posicion_robot[0], AIRE);
            set_celda(posicion_actual[1], posicion_actual[0], ROBOT);
            posicion_robot = posicion_actual;
        }
        else{
            set_celda(posicion_robot[1], posicion_robot[0], AIRE);
            posicion_robot = posicion_actual;
        }
    }


    public boolean es_valida(int fila, int columna) {
        return fila >= 0 && fila < TAMAÑO && columna >= 0 && columna < TAMAÑO;
    }

}