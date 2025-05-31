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
        Global.robot.agregar_extension(Global.EXTENSION, "ext", "Extension", 10, 10, 10, false, 1);
        Global.robot.agregar_rotacion(Global.ROTACION, "rot", "Rotacion", 10, 10, 10, false, 1);
        Global.robot.agregar_helicoidal(Global.HELICOIDAL, "heli", "Helicoidal", 10, 10, 10, false, 1);
        Global.robot.agregar_camara(Global.CAMARA, "cam", "Camara", 10, 10, 10, false, 1);
        Global.robot.agregar_sensor_proximidad(Global.SENSORPROXIMIDAD, "prox", "Proximidad", 10, 10, 10, false, 1);
        Global.robot.agregar_altavoz(Global.ALTAVOZ, "alt", "Altavoz", 10, 10, 10, false, 1);

        while(true){
            Global.mapa.imprimir_mapa();
            Scanner scanner = new Scanner(System.in);
            System.out.println("posicion actual del robot: " + Global.robot.get_pos()[0] + " " + Global.robot.get_pos()[1]);
            System.out.println("direccion actual del robot: " + Global.robot.get_direccion()[0] + " " + Global.robot.get_direccion()[1]);
            System.out.print("Ingrese el número de pasos a mover: ");
            int n_pasos = scanner.nextInt();
            Global.robot.get_modulo_id(Global.EXTENSION).get_sistema_comunicacion().recibir_mensaje("MOVER "+n_pasos);
        }


    }
}