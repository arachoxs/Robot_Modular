public class Rotacion extends ModuloDinamico {

    //Constructor
    public Rotacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    //Methods
    @Override
    public boolean moverse(int n_pasos, int grados , int pasos_giro) {
        // Obtener la dirección actual del robot
        int[] direccionActual = Global.robot.getDireccion();

        // Definir las 4 direcciones posibles en orden horario
        int[][] direcciones = {
                { 0, 1 }, // Derecha
                { 1, 0 }, // Abajo
                { 0, -1 }, // Izquierda
                { -1, 0 } // Arriba
        };

        // Encontrar el índice de la dirección actual
        int indiceActual = 0;
        for (int i = 0; i < 4; i++) {
            if (direccionActual[0] == direcciones[i][0] && direccionActual[1] == direcciones[i][1]) {
                indiceActual = i;
                break;
            }
        }

        // Redondear los grados al múltiplo de 90 más cercano
        int pasos = (int) Math.round(grados / 90.0);

        // Ajustar para negativos y normalizar a 0-3
        int nuevoIndice = (indiceActual + pasos) % 4;
        if (nuevoIndice < 0) {
            nuevoIndice += 4;
        }

        // Cambiar la dirección del robot
        Global.robot.setDireccion(direcciones[nuevoIndice]);

        System.out.println("Robot giró " + grados + " grados. Nueva dirección: [" +
                direcciones[nuevoIndice][0] + "," + direcciones[nuevoIndice][1] + "]");

        return true;
    }

    public boolean moverse(int n_pasos, int grados){
        return moverse(0, grados, 0);
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        if(mensaje.equals("ROTAR IZQUIERDA")){
            moverse(0, -90);
            this.get_sistema_comunicacion().enviar_mensaje(5,"VERIFICAR IZQUIERDA");
        }
    }

    @Override
    public void encender() {
        System.out.println("Módulo de Rotación encendido");
    }

    @Override
    public void apagar() {
        System.out.println("Módulo de Rotación apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {

    }
}
