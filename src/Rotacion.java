public class Rotacion extends ModuloDinamico {
    //La convención usada para los grados es la siguiente: Positivo en sentido horario, negativo en sentido antihorario.
    //Constructor
    public Rotacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    //Methods
    @Override
    public boolean moverse(int n_pasos, int grados , int pasos_giro) {
        // Obtener la dirección actual del robot
        int[] direccion_actual = Global.robot.get_direccion();

        // Definir las 4 direcciones posibles en orden horario
        int[][] direcciones = {
                { 1, 0 }, // Derecha
                { 0, -1 }, // Abajo
                { -1, 0 }, // Izquierda
                { 0, 1 } // Arriba
        };

        // Encontrar el índice de la dirección actual
        int indice_actual = 0;
        for (int i = 0; i < 4; i++) {
            if (direccion_actual[0] == direcciones[i][0] && direccion_actual[1] == direcciones[i][1]) {
                indice_actual = i;
                break;
            }
        }

        // Redondear los grados al múltiplo de 90 más cercano
        int pasos = (int) Math.round(grados / 90.0);

        // Ajustar para negativos y normalizar a 0-3
        int nuevo_indice = (indice_actual + pasos) % 4;
        if (nuevo_indice < 0) {
            nuevo_indice += 4;
        }

        // Cambiar la dirección del robot
        Global.robot.set_direccion(direcciones[nuevo_indice]);

        if (Global.log) System.out.println("Robot giró " + grados + " grados. Nueva dirección: [" +
                direcciones[nuevo_indice][0] + "," + direcciones[nuevo_indice][1] + "]");

        return true;
    }

    public boolean moverse(int grados){
        return moverse(0, grados, 0);
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        mensaje = mensaje.trim().toUpperCase(); // Normaliza el mensaje
        boolean resultado_accion = false;


        switch (mensaje) {
            case "ROTACION IZQUIERDA":
                resultado_accion = moverse(-90);
                this.get_sistema_control().enviar_respuesta_accion(Global.SENSORPROXIMIDAD, "VERIFICAR IZQUIERDA");
                break;

            case "ROTACION IZQUIERDA FIJA":
                resultado_accion = moverse(-90);
                break;

            case "ROTACION IZQUIERDA FALLIDA":
                resultado_accion = moverse(180);
                this.get_sistema_control().enviar_respuesta_accion(Global.SENSORPROXIMIDAD, "VERIFICAR DERECHA");
                break;

            default:
                try {
                    if (mensaje.startsWith("ROTAR")) {
                        int grados = extraer_grados(mensaje);
                        resultado_accion = moverse(grados);
                    } else {
                        System.out.println("Mensaje no reconocido: " + mensaje);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: número de grados inválido.");
                } catch (Exception e) {
                    System.out.println("Error general al interpretar mensaje: " + e.getMessage());
                }
                break;
        }

        this.enviar_respuesta_accion(resultado_accion);

    }

    private int extraer_grados(String mensaje) throws NumberFormatException {
        String numeroStr = mensaje.substring("ROTAR".length()).trim();
        return Integer.parseInt(numeroStr);
    }

    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log) System.out.println("Módulo de Rotación encendido");
    }

    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log) System.out.println("Módulo de Rotación apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if(respuesta){
            if (Global.log) System.out.println("Rotacion del robot ejecutada sin problemas.");
        }
        else{
            if (Global.log) System.out.println("Error en rotacion detectada, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }

}
