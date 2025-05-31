import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Global.mapa.set_celda(0, 4, 1);
        Global.mapa.set_celda(1, 10, 1);
        for (int i = 5; i < 10; i++){
            Global.mapa.set_celda(2, i, 1);
        }
        Global.mapa.set_celda(2, 8, 0);
        // Inicializar usuario
        new Usuario(1, "beta", "Estandar");

        // Agregar módulos al robot
        Global.robot.agregar_extension(1, "ext", "Extension", 10, 10, 10, false, 1);
        Global.robot.agregar_rotacion(2, "rot", "Rotacion", 10, 10, 10, false, 1);
        Global.robot.agregar_helicoidal(3, "heli", "Helicoidal", 10, 10, 10, false, 1);
        Global.robot.agregar_camara(4, "cam", "Camara", 10, 10, 10, false, 1);
        Global.robot.agregar_sensor_proximidad(5, "prox", "Proximidad", 10, 10, 10, false, 1);
        Global.robot.agregar_altavoz(6, "alt", "Altavoz", 10, 10, 10, false, 1);

        while(true){
            Global.mapa.imprimir_mapa();
            Scanner scanner = new Scanner(System.in);
            System.out.println("posicion actual del robot: " + Global.robot.getPos()[0] + " " + Global.robot.getPos()[1]);
            System.out.println("direccion actual del robot: " + Global.robot.getDireccion()[0] + " " + Global.robot.getDireccion()[1]);
            System.out.print("Ingrese el número de pasos a mover: ");
            int n_pasos = scanner.nextInt();
            Global.robot.get_modulo_id(1).get_sistema_comunicacion().recibir_mensaje("mover "+n_pasos);
        }


    }
}