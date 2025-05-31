import javax.swing.*;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static String traducir_direccion(int[] direccion) {
        if (direccion[0] == 0 && direccion[1] == 1) return "Norte";
        else if (direccion[0] == 0 && direccion[1] == -1) return "Sur";
        else if (direccion[0] == 1 && direccion[1] == 0) return "Este";
        else if (direccion[0] == -1 && direccion[1] == 0) return "Oeste";
        else return "";
    }
    public static void main(String[] args) {
        //Configuración inicial del mapa
        Global.mapa.set_celda(1, 10, 1);
        for (int i = 5; i < 10; i++){
            Global.mapa.set_celda(2, i, 1);
        }
        Global.mapa.set_celda(2, 6, 0);

        // Inicializar usuario
        new Usuario(1, "beta", "Estandar");

        Scanner scanner = new Scanner(System.in);
        String log;
        while (true) {
            System.out.printf("¿Desea ver el registro de operaciones realizadas? (S/N)\nSelecciones una opción: ");
            log = scanner.nextLine();

            if (log.equalsIgnoreCase("s")){
                Global.log = true;
                break;
            } else if (log.equalsIgnoreCase("n")){
                Global.log = false;
                break;
            } else{
                System.out.println("Por favor ingrese un valor válido.");
            }
        }

        // Agregar módulos al robot
        Global.robot.agregar_extension(Global.EXTENSION, "Extensión", "Extension por defecto", 10, 10, 10, false, 1);
        Global.robot.agregar_rotacion(Global.ROTACION, "Rotación", "Rotacion por defecto", 10, 10, 10, false, 1);
        Global.robot.agregar_helicoidal(Global.HELICOIDAL, "Helicoidal", "Helicoidal por defecto", 10, 10, 10, false, 1);
        Global.robot.agregar_camara(Global.CAMARA, "Cámara", "Camara por defecto", 10, 10, 10, false, 1);
        Global.robot.agregar_sensor_proximidad(Global.SENSORPROXIMIDAD, "Sensor de Proximidad", "Sensor de Proximidad por defecto", 10, 10, 10, false, 1);
        Global.robot.agregar_altavoz(Global.ALTAVOZ, "Altavoz", "Altavoz por defecto", 10, 10, 10, false, 1);
        Global.robot.encender();

        int opcion = -1;

        while(opcion != 0){
            System.out.println("----------------------------------");
            Global.mapa.imprimir_mapa();
            System.out.println("Posición actual del robot: " + Global.robot.get_pos()[0] + " " + Global.robot.get_pos()[1]);
            System.out.println("Dirección actual del robot: " + traducir_direccion(Global.robot.get_direccion()));
            System.out.println("----------------------------------");
            System.out.println("1) Extensión\n2) Rotación\n3) Helicoidal\n4) Camara\n5) Sensor proximidad\n6) Altavoz\n0) Salir");
            System.out.println("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            switch(opcion){
                case 1:{
                    System.out.print("Ingrese el número de pasos a mover: ");
                    int n_pasos = scanner.nextInt();
                    if (Global.log == true) System.out.println("\n###############-LOG-##############");
                    Global.robot.get_modulo_id(Global.EXTENSION).get_sistema_comunicacion().recibir_mensaje("MOVER " + n_pasos);
                    if (Global.log == true) System.out.println("##############-/LOG-##############\n");
                    break;
                }
                case 2:{
                    System.out.print("Ingrese el número de grados a girar (sentido horario): ");
                    int grados = scanner.nextInt();
                    if (Global.log == true) System.out.println("\n###############-LOG-##############");
                    Global.robot.get_modulo_id(Global.ROTACION).get_sistema_comunicacion().recibir_mensaje("ROTAR " + grados);
                    if (Global.log == true) System.out.println("##############-/LOG-##############\n");
                    break;
                }
                default:{
                    System.out.println("Por favor ingrese un valor válido.");
                    break;
                }
            }
        }


    }
}