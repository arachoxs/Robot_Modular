import java.util.Scanner;

/**
 * Clase Main - Punto de entrada y controlador principal del sistema de robot
 * modular
 * 
 * Esta clase proporciona la interfaz de usuario y controla la ejecución del
 * sistema.
 * Gestiona la configuración inicial del mapa, la inicialización del robot con
 * sus módulos,
 * y presenta un menú interactivo para controlar las funciones del robot.
 */
public class Main {

    /**
     * Traduce un vector de dirección a texto legible.
     * 
     * @param direccion Array con [deltaX, deltaY] de la dirección
     * @return String con el nombre de la dirección cardinal
     */
    public static String traducir_direccion(int[] direccion) {
        if (direccion[0] == 0 && direccion[1] == 1)
            return "Norte";
        else if (direccion[0] == 0 && direccion[1] == -1)
            return "Sur";
        else if (direccion[0] == 1 && direccion[1] == 0)
            return "Este";
        else if (direccion[0] == -1 && direccion[1] == 0)
            return "Oeste";
        else
            return "";
    }

    /**
     * Permite al usuario agregar un módulo estático al robot mediante entrada por
     * consola.
     * Solicita todos los datos necesarios y valida que los IDs sean únicos.
     * 
     * @param tipo_modulo Tipo de módulo (1=Cámara, 2=Sensor proximidad, 3=Altavoz)
     * @param scanner     Scanner para entrada de datos del usuario
     */
    public static void agregar_modulo_estatico(int tipo_modulo, Scanner scanner) {
        int id;
        while (true) {
            System.out.print("Ingrese el ID del módulo: ");
            id = scanner.nextInt();
            scanner.nextLine();
            if (Global.robot.existe_modulo_id(id)) {
                System.out.println("Error: Ya existe un módulo con ese ID. Intente con otro ID.");
            } else {
                break;
            }
        }
        System.out.print("Ingrese la referencia del módulo: ");
        String referencia = scanner.nextLine();
        System.out.print("Ingrese la descripción del módulo: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese el largo del módulo: ");
        int largo = scanner.nextInt();
        System.out.print("Ingrese el ancho del módulo: ");
        int ancho = scanner.nextInt();
        System.out.print("Ingrese la profundidad del módulo: ");
        int profundidad = scanner.nextInt();
        boolean encendido = Global.robot.get_encendido();

        if (tipo_modulo == 1) {
            Global.robot.agregar_camara(id, referencia, descripcion, largo, ancho, profundidad, encendido, 1);
        } else if (tipo_modulo == 2) {
            Global.robot.agregar_sensor_proximidad(id, referencia, descripcion, largo, ancho, profundidad, encendido,
                    1);
        } else if (tipo_modulo == 3) {
            Global.robot.agregar_altavoz(id, referencia, descripcion, largo, ancho, profundidad, encendido, 1);
        } else {
            System.out.println("Tipo de módulo no válido.");
            return;
        }

        if (tipo_modulo == 1 || tipo_modulo == 2) {
            System.out.print("Ingrese el número de sensores: ");
            int n_sensores = scanner.nextInt();
            for (int i = 0; i < n_sensores; i++) {
                int sensor_id;
                while (true) {
                    System.out.print("Ingrese el ID del sensor " + (i + 1) + ": ");
                    sensor_id = scanner.nextInt();
                    scanner.nextLine();
                    if (Global.robot.existe_sensor(sensor_id)) {
                        System.out.println("Error: Ya existe un sensor con ese ID. Intente con otro ID.");
                    } else {
                        break;
                    }
                }
                System.out.print("Ingrese el tipo del sensor " + (i + 1) + ": ");
                String tipo_sensor = scanner.nextLine();
                System.out.print("Ingrese la descripción del sensor " + (i + 1) + ": ");
                String descripcion_sensor = scanner.nextLine();
                Sensor sensor = new Sensor(sensor_id, tipo_sensor, descripcion_sensor);

                if (tipo_modulo == 1) {
                    Camara camara = (Camara) Global.robot.get_modulo_id(id);
                    camara.agregar_sensor(sensor);
                } else if (tipo_modulo == 2) {
                    SensorProximidad sensorProx = (SensorProximidad) Global.robot.get_modulo_id(id);
                    sensorProx.agregar_sensor(sensor);
                }
            }
            System.out.println("Módulo agregado con " + n_sensores + " sensores.");
        } else if (tipo_modulo == 3) {
            System.out.print("Ingrese el número de actuadores: ");
            int n_actuadores = scanner.nextInt();
            for (int i = 0; i < n_actuadores; i++) {
                int actuador_id;
                while (true) {
                    System.out.print("Ingrese el ID del actuador " + (i + 1) + ": ");
                    actuador_id = scanner.nextInt();
                    scanner.nextLine();
                    if (Global.robot.existe_actuador(actuador_id)) {
                        System.out.println("Error: Ya existe un actuador con ese ID. Intente con otro ID.");
                    } else {
                        break;
                    }
                }
                System.out.print("Ingrese el tipo del actuador " + (i + 1) + ": ");
                String tipo_actuador = scanner.nextLine();
                System.out.print("Ingrese la descripción del actuador " + (i + 1) + ": ");
                String descripcion_actuador = scanner.nextLine();
                Actuador actuador = new Actuador(actuador_id, tipo_actuador, descripcion_actuador);

                Altavoz altavoz = (Altavoz) Global.robot.get_modulo_id(id);
                altavoz.agregar_actuador(actuador);
            }
            System.out.println("Módulo agregado con " + n_actuadores + " actuadores.");
        }
    }

