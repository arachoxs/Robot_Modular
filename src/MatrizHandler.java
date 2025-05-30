import java.util.Scanner;

public class MatrizHandler {

    private int[][] matriz;

    MatrizHandler() {
        this.matriz = new int[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    // Método para actualizar la matriz desde consola
    public void actualizarMatriz() {


        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                int[] posicionActual = Global.robot.getPos();
                if (i == posicionActual[1] && j == posicionActual[0]) {
                    matriz[i][j] = 1;
                }

                System.out.print(matriz[i][j] + " ");

            }
            System.out.println(); // Salto de línea al final de cada fila
        }

        System.out.println("Matriz actualizada.");
    }

    // Método para mostrar la matriz (opcional)
    public void imprimirMatriz() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getCelda(int fila,int columa){
        return matriz[fila][columa];
    }

    public void setCelda(int fila,int columa,int valor){
        this.matriz[fila][columa] = valor;
    }

    // Getter para acceder a la matriz
    public int[][] getMatriz() {
        return matriz;
    }
}
