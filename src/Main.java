//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario(1,"beta","Estandar");
        Robot robot = new Robot ("123","ro","Robotcini banini");

        robot.agregar_extension(1, "ext", "Extension", 10, 10, 10, false, 1);
        robot.agregar_rotacion(2, "rot", "Rotacion", 10, 10, 10, false, 1);
        robot.agregar_helicoidal(3, "heli", "Helicoidal", 10, 10, 10, false, 1);

        robot.agregar_camara(4, "cam", "Camara", 10, 10, 10, false, 1);
        robot.agregar_sensor_proximidad(5, "prox", "Proximidad", 10, 10, 10, false, 1);
        robot.agregar_altavoz(6, "alt", "Altavoz", 10, 10, 10, false, 1);


        robot.get_modulo_Id(5).get_sistema_comunicacion().enviar_mensaje(robot.get_modulo_Id(4), "Objeto detectado");

        //usuario y modulo_dinamico
        //modulo dinamico y Sensor_proximidad
        //Sensor_proximidad y camara
        //si es aire-> camara y modulo dinamico
        //si es mascota -> camara y altavoz
        //si es obstaculo -> camara y rotacion
        //si ya movio el mascota -> altavoz y modulo dinamico



}
}