    /**
     * Imprime en consola una lista formateada de todos los módulos estáticos del
     * robot.
     * Muestra información detallada incluyendo sensores y actuadores de cada
     * módulo.
     */
    public static void imprimir_modulos_estaticos() {
        System.out.println("==== Lista de módulos estáticos ====");
        for (Modulo modulo : Global.robot.get_modulos()) {
            if (modulo instanceof ModuloDinamico)
                continue;
            System.out.printf(
                    "ID: %-4d | Ref: %-15s | Desc: %-25s | L: %-3d | A: %-3d | P: %-3d | Encendido: %-5s\n",
                    modulo.get_id(),
                    modulo.get_referencia(),
                    modulo.get_descripcion(),
                    modulo.get_largo(),
                    modulo.get_ancho(),
                    modulo.get_profundidad(),
                    modulo.get_encendido() ? "Sí" : "No");
            if (modulo instanceof Camara) {
                Camara camara = (Camara) modulo;
                System.out.println("  Sensores (" + camara.get_sensores().size() + "):");
                for (Sensor sensor : camara.get_sensores()) {
                    System.out.printf("    - ID: %-4d | Tipo: %-15s | Desc: %-25s\n",
                            sensor.get_id(), sensor.get_tipo(), sensor.get_descripcion());
                }
            } else if (modulo instanceof SensorProximidad) {
                SensorProximidad sensorProx = (SensorProximidad) modulo;
                System.out.println("  Sensores (" + sensorProx.get_sensores().size() + "):");
                for (Sensor sensor : sensorProx.get_sensores()) {
                    System.out.printf("    - ID: %-4d | Tipo: %-15s | Desc: %-25s\n",
                            sensor.get_id(), sensor.get_tipo(), sensor.get_descripcion());
                }
            } else if (modulo instanceof Altavoz) {
                Altavoz altavoz = (Altavoz) modulo;
                System.out.println("  Actuadores (" + altavoz.get_actuadores().size() + "):");
                for (Actuador actuador : altavoz.get_actuadores()) {
                    System.out.printf("    - ID: %-4d | Tipo: %-15s | Desc: %-25s\n",
                            actuador.get_id(), actuador.get_tipo(), actuador.get_descripcion());
                }
            }
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------");
        }
    }

