import java.util.HashMap;

public class Global {
    public static RedComunicacion red = new RedComunicacion();
    public static Robot robot = new Robot("123", "Phi", "Robotcini Banini");
    public static Mapa mapa = new Mapa();// Referencia al mapa global

    //Constantes id módulos
    public static final int EXTENSION = 1;
    public static final int ROTACION = 2;
    public static final int HELICOIDAL = 3;
    public static final int CAMARA = 4;
    public static final int SENSORPROXIMIDAD = 5;
    public static final int ALTAVOZ = 6;

}
