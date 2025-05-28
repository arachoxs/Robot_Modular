//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario(1,"beta","Estandar");
        Robot robot = new Robot ("123","ro","Robotcini banini");

        //modulos dinamicos
        Extension modulo_extencion = new Extension(1,"ext","Extension",10,10,10,false,1);
        Rotacion modulo_rotacion = new Rotacion(2,"rot","Rotacion",10,10,10,false,1);
        Helicoidal modulo_helicoidal = new Helicoidal(3,"heli","Helicoidal",10,10,10,false,1);

        //agregacion modulos
        robot.agregar_modulo(modulo_extencion);
        robot.agregar_modulo(modulo_rotacion);
        robot.agregar_modulo(modulo_helicoidal);

        //modulos estaticos
        Camara modulo_camara = new Camara(4,"cam","Camara",10,10,10,false,0);
        Sensor sensor_camara = new Sensor(5,"visual","Sensor que reconoce objetos");
        modulo_camara.agregar_sensor(sensor_camara);

        SensorProximidad modulo_proximidad = new SensorProximidad(6,"prox","Proximidad",10,10,10, false, 0);
        Sensor sensor_proximidad = new Sensor(7,"proximidad","Sensor que detecta objetos");
        modulo_proximidad.agregar_sensor(sensor_proximidad);

        Altavoz modulo_altavoz = new Altavoz(8,"alt","Altavoz",10,10,10,false,0);
        Actuador actuador_altavoz = new Actuador(9,"sonido","Emite alerta");
        modulo_altavoz.agregar_actuador(actuador_altavoz);

        robot.agregar_modulo(modulo_camara);
        robot.agregar_modulo(modulo_proximidad);
        robot.agregar_modulo(modulo_altavoz);

        modulo_proximidad.get_sistema_comunicacion().enviar_mensaje(modulo_camara, "Objeto detectado");


}
}