    /**
     * Método principal que inicializa el sistema y ejecuta el bucle de interfaz de
     * usuario.
     * 
     * Configuración inicial:
     * - Configura el mapa con obstáculos y mascotas predefinidos
     * - Inicializa el robot con todos los módulos básicos
     * - Solicita al usuario si desea ver logs de operaciones
     * 
     * Bucle principal:
     * - Muestra el mapa actual y estado del robot
     * - Presenta menú de opciones
     * - Procesa la entrada del usuario y ejecuta acciones correspondientes
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Configuración inicial del mapa con obstáculos y mascotas
        Global.mapa.set_celda(1, 10, 1);
        Global.mapa.set_celda(5, 8, Mapa.MASCOTA);
        Global.mapa.set_celda(6, 8, Mapa.OBSTACULO);
        Global.mapa.set_celda(7, 8, Mapa.OBSTACULO);
        Global.mapa.set_celda(6, 7, Mapa.OBSTACULO);
        Global.mapa.set_celda(7, 7, Mapa.OBSTACULO);
        Global.mapa.set_celda(4, 8, Mapa.OBSTACULO);
        Global.mapa.set_celda(3, 8, Mapa.OBSTACULO);
        Global.mapa.set_celda(4, 7, Mapa.OBSTACULO);
        Global.mapa.set_celda(3, 7, Mapa.OBSTACULO);
        Global.mapa.set_celda(7, 6, Mapa.OBSTACULO);
        Global.mapa.set_celda(6, 6, Mapa.OBSTACULO);
        Global.mapa.set_celda(3, 6, Mapa.OBSTACULO);
        Global.mapa.set_celda(4, 6, Mapa.OBSTACULO);
        Global.mapa.set_celda(4, 4, Mapa.MASCOTA);
        Global.mapa.set_celda(4, 5, Mapa.MASCOTA);
        Global.mapa.set_celda(5, 4, Mapa.MASCOTA);
        Global.mapa.set_celda(5, 6, Mapa.MASCOTA);
        Global.mapa.set_celda(6, 4, Mapa.MASCOTA);
        Global.mapa.set_celda(6, 5, Mapa.MASCOTA);

        new Usuario(1, "beta", "Estandar");

        Scanner scanner = new Scanner(System.in);
        String log;
        while (true) {
            System.out.printf("¿Desea ver el registro de operaciones realizadas? (S/N)\nSelecciones una opción: ");
            log = scanner.nextLine();

            if (log.equalsIgnoreCase("s")) {
                Global.log = true;
                break;
            } else if (log.equalsIgnoreCase("n")) {
                Global.log = false;
                break;
            } else {
                System.out.println("Por favor ingrese un valor válido.");
            }
        }

        // Inicialización de módulos básicos del robot
        Global.robot.agregar_extension(Global.EXTENSION, "Extensión", "Extension principal", 10, 10, 10, false, 1);
        Global.robot.agregar_rotacion(Global.ROTACION, "Rotación", "Rotacion principal", 10, 10, 10, false, 1);
        Global.robot.agregar_helicoidal(Global.HELICOIDAL, "Helicoidal", "Helicoidal principal", 10, 10, 10, false, 1);
        Global.robot.agregar_camara(Global.CAMARA_PRINCIPAL, "Cámara", "Camara principal", 10, 10, 10, false, 1);
        Global.robot.agregar_sensor_proximidad(Global.SENSOR_PROXIMIDAD_PRINCIPAL, "Sensor de Proximidad",
                "Sensor de Proximidad principal", 10, 10, 10, false, 1);
        Global.robot.agregar_altavoz(Global.ALTAVOZ_PRINCIPAL, "Altavoz", "Altavoz principal", 10, 10, 10, false, 1);
        Global.inicializado = true;

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\t\t╔════════════════════════════════════════════════════════════╗");
            System.out.println("\t\t║                      Menú principal                        ║");
            System.out.println("\t\t╚════════════════════════════════════════════════════════════╝");
            Global.mapa.imprimir_mapa();
            System.out.print("\t\tPosición actual del robot: " + Global.robot.get_pos()[0] + " "
                    + Global.robot.get_pos()[1] + "\t|\t");
            System.out.println("Dirección actual del robot: " + traducir_direccion(Global.robot.get_direccion()));
            System.out.println("\t\t╔════════════════════════════════════════════════════════════╗");
            System.out.println("\t\t║ 1) Extensión              6) Altavoz                       ║");
            System.out.println("\t\t║ 2) Rotación               7) Encender robot                ║");
            System.out.println("\t\t║ 3) Helicoidal             8) Apagar robot                  ║");
            System.out.println("\t\t║ 4) Cámara                 9) Agregar módulo estático       ║");
            System.out.println("\t\t║ 5) Sensor proximidad     10) Lista de módulos estáticos    ║");
            System.out.println("\t\t║ 0) Salir                                                   ║");
            System.out.println("\t\t╚════════════════════════════════════════════════════════════╝");
            System.out.print("\t\tSeleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 0:
                    break;
                case 1: {
                    if (!Global.robot.get_encendido()) {
                        System.out.println("El robot debe estar encendido para realizar esta acción.");
                        break;
                    }
                    System.out.print("Ingrese el número de pasos a mover: ");
                    int n_pasos = scanner.nextInt();
                    if (Global.log)
                        System.out.println("\n###############-LOG-##############");
                    Global.robot.get_modulo_id(Global.EXTENSION).get_sistema_comunicacion().recibir_mensaje("MOVER " + n_pasos);
                    if (Global.log)
                        System.out.println("##############-/LOG-##############\n");
                    break;
                }
                case 2: {
                    if (!Global.robot.get_encendido()) {
                        System.out.println("El robot debe estar encendido para realizar esta acción.");
                        break;
                    }
                    System.out.print("Ingrese el número de grados a girar (sentido horario): ");
                    int grados = scanner.nextInt();
                    if (Global.log)
                        System.out.println("\n###############-LOG-##############");
                    Global.robot.get_modulo_id(Global.ROTACION).get_sistema_comunicacion().recibir_mensaje("ROTAR " + grados);
                    if (Global.log)
                        System.out.println("##############-/LOG-##############\n");
                    break;
                }
                case 3: {
                    if (!Global.robot.get_encendido()) {
                        System.out.println("El robot debe estar encendido para realizar esta acción.");
                        break;
                    }
                    System.out.print("Ingrese el número de pasos a mover: ");
                    int n_pasos = scanner.nextInt();
                    int grados = 0;
                    scanner.nextLine();
                    while (true) {
                        System.out.printf(
                                "Ingrese el sentido en que va a girar el robot.\n1) Sentido horario\n2) Sentido antihorario\nSelecciones una opción: ");
                        String sentido = scanner.nextLine();

                        if (sentido.equalsIgnoreCase("1")) {
                            grados = 90;
                            break;
                        } else if (sentido.equalsIgnoreCase("2")) {
                            grados = -90;
                            break;
                        } else {
                            System.out.println("Por favor ingrese un valor válido.");
                        }
                    }
                    System.out.print("Ingrese el número de pasos antes del primer giro: ");
                    int pasos_giro = scanner.nextInt();

                    if (Global.log)
                        System.out.println("\n###############-LOG-##############");
                    Global.robot.get_modulo_id(Global.HELICOIDAL).get_sistema_comunicacion().recibir_mensaje("HELICOIDAL " + n_pasos + "," + grados + "," + pasos_giro);
                    if (Global.log)
                        System.out.println("##############-/LOG-##############\n");
                    break;
                }
                case 4: {
                    if (!Global.robot.get_encendido()) {
                        System.out.println("El robot debe estar encendido para realizar esta acción.");
                        break;
                    }
                    if (Global.log)
                        System.out.println("\n###############-LOG-##############");
                    Global.robot.get_modulo_id(Global.CAMARA_PRINCIPAL).get_sistema_comunicacion().recibir_mensaje("OBSERVAR OBJETO");
                    if (Global.log)
                        System.out.println("##############-/LOG-##############\n");
                    break;
                }
                case 5: {
                    if (!Global.robot.get_encendido()) {
                        System.out.println("El robot debe estar encendido para realizar esta acción.");
                        break;
                    }
                    if (Global.log)
                        System.out.println("\n###############-LOG-##############");
                    Global.robot.get_modulo_id(Global.SENSOR_PROXIMIDAD_PRINCIPAL).get_sistema_comunicacion().recibir_mensaje("OBSERVAR");
                    if (Global.log)
                        System.out.println("##############-/LOG-##############\n");
                    break;
                }
                case 6: {
                    if (!Global.robot.get_encendido()) {
                        System.out.println("El robot debe estar encendido para realizar esta acción.");
                        break;
                    }
                    if (Global.log)
                        System.out.println("\n###############-LOG-##############");
                    Global.robot.get_modulo_id(Global.ALTAVOZ_PRINCIPAL).get_sistema_comunicacion().recibir_mensaje("EMITIR");
                    if (Global.log)
                        System.out.println("##############-/LOG-##############\n");
                    break;
                }
                case 7: {
                    if (Global.log)
                        System.out.println("\n###############-LOG-##############");
                    Global.robot.encender();
                    if (Global.log)
                        System.out.println("##############-/LOG-##############\n");
                    break;
                }
                case 8: {
                    if (Global.log)
                        System.out.println("\n###############-LOG-##############");
                    Global.robot.apagar();
                    if (Global.log)
                        System.out.println("##############-/LOG-##############\n");
                    break;
                }
                case 9: {
                    System.out.println("Seleccione el tipo de módulo estático a agregar:");
                    System.out.println("1) Cámara\n2) Sensor de proximidad\n3) Altavoz");
                    System.out.println("Seleccione una opción: ");
                    int tipo_modulo = scanner.nextInt();
                    scanner.nextLine();
                    agregar_modulo_estatico(tipo_modulo, scanner);
                    break;
                }
                case 10: {
                    imprimir_modulos_estaticos();
                    break;
                }
                default: {
                    System.out.println("Por favor ingrese un valor válido.");
                    break;
                }
            }
        }
    }
}