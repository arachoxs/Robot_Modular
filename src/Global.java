public class Global {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BLACK = "\u001B[30m";


    public static RedComunicacion red = new RedComunicacion();
    public static int[] posicion_inicial = {1, 1};
    public static Robot robot = new Robot("123", "Phi", "Robotcini Banini", posicion_inicial);
    public static Mapa mapa = new Mapa();// Referencia al mapa global
    public static boolean log = false;
    public static boolean inicializado = false;
    public static boolean helicoidal_fallido = false;

    //Constantes id módulos
    public static final int EXTENSION = 1;
    public static final int ROTACION = 2;
    public static final int HELICOIDAL = 3;
    public static final int CAMARA_PRINCIPAL = 4;
    public static final int SENSOR_PROXIMIDAD_PRINCIPAL = 5;
    public static final int ALTAVOZ_PRINCIPAL = 6;
    public static final int SENSOR_PRINCIPAL = 7;
    public static final int ACTUADOR_PRINCIPAL = 8;

    //pausa
    public static void pausa() {
        System.out.println("Presiona ENTER para continuar...");
        new java.util.Scanner(System.in).nextLine();
    }

}
